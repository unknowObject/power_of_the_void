package org.yunz21.powerofthevoid.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BatteryCapabilityProvider implements ICapabilityProvider, INBTSerializable {
    public static Capability<BatteryCapability> PLAYER_CHARGE = CapabilityManager.get(new CapabilityToken<>(){});

    private BatteryCapability battery;
    private LazyOptional<BatteryCapability> lazyOptional = LazyOptional.of(()->this.battery);

    public BatteryCapabilityProvider() {
        this.battery = new BatteryCapability();
    }

    @Override
    public Tag serializeNBT() {
        var tag = new CompoundTag();
        battery.saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        battery.loadNBTData((CompoundTag) nbt);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull final Capability<T> cap, final @Nullable Direction side){
        if (cap == PLAYER_CHARGE) {
            return lazyOptional.cast();
        } return LazyOptional.empty();

        //return getCapability(cap);
        //return cap == ModCapabilities.BATTERY_CAPABILITY ? lazyOptional.cast() : LazyOptional.empty();
    }

    /*
     * Purely added as a bouncer to sided version, to make modders stop complaining about calling with a null value.
     * This should never be OVERRIDDEN, modders should only ever implement the sided version.
     */
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull final Capability<T> cap) {
        if(cap == PLAYER_CHARGE){
            return lazyOptional.cast();
        } else {
            return lazyOptional.empty();
        }
    }

    public void invalidate() {
        lazyOptional.invalidate();
    }
}
