package org.yunz21.powerofthevoid.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.yunz21.powerofthevoid.capabilities.BatteryCapabilityProvider;

public class GetRedlineDurationCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("getredlineduration").executes((context)-> {
            context.getSource().getPlayer().getCapability(BatteryCapabilityProvider.PLAYER_CHARGE).ifPresent((battery)-> {
                context.getSource().sendSuccess(()-> Component.literal("Redline Duration: " + battery.getRedlineDuration()), false);
            });
            return 0;
        }));
    }
}
