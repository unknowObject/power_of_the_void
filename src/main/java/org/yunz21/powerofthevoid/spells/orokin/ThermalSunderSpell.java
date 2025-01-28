package org.yunz21.powerofthevoid.spells.orokin;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.network.spell.ClientboundParticleShockwave;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.particle.ShockwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.yunz21.powerofthevoid.registries.VSoundRegistry;
import io.redspace.ironsspellbooks.setup.Messages;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.yunz21.powerofthevoid.PowerOfTheVoid;
import org.yunz21.powerofthevoid.registries.VSchoolRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class ThermalSunderSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(PowerOfTheVoid.MODID, "thermal_sunder");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(VSchoolRegistry.OROKIN_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(1)
            .build();

    public ThermalSunderSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 15;
        this.spellPowerPerLevel = 3;
        this.castTime = 20;
        this.baseManaCost = 50;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)),
                Component.translatable("ui.irons_spellbooks.rend", Utils.stringTruncation(100, 1)),
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getDuration(spellLevel, caster), 2)),
                Component.translatable("ui.irons_spellbooks.radius", Utils.stringTruncation(getRadius(spellLevel, caster), 2))
        );
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(VSoundRegistry.THERMAL_SUNDER_ICE.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(VSoundRegistry.THERMAL_SUNDER_FIRE.get());
    }

    @Override
    public void onServerPreCast(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        float radius = getRadius(spellLevel, entity);

        MagicManager.spawnParticles(level, new ShockwaveParticleOptions(SchoolRegistry.ICE.get().getTargetingColor(), radius, false, "irons_spellbooks:snowflake"), entity.getX(), entity.getY() + .15f, entity.getZ(), 1, 0, 0, 0, 0, true);
        level.getEntities(entity, entity.getBoundingBox().inflate(radius, 4, radius), (target) -> !DamageSources.isFriendlyFireBetween(target, entity) && Utils.hasLineOfSight(level, entity, target, true)).forEach(target -> {
            if (target instanceof LivingEntity livingEntity && livingEntity.distanceToSqr(entity) < radius * radius) {
                livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.CHILLED.get(), getDuration(spellLevel, entity)));
                MagicManager.spawnParticles(level, ParticleHelper.SNOWFLAKE, livingEntity.getX(), livingEntity.getY() + livingEntity.getBbHeight() * .5f, livingEntity.getZ(), 50, livingEntity.getBbWidth() * .5f, livingEntity.getBbHeight() * .5f, livingEntity.getBbWidth() * .5f, .03, false);
            }
        });
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {


        float radius = getRadius(spellLevel, entity);
        Vector3f edge = new Vector3f(.7f, 1f, 1f);
        Vector3f center = new Vector3f(1, 1f, 1f);
        var damage = getDamage(spellLevel, entity);

        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.FIRE.get()).getTargetingColor(), radius), entity.getX(), entity.getY() + (double)0.165F, entity.getZ(), 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, true);
        Messages.sendToPlayersTrackingEntity(new ClientboundParticleShockwave(new Vec3(entity.getX(), entity.getY() + (double)0.165F, entity.getZ()), radius, (ParticleType) ParticleRegistry.FIRE_PARTICLE.get()), entity, true);
        level.getEntities(entity, entity.getBoundingBox().inflate((double)radius, (double)4.0F, (double)radius), (target) -> !DamageSources.isFriendlyFireBetween(target, entity) && Utils.hasLineOfSight(level, entity, target, true)).forEach((target) -> {
            DamageSources.applyDamage(target, damage, getDamageSource(entity));
            if (target instanceof LivingEntity livingEntity) {
                if (livingEntity.distanceToSqr(entity) < (double)(radius * radius)) {
                    int i = this.getDuration(spellLevel, entity);
                    MagicManager.spawnParticles(level, ParticleHelper.EMBERS, livingEntity.getX(), livingEntity.getY() + (double)(livingEntity.getBbHeight() * 0.5F), livingEntity.getZ(), 50, (double)(livingEntity.getBbWidth() * 0.5F), (double)(livingEntity.getBbHeight() * 0.5F), (double)(livingEntity.getBbWidth() * 0.5F), 0.03, false);
                    livingEntity.setSecondsOnFire(i / 20);
                    if (livingEntity.hasEffect(MobEffectRegistry.REND.get())){
                        livingEntity.addEffect(new MobEffectInstance((MobEffect)MobEffectRegistry.REND.get(), MobEffectInstance.INFINITE_DURATION, this.getRendAmplifier(20, entity)));
                    }
                }
            }
        });
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public SpellDamageSource getDamageSource(@org.jetbrains.annotations.Nullable Entity projectile, Entity attacker) {
        return super.getDamageSource(projectile, attacker).setFireTime(3);
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return getSpellPower(spellLevel, caster) * .5f;
    }


    public float getRadius(int spellLevel, LivingEntity caster) {
        return 6 + spellLevel * 2;
    }

    public int getDuration(int spellLevel, LivingEntity caster) {
        return (int) (getSpellPower(spellLevel, caster) * 30);
    }

    public int getRendAmplifier(int spellLevel, LivingEntity caster) {
        return 1 + (spellLevel);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.ANIMATION_LONG_CAST_FINISH;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.ANIMATION_LONG_CAST_FINISH;
    }
}
