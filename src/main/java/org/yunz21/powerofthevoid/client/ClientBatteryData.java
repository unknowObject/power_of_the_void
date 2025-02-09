package org.yunz21.powerofthevoid.client;

public class ClientBatteryData {
    private static float playerBattery;
    private static int playerMaxCharge;
    private static float playerPercent;

    /***
     * @param battery
     */
    // 设置饥渴值
    public static void set(float battery, int maxCharge, float playerPercent) {
        ClientBatteryData.playerBattery = battery;
        ClientBatteryData.playerMaxCharge = maxCharge;
        ClientBatteryData.playerPercent = playerPercent;
    }
    // 获得饥渴值的数值
    public static float getPlayerBattery() {
        return playerBattery;
    }

    public static int getPlayerMaxCharge() {
        return  playerMaxCharge;
    }

    public static float getPlayerPercent() {
        return  playerPercent;
    }
}
