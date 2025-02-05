package org.yunz21.powerofthevoid.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.yunz21.powerofthevoid.capabilities.BatteryCapabilityProvider;

public class GetPercentCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("getpercent").executes((context)-> {
            context.getSource().getPlayer().getCapability(BatteryCapabilityProvider.PLAYER_CHARGE).ifPresent((charge)-> {
                context.getSource().sendSuccess(()-> Component.literal("Percent: " + charge.getPercent()), false);
            });
            return 0;
        }));
    }
}
