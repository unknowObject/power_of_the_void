package org.yunz21.powerofthevoid.event;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.yunz21.powerofthevoid.command.GetBatteryChargeCommand;
import org.yunz21.powerofthevoid.command.GetMaxBatteryChargeCommand;
import org.yunz21.powerofthevoid.command.GetPercentCommand;
import org.yunz21.powerofthevoid.command.GetRedlineDurationCommand;

import static org.yunz21.powerofthevoid.PowerOfTheVoid.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MODID)
public class CommandRegisterEvent {

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        GetBatteryChargeCommand.register(event.getDispatcher());
        GetMaxBatteryChargeCommand.register(event.getDispatcher());
        GetRedlineDurationCommand.register(event.getDispatcher());
        GetPercentCommand.register(event.getDispatcher());
    }
}
