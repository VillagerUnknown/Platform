package me.villagerunknown.platform.cmd;

import me.villagerunknown.platform.feature.commandsFeature;
import me.villagerunknown.platform.util.MessageUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

public class helpCommand {
	
	public static void execute() {
		Component message = MessageUtil.formClickableMessage(
				"Click here to goto Github for help!",
				"https://github.com/VillagerUnknown/Platform/issues"
		);
		
		commandsFeature.COMMAND_SOURCE.sendSystemMessage( message );
		commandsFeature.playSound(SoundEvents.VILLAGER_YES);
	}
	
}
