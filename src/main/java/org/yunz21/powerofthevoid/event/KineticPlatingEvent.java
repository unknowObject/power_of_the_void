package org.yunz21.powerofthevoid.event;


import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ISystemReportExtender;
import net.minecraftforge.fml.common.Mod;
import org.yunz21.powerofthevoid.capabilities.BatteryCapability;
import org.yunz21.powerofthevoid.capabilities.BatteryCapabilityProvider;
//import org.yunz21.powerofthevoid.config.ConfigForge;
import org.yunz21.powerofthevoid.effects.KineticPlatingEffect;
import org.yunz21.powerofthevoid.network.ModMessages;
import org.yunz21.powerofthevoid.network.packet.BatteryDataSyncPacket;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;

import javax.annotation.Syntax;
import java.lang.reflect.Field;

import static net.minecraft.world.effect.MobEffects.FIRE_RESISTANCE;

@Mod.EventBusSubscriber
public class KineticPlatingEvent {
    @SubscribeEvent
    public static void onLivingKnockback(LivingKnockBackEvent event) {
        if (event.getEntity() instanceof Player player && player.hasEffect(VMobEffectRegistry.KINETIC_PLATING.get())) {
            event.setCanceled(true); // Remove knockback
            //System.out.println("[DEBUG] Knockback prevented by Kinetic Plating!");
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        // 检查攻击者是否为玩家
        if (event.getSource().getDirectEntity() instanceof Player player) {
            // 检查玩家是否拥有 kinetic_plating 和 赤限 效果
            if (player.hasEffect(VMobEffectRegistry.KINETIC_PLATING.get()) && player.hasEffect(VMobEffectRegistry.REDLINE.get())) {
                // 将伤害值翻倍
                event.setAmount(event.getAmount() * 2);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingTakeAttack(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.hasEffect(VMobEffectRegistry.KINETIC_PLATING.get())) {
                player.getCapability(BatteryCapabilityProvider.PLAYER_CHARGE).ifPresent(battery -> {
                    float reduction = player.getPersistentData().getFloat("KineticPlatingReduction");
                    if (reduction == 100) {
                        DamageSource source = event.getSource();
                        boolean isNotPhysical = source == player.damageSources().dragonBreath() ||
                                source == player.damageSources().drown() ||
                                source == player.damageSources().magic() ||
                                source == player.damageSources().starve() ||
                                source == player.damageSources().wither() ||
                                source == player.damageSources().lightningBolt();
                        if (!isNotPhysical) {
                            event.setCanceled(true);
                        }
                        //System.out.println("[DEBUG]Completely canceled");
                    }
                });

                DamageSource source = event.getSource();

                // Cancel fire/fall-based damage
                if (//source == player.damageSources().lava() ||
                        source == player.damageSources().fall() ||
                        //source == player.damageSources().inFire() ||
                        source == player.damageSources().onFire() //||
                        //source == player.damageSources().hotFloor()
                ) {
                    event.setCanceled(true);
                    //System.out.println("[DEBUG] Kinetic Plating granted immunity!");
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        if (player.hasEffect(VMobEffectRegistry.KINETIC_PLATING.get())) {
            player.getCapability(BatteryCapabilityProvider.PLAYER_CHARGE).ifPresent(battery -> {
                DamageSource source = event.getSource();

                boolean isNotPhysical = source == player.damageSources().dragonBreath() ||
                        source == player.damageSources().drown() ||
                        source == player.damageSources().magic() ||
                        source == player.damageSources().starve() ||
                        source == player.damageSources().wither() ||
                        source == player.damageSources().lightningBolt();

                if (isNotPhysical) {
                    return; // Apply no damage reduction when damage source is not physical
                }
                float damage = event.getAmount();
                float reduction = player.getPersistentData().getFloat("KineticPlatingReduction");
                System.out.println("[DEBUG]Reduction percentage: " + reduction);

                float reducedDamage = damage * (1 - reduction/100);
                event.setAmount(reducedDamage);

                // Reduce charge based on damage taken
                float chargeLoss = (int) (event.getAmount() / 1.5f); // Scale charge loss
                chargeLoss = Math.max(chargeLoss, 1.0f);
                chargeLoss = Math.min(chargeLoss, 5.0f);
                battery.consumeCharge(chargeLoss);
                if (player instanceof ServerPlayer) {
                    ModMessages.sendToPlayer(new BatteryDataSyncPacket(battery.getCharge(), battery.getMaxCharge(), battery.getPercent()), (ServerPlayer) player);
                    //System.out.println("Being hit, lost charge: "+chargeLoss);
                    //System.out.println("Damage reduction: "+reduction);
                }

                // Gain mana based on damage
                int manaGain = (int) (damage / 4);
                player.addEffect(new MobEffectInstance(MobEffectRegistry.INSTANT_MANA.get(), manaGain + 1));
                //System.out.println("Being hit, gain mana: " + (manaGain + 1));

                //System.out.println("[DEBUG] Kinetic Plating reduced damage to " + reducedDamage + ", lost " + chargeLoss + " charge, gained " + manaGain + " mana.");
            });

            event.setCanceled(false);
        }
    }

//    private static final ConfigForge configuration = new ConfigForge();
//
//    @SubscribeEvent
//    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
//
//        // If entity has fire resistance it will extinguish them right away when on
//        // fire.
//        boolean fireResistance = event.getEntity().hasEffect(VMobEffectRegistry.INVULNERABLE.get()) ||
//                event.getEntity().hasEffect(VMobEffectRegistry.KINETIC_PLATING.get())||
//                event.getEntity().hasEffect(VMobEffectRegistry.MESMER_SKIN.get());
//        if (configuration.shouldFireResExtinguish() && !event.getEntity().level().isClientSide && event.getEntity().isOnFire() && fireResistance) {
//
//            event.getEntity().extinguishFire();
//        }
//    }
}
