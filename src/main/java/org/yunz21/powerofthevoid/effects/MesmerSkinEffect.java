package org.yunz21.powerofthevoid.effects;

import io.redspace.ironsspellbooks.effect.CustomDescriptionMobEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class MesmerSkinEffect extends CustomDescriptionMobEffect {
    private int hits_remaining;

    public MesmerSkinEffect(MobEffectCategory mobEffectCategory, int color) {
        super(MobEffectCategory.BENEFICIAL, color);
    }

    @Override
    public Component getDescriptionLine(MobEffectInstance instance) {
        int amp = instance.getAmplifier() + 1;
        return Component.translatable("tooltip.power_of_the_void.invulnerable_description", amp).withStyle(ChatFormatting.BLUE);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity livingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(livingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

//    public static boolean doEffect(LivingEntity livingEntity, DamageSource damageSource) {
//
//    }
//
//
//
//    public int getHits_remaining {
//        return hits_remaining;
//    }
}
