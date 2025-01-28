package org.yunz21.powerofthevoid.spells.orokin;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.yunz21.powerofthevoid.PowerOfTheVoid;
import org.yunz21.powerofthevoid.registries.VSchoolRegistry;
import org.yunz21.powerofthevoid.registries.VSoundRegistry;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;

import java.util.Optional;

@AutoSpellConfig
public class MesmerSkinSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(PowerOfTheVoid.MODID, "mesmer_skin");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(VSchoolRegistry.OROKIN_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(1)
            .build();

    public MesmerSkinSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 3;
        this.castTime = 20;
        this.baseManaCost = 60;
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
        return Optional.of(VSoundRegistry.MESMER_SKIN_START.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(VSoundRegistry.MESMER_SKIN_END.get());
    }

    @Override
    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Remove all negative effects
        entity.getActiveEffects().stream()
                .filter(effect -> effect.getEffect().getCategory() == MobEffectCategory.HARMFUL)
                .toList() // Avoid concurrent modification
                .forEach(effect -> entity.removeEffect(effect.getEffect()));

        var amp = getAmp(spellLevel);
        entity.addEffect(new MobEffectInstance(VMobEffectRegistry.MESMER_SKIN.get(), MobEffectInstance.INFINITE_DURATION, amp, false, false, true));
        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.ANIMATION_LONG_CAST;
    }

    private int getAmp(int spellLevel) {
        return baseSpellPower + spellLevel * 2;
    }
}
