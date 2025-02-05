package org.yunz21.powerofthevoid.spells.orokin;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.effect.ChargeEffect;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.yunz21.powerofthevoid.PowerOfTheVoid;
import org.yunz21.powerofthevoid.capabilities.BatteryCapabilityProvider;
import org.yunz21.powerofthevoid.registries.VSchoolRegistry;
import org.yunz21.powerofthevoid.registries.VSoundRegistry;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class RedlineSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(PowerOfTheVoid.MODID, "redline");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getSpellPower(spellLevel, caster) * 20, 1)),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentSpellPower(spellLevel, caster), 0), Component.translatable("attribute.irons_spellbooks.spell_power")),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentSpellPower(spellLevel, caster), 0), Component.translatable("attribute.name.generic.attack_speed"))
                //Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentSpellPower(spellLevel, caster), 0), Component.translatable("attribute.irons_spellbooks.spell_power"))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(VSchoolRegistry.OROKIN_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(1)
            .build();

    public RedlineSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 15;
        this.spellPowerPerLevel = 3;
        this.castTime = 30;
        this.baseManaCost = 100;
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
        return Optional.of(VSoundRegistry.REDLINE_START.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(VSoundRegistry.REDLINE_END.get());
    }


    private float getPercentSpellPower(int spellLevel, LivingEntity entity) {
        return spellLevel * ChargeEffect.SPELL_POWER_PER_LEVEL * 100;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        entity.addEffect(new MobEffectInstance(VMobEffectRegistry.REDLINE.get(), (int) (getSpellPower(spellLevel, entity) * 20), spellLevel - 1, false, false, true));

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.PREPARE_CROSS_ARMS;
    }

    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.CAST_T_POSE;
    }
}
