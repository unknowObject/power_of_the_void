package org.yunz21.powerofthevoid.effects;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.effect.CustomDescriptionMobEffect;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.yunz21.powerofthevoid.capabilities.BatteryCapabilityProvider;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;

import java.awt.*;

public class RedlineEffect extends CustomDescriptionMobEffect {
    public static final float SPELL_POWER_PER_LEVEL = .05f;
    public static final float ATTACK_SPEED_PER_LEVEL = .05f;

    public RedlineEffect(MobEffectCategory mobEffectCategory, int color) {
        super(MobEffectCategory.BENEFICIAL, color);
    }

    @Override
    public Component getDescriptionLine(MobEffectInstance instance) {
        int amp = instance.getAmplifier() + 1;
        return Component.translatable("tooltip.power_of_the_void.redline_description", amp).withStyle(ChatFormatting.BLUE);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity livingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(livingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

//    @SubscribeEvent
//    public void onEffectUpdated(LivingEvent.LivingTickEvent event) {
//        if (event.getEntity() instanceof Player player) {
//            player.getCapability(BatteryCapabilityProvider.PLAYER_CHARGE).ifPresent(battery -> {
//                boolean hasRedline = player.hasEffect(VMobEffectRegistry.REDLINE.get());
//
//                if (hasRedline) {
//                    MobEffectInstance effect = player.getEffect(VMobEffectRegistry.REDLINE.get());
//                    battery.setRedlineActive(true);//, effect.getDuration());
//                    battery.setMaxCharge();
//                    player.sendSystemMessage(Component.literal("Redline on!"));
//                } else {
//                    battery.setRedlineActive(false);//, 0);
//                    battery.setMaxCharge();
//                    player.sendSystemMessage(Component.literal("Redline off!"));
//                }
//            });
//        }
//    }
}
