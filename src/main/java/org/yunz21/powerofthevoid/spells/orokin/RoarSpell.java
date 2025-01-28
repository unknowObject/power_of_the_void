package org.yunz21.powerofthevoid.spells.orokin;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.events.SpellHealEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.network.spell.ClientboundHealParticles;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.setup.Messages;
import io.redspace.ironsspellbooks.spells.TargetedTargetAreaCastData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import org.yunz21.powerofthevoid.PowerOfTheVoid;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;
import org.yunz21.powerofthevoid.registries.VSchoolRegistry;
import org.yunz21.powerofthevoid.registries.VSoundRegistry;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@AutoSpellConfig
public class RoarSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(PowerOfTheVoid.MODID, "roar");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getSpellPower(spellLevel, caster) * 20, 1)),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getDamageBoost(spellLevel, caster), 0), Component.translatable("attribute.name.generic.damage_boost")),
                Component.translatable("ui.irons_spellbooks.radius", Utils.stringTruncation(getRadius(spellLevel), 2))
        );
    }

    private double getDamageBoost(int spellLevel, LivingEntity caster) {
        return 1.0F + (0.4F * (spellLevel)) * 100;
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(VSchoolRegistry.OROKIN_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(1)
            .build();

    public RoarSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 15;
        this.spellPowerPerLevel = 3;
        this.castTime = 30;
        this.baseManaCost = 75;
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

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(VSoundRegistry.ROAR.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        int radius = getRadius(spellLevel);
        level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius)).forEach((target) -> {
            if (target.distanceToSqr(entity.position()) < radius * radius && target instanceof Player) {
                target.addEffect(new MobEffectInstance(VMobEffectRegistry.ROAR.get(), getDuration(spellLevel), getAmplifier(spellLevel)));
            }
        });
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public int getRadius(int spellLevel) {
        return 10 + spellLevel * 2;
    }

    public int getDuration(int spellLevel) {
        return 400 + spellLevel * 100;
    }

    public int getAmplifier(int spellLevel) {
        return spellLevel - 1;
    }
}
