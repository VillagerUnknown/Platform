package me.villagerunknown.platform.cmd;

import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.feature.commandsFeature;
import me.villagerunknown.platform.util.MessageUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class reloadCommand {
	
	public static void execute() {
		Platform.reload();
		
		String message = MessageUtil.formComment( "Platform is reloading config data..." );
		commandsFeature.COMMAND_SOURCE.sendMessage( Text.of( message ) );
		commandsFeature.playSound(SoundEvents.ENTITY_VILLAGER_YES);
	}
	
}
