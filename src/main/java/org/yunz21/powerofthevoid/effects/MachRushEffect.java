package org.yunz21.powerofthevoid.effects;

import io.redspace.ironsspellbooks.effect.CustomDescriptionMobEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;

public class MachRushEffect extends CustomDescriptionMobEffect {
    public MachRushEffect(MobEffectCategory mobEffectCategory, int color) {
        super(MobEffectCategory.BENEFICIAL, 0x00FF00); // 设置效果类型和颜色
    }

    @Override
    public Component getDescriptionLine(MobEffectInstance instance) {
        int amp = instance.getAmplifier() + 1;
        return Component.translatable("tooltip.power_of_the_void.mach_rush_description", amp).withStyle(ChatFormatting.BLUE);
    }
}