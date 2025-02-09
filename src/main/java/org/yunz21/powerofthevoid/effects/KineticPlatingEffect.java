package org.yunz21.powerofthevoid.effects;

import io.redspace.ironsspellbooks.effect.CustomDescriptionMobEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.yunz21.powerofthevoid.capabilities.BatteryCapability;
import org.yunz21.powerofthevoid.capabilities.BatteryCapabilityProvider;

public class KineticPlatingEffect extends CustomDescriptionMobEffect {
    private int minReduction;
    private int maxReduction;

    public KineticPlatingEffect(MobEffectCategory mobEffectCategory, int color) {
        super(MobEffectCategory.BENEFICIAL, color); // Gold color
    }

    @Override
    public Component getDescriptionLine(MobEffectInstance instance) {
        int amp = instance.getAmplifier() + 1;
        return Component.translatable("tooltip.power_of_the_void.kinetic_plating_description", amp).withStyle(ChatFormatting.BLUE);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!(entity instanceof Player player)) return;
        entity.clearFire();
        entity.displayFireAnimation();
        player.getCapability(BatteryCapabilityProvider.PLAYER_CHARGE).ifPresent(battery -> {
            // Reduce damage taken (scaling with charge)
            float damageReduction = getReduction(battery, amplifier);
            //System.out.println("Amp: "+ amplifier);
            player.getPersistentData().putFloat("KineticPlatingReduction", damageReduction);
        });
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // Effect applies every tick
    }

    public int getMinReduction (int amp) {
        return 14 + 2 * amp;
    }

    public int getMaxReduction (int amp) {
        return 70 + 10 * amp;
    }

    public float getReduction (BatteryCapability battery, int amp) {
        // 实际伤害减免 = 最小伤害减免 +（最大伤害减免 - 最小伤害减免） × 当前电量
        int min = getMinReduction(amp);
        int max = getMaxReduction(amp);
        return min + (max - min) * battery.getCharge() / 100.0f;
    }
}
