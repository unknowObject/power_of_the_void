package org.yunz21.powerofthevoid.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.yunz21.powerofthevoid.network.packet.BatteryDataSyncPacket;

import static org.yunz21.powerofthevoid.PowerOfTheVoid.MODID;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetID = 0;
    private static int id() {return packetID++; }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(BatteryDataSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BatteryDataSyncPacket::new)
                .encoder(BatteryDataSyncPacket::toBytes)
                .consumerMainThread(BatteryDataSyncPacket::handle)
                .add();

//        net.messageBuilder(MaxChargeSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
//                .decoder(MaxChargeSyncPacket::new)
//                .encoder(MaxChargeSyncPacket::toBytes)
//                .consumerMainThread(MaxChargeSyncPacket::handle)
//                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
