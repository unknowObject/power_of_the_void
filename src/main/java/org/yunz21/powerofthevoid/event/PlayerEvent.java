package org.yunz21.powerofthevoid.event;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import it.unimi.dsi.fastutil.longs.AbstractLongList;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import org.yunz21.powerofthevoid.PowerOfTheVoid;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;
import net.minecraftforge.event.entity.living.PotionColorCalculationEvent;
import net.minecraftforge.event.TickEvent;

import java.util.Iterator;
import java.util.Objects;


@Mod.EventBusSubscriber
public class PlayerEvent {

    @SubscribeEvent
    public static void onLivingTakeAttack(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.hasEffect(VMobEffectRegistry.INVULNERABLE.get())) {
                event.setCanceled(true); // Cancel the attack event
            }
        }

        if (event.getEntity() instanceof Player player) {
            // Check if the player has "Mesmer Skin"
            if (player.hasEffect(VMobEffectRegistry.MESMER_SKIN.get())) {
                MobEffectInstance mesmerSkin = player.getEffect(VMobEffectRegistry.MESMER_SKIN.get());

                // Check if the player is "Invulnerable"
                if (player.hasEffect(VMobEffectRegistry.INVULNERABLE.get())) {
                    // Prevent damage reduction for "Mesmer Skin"
                    return;
                }

                // Reduce the level of "Mesmer Skin" and apply "Invulnerable"
                int currentLevel = mesmerSkin.getAmplifier();
                if (currentLevel > 0) {
                    player.removeEffect(VMobEffectRegistry.MESMER_SKIN.get()); // Remove the old instance

                    player.addEffect(new MobEffectInstance(VMobEffectRegistry.MESMER_SKIN.get(), MobEffectInstance.INFINITE_DURATION, currentLevel - 1));
                    player.addEffect(new MobEffectInstance(VMobEffectRegistry.INVULNERABLE.get(), 20)); // 1 second invulnerability (20 ticks)

                    if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                        // Apply damage to the attacker
                        attacker.hurt(event.getSource(), 2); // Adjust the damage value as needed

                        // Apply the "Stunned" effect to the attacker
                        attacker.addEffect(new MobEffectInstance(VMobEffectRegistry.STUNNED.get(), 80, 0)); // 4 seconds (80 ticks) stun
                    }
                }

                // If "Mesmer Skin" level is now 0, remove it entirely
                if (currentLevel - 1 <= 0) {
                    player.removeEffect(VMobEffectRegistry.MESMER_SKIN.get());
                }

                // Cancel the damage
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            if (attacker.hasEffect(VMobEffectRegistry.ROAR.get())) {
                int amplifier = Objects.requireNonNull(attacker.getEffect(VMobEffectRegistry.ROAR.get())).getAmplifier();
                float boostMultiplier = 1.0F + (0.4F * (amplifier + 1)); // 40% per level
                event.setAmount(event.getAmount() * boostMultiplier); // Amplify damage
            }
        }
    }

    @SubscribeEvent
    public static void onApplyPotion(MobEffectEvent.Applicable event) {
        //System.out.println("potion effect detected!");
        //
        if (event.getEntity() instanceof Player player) {
            // Check if the entity has the "Invulnerable" effect
            if (player.hasEffect(VMobEffectRegistry.INVULNERABLE.get()) || player.hasEffect(VMobEffectRegistry.MESMER_SKIN.get())) {
                // Prevent non-beneficial effects from being applied
                if (!event.getEffectInstance().getEffect().isBeneficial()) {
                    event.setResult(net.minecraftforge.eventbus.api.Event.Result.DENY);
                }
            }
        }

        // Only run on the server to avoid redundancy
//        if (event.phase != TickEvent.Phase.START || event.player.level().isClientSide()) {
//            return;
//        }
//
//        Player player = event.player;
//
//        // Check if the player has the "Invulnerable" effect
//        if (player.hasEffect(VMobEffectRegistry.INVULNERABLE.get())) {
//            Iterator<MobEffectInstance> effects = player.getActiveEffects().iterator();
//
//            while (effects.hasNext()) {
//                MobEffectInstance effect = effects.next();
//
//                // Remove non-beneficial effects
//                if (!effect.getEffect().isBeneficial()) {
//                    player.removeEffect(effect.getEffect());
//                }
//            }
//        }
    }
//
//    @SubscribeEvent
//    public static void onLivingDamage(LivingAttackEvent event) {
//        if (event.getEntity() instanceof Player player) {
//            // Check if the player has "Mesmer Skin"
//            if (player.hasEffect(VMobEffectRegistry.MESMER_SKIN.get())) {
//                MobEffectInstance mesmerSkin = player.getEffect(VMobEffectRegistry.MESMER_SKIN.get());
//
//                // Check if the player is "Invulnerable"
//                if (player.hasEffect(VMobEffectRegistry.INVULNERABLE.get())) {
//                    // Prevent damage reduction for "Mesmer Skin"
//                    return;
//                }
//
//                // Reduce the level of "Mesmer Skin" and apply "Invulnerable"
//                int currentLevel = mesmerSkin.getAmplifier();
//                if (currentLevel > 0) {
//                    player.removeEffect(VMobEffectRegistry.MESMER_SKIN.get()); // Remove the old instance
//
//                    player.addEffect(new MobEffectInstance(VMobEffectRegistry.MESMER_SKIN.get(), MobEffectInstance.INFINITE_DURATION, currentLevel - 1));
//                    player.addEffect(new MobEffectInstance(VMobEffectRegistry.INVULNERABLE.get(), 20)); // 1 second invulnerability (20 ticks)
//
//                    if (event.getSource().getEntity() instanceof LivingEntity attacker) {
//                        // Apply damage to the attacker
//                        attacker.hurt(event.getSource(), 2); // Adjust the damage value as needed
//
//                        // Apply the "Stunned" effect to the attacker
//                        attacker.addEffect(new MobEffectInstance(VMobEffectRegistry.STUNNED.get(), 80, 0)); // 4 seconds (80 ticks) stun
//                    }
//                }
//
//                // If "Mesmer Skin" level is now 0, remove it entirely
//                if (currentLevel - 1 <= 0) {
//                    player.removeEffect(VMobEffectRegistry.MESMER_SKIN.get());
//                }
//
//                // Cancel the damage
//                event.setCanceled(true);
//            }
//        }
//    }
}
