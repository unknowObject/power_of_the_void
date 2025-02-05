package org.yunz21.powerofthevoid.client;

public class ClientBatteryData {
    private static int playerBattery;
    private static int playerMaxCharge;

    /***
     * @param battery
     */
    // 设置饥渴值
    public static void set(int battery, int maxCharge) {
        ClientBatteryData.playerBattery = battery;
        ClientBatteryData.playerMaxCharge = maxCharge;
    }
    // 获得饥渴值的数值
    public static int getPlayerBattery() {
        return playerBattery;
    }

    public static int getPlayerMaxCharge() {
        return  playerMaxCharge;
    }
}
