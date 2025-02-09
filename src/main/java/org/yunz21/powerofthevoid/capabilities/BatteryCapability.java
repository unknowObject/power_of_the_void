package org.yunz21.powerofthevoid.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;
import org.yunz21.powerofthevoid.registries.VMobEffectRegistry;

@AutoRegisterCapability
public class BatteryCapability{
    private float charge;
    private int maxChargeDefault = 80;
    private int maxChargeRedline = 100;
    private boolean isRedlineActive;
    private int redlineDuration; // Track Redline duration
    private float percent;
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

    public float getCharge() {
        return charge;
    }

    public float getPercent() {
        return percent;
    }

    public void setMaxCharge(int amount) {
        maxCharge = amount;
        System.out.println("[DEBUG]Max Charge set as:"+amount);
    }

    public int getMaxCharge() {
        return maxCharge;
    }

    public void addCharge(float amount) {
        charge = Math.min(getMaxCharge(), charge + amount);
    }

    public void consumeCharge(float amount) {
        if (getPercent() != 100) {
            charge = Math.max(0, charge - amount);
        } return;
    }

    public boolean hasCharge(int amount) {
        return charge > amount;
    }

    public void changePercent() {
        float charge = getCharge();
        float chargeDiff = charge - 80.0f;
        float incRate;

        if (chargeDiff >= 0){
            // 100% incRate when fully charged, linear
            incRate = chargeDiff / 20;
            percent = Math.min(100.0f, percent + (100 / (getRedlineDuration() / 5.0f)) * incRate);
        }else {
            // -100% incRate when not charged, linear
            incRate = chargeDiff / 80;
            percent = Math.max(0.0f, percent + (100 / (getRedlineDuration() / 5.0f)) * incRate);
            //System.out.println(chargeDiff);
        }
    }

    public void setPercent(float amount) {
        percent = amount;
    }

    public void setCharge(float amount) {
        charge = amount;
    }

    public void setRedlineDuration(int amount) {
        redlineDuration = amount;
    }

    public int getRedlineDuration() {
        return redlineDuration;
    }

    public void saveNBTData(CompoundTag compoundTag) {
        compoundTag.putFloat("BatteryCharge", charge);
        compoundTag.putInt("MaxCharge", maxCharge);
        compoundTag.putFloat("Percent", percent);
        compoundTag.putBoolean("IsRedlineActive", isRedlineActive);
        compoundTag.putInt("RedlineDuration", redlineDuration);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        charge = compoundTag.getFloat("BatteryCharge");
        maxCharge = compoundTag.contains("MaxCharge") ? compoundTag.getInt("MaxCharge") : 80;
        percent = compoundTag.contains("Percent") ? compoundTag.getFloat("Percent") : 0.0f;
        isRedlineActive = compoundTag.getBoolean("IsRedlineActive");
        redlineDuration = compoundTag.getInt("RedlineDuration");
    }
}
