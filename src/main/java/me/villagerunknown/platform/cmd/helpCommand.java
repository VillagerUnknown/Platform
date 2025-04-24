package me.villagerunknown.platform.cmd;

import me.villagerunknown.platform.feature.commandsFeature;
import me.villagerunknown.platform.util.MessageUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class helpCommand {
	
	public static void execute() {
		Text message = MessageUtil.formClickableMessage(
				"Click here to goto Github for help!",
				"https://github.com/VillagerUnknown/Platform/issues"
		);
		
		commandsFeature.COMMAND_SOURCE.sendMessage( message );
		commandsFeature.playSound(SoundEvents.ENTITY_VILLAGER_YES);
	}
	
}
