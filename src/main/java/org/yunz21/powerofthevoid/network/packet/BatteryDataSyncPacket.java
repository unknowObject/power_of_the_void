package org.yunz21.powerofthevoid.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.yunz21.powerofthevoid.client.ClientBatteryData;

import java.util.function.Supplier;

public class BatteryDataSyncPacket {
    // 传递的信息电量
    private final int battery;
    private final int maxCharge;
    // 构建这个包时候传入饥渴值
    public BatteryDataSyncPacket(int battery, int maxCharge) {
        this.battery = battery;
        this.maxCharge = maxCharge;
    }
    // 从buf中恢复这个包
    public BatteryDataSyncPacket(FriendlyByteBuf buf) {
        this.battery = buf.readInt();
        this.maxCharge = buf.readInt();
    }
    // 转为buf
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(battery);
        buf.writeInt(maxCharge);
    }
    // 客户端处理
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            // 给我们之前写的ClientThirstData的类的饥渴值进行赋值。
            ClientBatteryData.set(battery, maxCharge);
        });
        return true;
    }
}
