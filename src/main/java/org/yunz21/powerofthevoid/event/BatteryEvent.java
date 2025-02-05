package org.yunz21.powerofthevoid.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.yunz21.powerofthevoid.capabilities.BatteryCapabilityProvider;
import org.yunz21.powerofthevoid.network.ModMessages;
import org.yunz21.powerofthevoid.network.packet.BatteryDataSyncPacket;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;

@Mod.EventBusSubscriber
public class BatteryEvent {
    private static boolean wasRedlineActive = false;  // Track if Redline was active last tick

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;

            player.getCapability(BatteryCapabilityProvider.PLAYER_CHARGE).ifPresent(battery -> {
                boolean hasRedline = player.hasEffect(VMobEffectRegistry.REDLINE.get());

                // Only update if Redline status has changed
                if (wasRedlineActive && !hasRedline) {
                    // Effect was removed
                    battery.setMaxCharge(80);
                    System.out.println("[DEBUG] Redline Removed - MaxCharge Set to 80");

                    // Send sync update
                    if (player instanceof ServerPlayer serverPlayer) {
                        ModMessages.sendToPlayer(new BatteryDataSyncPacket(battery.getCharge(),80), (ServerPlayer) player);
                    }
                }

                // If Redline just got applied, update max charge
                if (!wasRedlineActive && hasRedline) {
                    battery.setMaxCharge(100);
                    System.out.println("[DEBUG] Redline Applied - MaxCharge Set to 100");

                    if (player instanceof ServerPlayer serverPlayer) {
                        ModMessages.sendToPlayer(new BatteryDataSyncPacket(battery.getCharge(), 100), (ServerPlayer) player);
                        //ModMessages.sendToPlayer(new BatteryDataSyncPacket(battery.getCharge()), (ServerPlayer) player);
                    }
                }

                // Update the last known state
                wasRedlineActive = hasRedline;
            });
        }
    }
}