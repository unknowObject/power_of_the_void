package org.yunz21.powerofthevoid.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;

@AutoRegisterCapability
public class BatteryCapability{
    private int charge;
    private int maxChargeDefault = 80;
    private int maxChargeRedline = 100;
    private boolean isRedlineActive;
    private int redlineDuration; // Track Redline duration
    private int percent;
    private int maxCharge;

    public BatteryCapability(){
        this.charge = 0;
        this.maxChargeDefault = 80;
        this.maxChargeRedline = 100;
        this.isRedlineActive = false;
        this.redlineDuration = 0;
        this.percent = 0;
        this.maxCharge = 80;
    }

    public int getCharge() {
        return charge;
    }

    public int getPercent() {
        return percent;
    }

    public void setMaxCharge(int amount) {
        maxCharge = amount;
        System.out.println("[DEBUG]Max Charge set as:"+amount);
    }

    public int getMaxCharge() {
        return maxCharge;
    }

    public void addCharge(int amount) {
        charge = Math.min(getMaxCharge(), charge + amount);
    }

    public void consumeCharge(int amount) {
        charge = Math.max(0, charge - amount);
    }

    public boolean hasCharge(int amount) {
        return charge >= amount;
    }

    public void addPercent(int amount) {
        percent = Math.min(100, percent + amount);
    }

    public void consumePercent(int amount) {
        percent = Math.max(0, percent - amount);
    }

    public void saveNBTData(CompoundTag compoundTag) {
        compoundTag.putInt("BatteryCharge", charge);
        compoundTag.putInt("MaxCharge", maxCharge);
        compoundTag.putBoolean("IsRedlineActive", isRedlineActive);
        compoundTag.putInt("RedlineDuration", redlineDuration);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        charge = compoundTag.getInt("BatteryCharge");
        maxCharge = compoundTag.contains("MaxCharge") ? compoundTag.getInt("MaxCharge") : 80;
        isRedlineActive = compoundTag.getBoolean("IsRedlineActive");
        redlineDuration = compoundTag.getInt("RedlineDuration");
    }
}
