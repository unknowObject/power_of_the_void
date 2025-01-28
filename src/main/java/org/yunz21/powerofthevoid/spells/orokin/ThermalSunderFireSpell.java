package org.yunz21.powerofthevoid.spells.orokin;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.network.spell.ClientboundParticleShockwave;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.particle.ShockwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.HitResult;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;
import org.yunz21.powerofthevoid.registries.VSoundRegistry;
import io.redspace.ironsspellbooks.setup.Messages;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.yunz21.powerofthevoid.PowerOfTheVoid;
import org.yunz21.powerofthevoid.registries.VSchoolRegistry;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AutoSpellConfig
public class ThermalSunderFireSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(PowerOfTheVoid.MODID, "thermal_sunder_fire");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(VSchoolRegistry.OROKIN_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(1)
            .build();

    public ThermalSunderFireSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 2;
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
        return CastType.INSTANT;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

//    @Override
//    public Optional<SoundEvent> getCastStartSound() {
//        return Optional.of(VSoundRegistry.THERMAL_SUNDER_ICE.get());
//    }
//
//    @Override
//    public Optional<SoundEvent> getCastFinishSound() {
//        return Optional.of(VSoundRegistry.THERMAL_SUNDER_FIRE.get());
//    }

//    @Override
//    public void onServerPreCast(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
//        float radius = getRadius(spellLevel, entity);
//
//        MagicManager.spawnParticles(level, new ShockwaveParticleOptions(SchoolRegistry.ICE.get().getTargetingColor(), radius, false, "irons_spellbooks:snowflake"), entity.getX(), entity.getY() + .15f, entity.getZ(), 1, 0, 0, 0, 0, true);
//        level.getEntities(entity, entity.getBoundingBox().inflate(radius, 4, radius), (target) -> !DamageSources.isFriendlyFireBetween(target, entity) && Utils.hasLineOfSight(level, entity, target, true)).forEach(target -> {
//            if (target instanceof LivingEntity livingEntity && livingEntity.distanceToSqr(entity) < radius * radius) {
//                livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.CHILLED.get(), getDuration(spellLevel, entity)));
//                MagicManager.spawnParticles(level, ParticleHelper.SNOWFLAKE, livingEntity.getX(), livingEntity.getY() + livingEntity.getBbHeight() * .5f, livingEntity.getZ(), 50, livingEntity.getBbWidth() * .5f, livingEntity.getBbHeight() * .5f, livingEntity.getBbWidth() * .5f, .03, false);
//            }
//        });
//    }


    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        float radius = getRadius(spellLevel, entity);
        var damage = getDamage(spellLevel, entity);

        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(((SchoolType)SchoolRegistry.FIRE.get()).getTargetingColor(), radius), entity.getX(), entity.getY() + (double)0.165F, entity.getZ(), 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, true);
        Messages.sendToPlayersTrackingEntity(new ClientboundParticleShockwave(new Vec3(entity.getX(), entity.getY() + (double)0.165F, entity.getZ()), radius, (ParticleType) ParticleRegistry.FIRE_PARTICLE.get()), entity, true);
        level.getEntities(entity, entity.getBoundingBox().inflate((double)radius, (double)4.0F, (double)radius), (target) -> !DamageSources.isFriendlyFireBetween(target, entity) && Utils.hasLineOfSight(level, entity, target, true)).forEach((target) -> {
            if (target instanceof LivingEntity livingEntity && livingEntity.distanceToSqr(entity) < radius * radius) {

                MagicManager.spawnParticles(level, ParticleHelper.FIRE, livingEntity.getX(), livingEntity.getY() + livingEntity.getBbHeight() * .5f, livingEntity.getZ(), 50, livingEntity.getBbWidth() * .5f, livingEntity.getBbHeight() * .5f, livingEntity.getBbWidth() * .5f, .03, false);


                if (livingEntity.hasEffect(MobEffectRegistry.CHILLED.get())) {
                    // If target already effected by chilled, deal more damage base on chilled level and clear chilled effect, add 1 level heat
                    int amp = Objects.requireNonNull(livingEntity.getEffect(MobEffectRegistry.CHILLED.get())).getAmplifier();
                    livingEntity.removeEffect(MobEffectRegistry.CHILLED.get());
                    livingEntity.addEffect(new MobEffectInstance(VMobEffectRegistry.HEAT.get(), getDuration(spellLevel, entity)));
                    livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.REND.get(), getDuration(spellLevel, entity), (amp + 1) * 5 - 1));
                    DamageSources.applyDamage(target, damage * (float) Math.pow(2, amp+1), getDamageSource(entity));
                    pushTargets(livingEntity, entity, 0.2f);
                } else if (livingEntity.hasEffect(VMobEffectRegistry.HEAT.get())) {
                    // If target not effected by heat, add 1 level heat and deal more damage
                    int amp = Objects.requireNonNull(livingEntity.getEffect(VMobEffectRegistry.HEAT.get())).getAmplifier();
                    livingEntity.removeEffect(VMobEffectRegistry.HEAT.get());
                    livingEntity.addEffect(new MobEffectInstance(VMobEffectRegistry.HEAT.get(), getDuration(spellLevel, entity), amp + 1));
                    DamageSources.applyDamage(target, damage * (float) Math.pow(1.5, amp), getDamageSource(entity));
                    pullTargets(livingEntity, entity, 0.1f);
                } else {
                    // If target not effected by heat, add 1 level heat and deal damage
                    livingEntity.addEffect(new MobEffectInstance(VMobEffectRegistry.HEAT.get(), getDuration(spellLevel, entity)));
                    DamageSources.applyDamage(target, damage, getDamageSource(entity));
                    pullTargets(livingEntity, entity, 0.1f);
                }
            }

        });
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public SpellDamageSource getDamageSource(@org.jetbrains.annotations.Nullable Entity projectile, Entity attacker) {
        return super.getDamageSource(projectile, attacker).setFireTime(3);
    }

    public void pushTargets(LivingEntity livingEntity, Entity entity, float pushForce) {
        // Calculate the vector from the entity to the player
        Vec3 direction = new Vec3(
                - entity.getX() + livingEntity.getX(),
                - entity.getY() + livingEntity.getY(),
                - entity.getZ() + livingEntity.getZ()
        ).normalize(); // Normalize to get a unit vector

        // Apply pulling force to the entity's motion
        Vec3 newMotion = livingEntity.getDeltaMovement().add(direction.scale(pushForce));
        livingEntity.setDeltaMovement(newMotion);
    }

    public void pullTargets(LivingEntity livingEntity, LivingEntity entity, float pulllForce) {
        // Calculate the vector from the entity to the player
        Vec3 direction = new Vec3(
                entity.getX() - livingEntity.getX(),
                entity.getY() - livingEntity.getY(),
                entity.getZ() - livingEntity.getZ()
        ).normalize(); // Normalize to get a unit vector

        // Apply pulling force to the entity's motion
        Vec3 newMotion = livingEntity.getDeltaMovement().add(direction.scale(pulllForce));
        livingEntity.setDeltaMovement(newMotion);
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return getSpellPower(spellLevel, caster) * .4f;
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

//    @Override
//    public AnimationHolder getCastStartAnimation() {
//        return SpellAnimations.ANIMATION_LONG_CAST_FINISH;
//    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(VSoundRegistry.THERMAL_SUNDER_FIRE.get());
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.ANIMATION_LONG_CAST_FINISH;
    }
}
