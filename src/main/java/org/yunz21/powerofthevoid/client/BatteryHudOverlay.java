package org.yunz21.powerofthevoid.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.yunz21.powerofthevoid.PowerOfTheVoid;

public class BatteryHudOverlay {
    private static final ResourceLocation FILLED_BATTERY = new ResourceLocation(PowerOfTheVoid.MODID,
            "textures/gui/battery_filled.png");
    private static final ResourceLocation EMPTY_BATTERY = new ResourceLocation(PowerOfTheVoid.MODID,
            "textures/gui/battery_empty.png");

    // 通过这个属性进行绘制，这个是一个IguiOverLay的接口，实现这个接口。
    // 通过lammbd表达式实现。
    public static final IGuiOverlay HUD_BATTERY = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        // 通过宽高获得绘制的x，y
        int x = screenWidth - 40;
        int y = screenHeight - 100 + 15;
        int width = 18; // 能量条宽度
        int height = 60; // 能量条高度

        //设置绘制的信息
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_BATTERY);

        // 贴图，x坐标，y坐标，z坐标（图层 越高越不容易被遮盖）
        guiGraphics.blit(EMPTY_BATTERY, x, y, 90,0,0, width, height,
                width, height);

        RenderSystem.setShaderTexture(0, FILLED_BATTERY);

        int normalHeight = (int) (height * 0.57); // 56% height for 80% charge
        int redlineHeight = height - normalHeight;
        int charge = ClientBatteryData.getPlayerBattery();// * 8 / 10;
        int chargeHeight;

        // If charge below 80% render under red line
        // If charge above 80% render over red line
        if (charge <= 80) {
            chargeHeight = (int) ((charge / 80.0) * normalHeight);
        } else {
            chargeHeight = normalHeight + (int) (((charge - 80) / 20.0) * redlineHeight);
        }

        // 根据电量绘制HUD。
        guiGraphics.blit(FILLED_BATTERY, x, y + (height-chargeHeight), 90,0, height - chargeHeight, width, chargeHeight,
                width, height);


    };
}
