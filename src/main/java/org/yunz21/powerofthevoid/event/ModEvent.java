package org.yunz21.powerofthevoid.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.yunz21.powerofthevoid.capabilities.BatteryCapabilityProvider;
import org.yunz21.powerofthevoid.network.ModMessages;
import org.yunz21.powerofthevoid.network.packet.BatteryDataSyncPacket;

import static org.yunz21.powerofthevoid.PowerOfTheVoid.MODID;

@Mod.EventBusSubscriber(modid = MODID)

public class ModEvent {

    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event){
        event.register(BatteryCapabilityProvider.class);
        System.out.println("[DEBUG]Capability is successfully registered!");
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(BatteryCapabilityProvider.PLAYER_CHARGE).ifPresent(battery -> {
                    ModMessages.sendToPlayer(new BatteryDataSyncPacket(battery.getCharge(), battery.getMaxCharge()), player);
                });
            }
        }
    }
}
