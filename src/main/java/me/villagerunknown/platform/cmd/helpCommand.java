package me.villagerunknown.platform.cmd;

import me.villagerunknown.platform.feature.commandsFeature;
import me.villagerunknown.platform.util.MessageUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class helpCommand {
	
	public static void execute() {
		Text message = MessageUtil.formClickableMessage(
				"Click here to join VillagerUnknown's Discord Server for help!",
				"https://discord.gg/PXjXh6M38H"
		);
		
		commandsFeature.COMMAND_SOURCE.sendMessage( message );
		commandsFeature.playSound(SoundEvents.ENTITY_VILLAGER_YES);
	}
	
}
