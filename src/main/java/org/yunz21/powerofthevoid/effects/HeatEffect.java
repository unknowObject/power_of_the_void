package org.yunz21.powerofthevoid.effects;

import io.redspace.ironsspellbooks.effect.CustomDescriptionMobEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class HeatEffect extends CustomDescriptionMobEffect {
    public HeatEffect(MobEffectCategory mobEffectCategory, int color) {
        super(MobEffectCategory.HARMFUL, color);
    }

    @Override
    public Component getDescriptionLine(MobEffectInstance instance) {
        int amp = instance.getAmplifier() + 1;
        return Component.translatable("tooltip.power_of_the_void.heat_description", amp).withStyle(ChatFormatting.BLUE);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, net.minecraft.world.entity.ai.attributes.AttributeMap attributes, int amplifier) {
        super.removeAttributeModifiers(entity, attributes, amplifier);
        if (!entity.level().isClientSide) {
            // Extinguish the entity when the effect is removed
            entity.clearFire();
        }
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide) {
            // Ignite the entity for a duration based on the amplifier
            int burnDuration = 2 + amplifier * 2; // Base 2 seconds, +2 seconds per level
            if (!entity.isOnFire()) {
                entity.setSecondsOnFire(burnDuration);
            }
        }
        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // Apply the effect tick every second
        return true;
    }


}
