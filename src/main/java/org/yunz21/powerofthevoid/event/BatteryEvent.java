package org.yunz21.powerofthevoid.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.yunz21.powerofthevoid.capabilities.BatteryCapabilityProvider;
import org.yunz21.powerofthevoid.network.ModMessages;
import org.yunz21.powerofthevoid.network.packet.BatteryDataSyncPacket;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;
import org.yunz21.powerofthevoid.registries.VSoundRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber
public class BatteryEvent {
    private static boolean wasRedlineActive = false;  // Track if Redline was active last tick
    private static final Map<UUID, Vec3> previousPositions = new HashMap<>();
    private static final Map<UUID, Float> accumulatedDistance = new HashMap<>();
    private static final int CHECK_INTERVAL = 5; // Check every 5 ticks
    private static int tickCounter = 0;
    private static boolean fullCharge = false;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;

            player.getCapability(BatteryCapabilityProvider.PLAYER_CHARGE).ifPresent(battery -> {
                boolean hasRedline = player.hasEffect(VMobEffectRegistry.REDLINE.get());


                if (battery.getPercent() == 100) {
                    if (!fullCharge){
                        //play sound
                        player.level().playSound(null, player.blockPosition(), VSoundRegistry.FULL_CHARGE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                    }
                    fullCharge = true;
                }

                // Only update if Redline status has changed
                if (wasRedlineActive && !hasRedline) {
                    // Effect was removed
                    battery.setMaxCharge(80);
                    //System.out.println("[DEBUG] Redline Removed - MaxCharge Set to 80");

                    float percent = battery.getPercent();
                    float curCharge = battery.getCharge();
                    int finalCharge = Math.min((int) (curCharge * percent / 100), 80);
                    battery.setCharge(finalCharge);
                    battery.setPercent(0.0f);

                    // Send sync update
                    if (player instanceof ServerPlayer) {
                        fullCharge = false;
                        player.level().playSound(null, player.blockPosition(), VSoundRegistry.REDLINE_EFFECT_END.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                        ModMessages.sendToPlayer(new BatteryDataSyncPacket(finalCharge,80, battery.getPercent()), (ServerPlayer) player);
                        //System.out.println("[DEBUG]Redline Removed - MaxCharge Set to 80, Charge to "+finalCharge);
                    }
                }

                // If Redline just got applied, update max charge
                if (!wasRedlineActive && hasRedline) {
                    battery.setMaxCharge(100);
                    //System.out.println("[DEBUG] Redline Applied - MaxCharge Set to 100");

                    if (player instanceof ServerPlayer) {
                        ModMessages.sendToPlayer(new BatteryDataSyncPacket(battery.getCharge(), 100, battery.getPercent()), (ServerPlayer) player);
                    }
                }

                // Add percent when charge over 80%
                if (hasRedline) {
                    battery.changePercent();
                    //System.out.println(battery.getCharge());
                    if (player instanceof ServerPlayer) {
                        ModMessages.sendToPlayer(new BatteryDataSyncPacket(battery.getCharge(), 100, battery.getPercent()), (ServerPlayer) player);
                    }
                }

                if (hasRedline && battery.getPercent() != 100.0f) {
                    battery.consumeCharge(0.1f);
                    if (player instanceof ServerPlayer) {
                        ModMessages.sendToPlayer(new BatteryDataSyncPacket(battery.getCharge(), 100, battery.getPercent()), (ServerPlayer) player);
                    }
                }
                // Update the last known state
                wasRedlineActive = hasRedline;
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerMove(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return; // Only run at the end of the tick
        Player player = event.player;

        // Get current position
        Vec3 currentPos = player.position();
        UUID playerId = player.getUUID();

        // Only check movement every 5 ticks to account for small steps
        tickCounter++;
        if (tickCounter % CHECK_INTERVAL != 0) return;

        // Get previous position (if exists)
        Vec3 prevPos = previousPositions.getOrDefault(playerId, currentPos);

        // Calculate distance traveled
        float dx = (float) (currentPos.x - prevPos.x);
        float dz = (float) (currentPos.z - prevPos.z);
        float dy = player.isFallFlying() ? (float) (currentPos.y - prevPos.y) : 0; // Consider Y movement only if elytra flying

        float distanceMoved = (float) Math.sqrt(dx * dx + dz * dz + dy * dy);

        // Track accumulated distance
        float totalDistance = accumulatedDistance.getOrDefault(playerId, 0.0f) + distanceMoved;

        if (totalDistance >= 0.83) { // Gain 1 charge per 1.83 blocks moved
            float gainedCharge =  totalDistance / 0.83f; // How many full charges gained
            totalDistance %= 0.83f; // Keep remaining distance

            if (player instanceof ServerPlayer) {   // gain charge when moving
                player.getCapability(BatteryCapabilityProvider.PLAYER_CHARGE).ifPresent(battery -> {
                    battery.addCharge(gainedCharge);
                    //System.out.println("[DEBUG] Player " + player.getName().getString() + " gained " + gainedCharge + " charge.");
                    ModMessages.sendToPlayer(new BatteryDataSyncPacket(battery.getCharge(), battery.getMaxCharge(), battery.getPercent()), (ServerPlayer) player);
                });
            }
        } else if (distanceMoved < 0.1) {
            float lostCharge = CHECK_INTERVAL / 20.0f;
            if (player instanceof ServerPlayer) {   // lost charge if not moving
                player.getCapability(BatteryCapabilityProvider.PLAYER_CHARGE).ifPresent(battery -> {
                    battery.consumeCharge(lostCharge);
                    //System.out.println("[DEBUG] Player " + player.getName().getString() + " lost " + lostCharge + " charge.");
                    ModMessages.sendToPlayer(new BatteryDataSyncPacket(battery.getCharge(), battery.getMaxCharge(), battery.getPercent()), (ServerPlayer) player);
                });
            }
        }

        // Update stored position and accumulated distance
        previousPositions.put(playerId, currentPos);
        accumulatedDistance.put(playerId, totalDistance);
    }
}