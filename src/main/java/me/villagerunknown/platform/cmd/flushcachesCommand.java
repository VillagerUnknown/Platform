package me.villagerunknown.platform.cmd;

import me.villagerunknown.platform.feature.commandsFeature;
import me.villagerunknown.platform.feature.playerCacheFeature;
import me.villagerunknown.platform.util.MessageUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class flushcachesCommand {
	
	public static void execute() {
		playerCacheFeature.emptyCache();
		
		String message = MessageUtil.formComment( "Platform flushed caches..." );
		commandsFeature.COMMAND_SOURCE.sendMessage( Text.of( message ) );
		commandsFeature.playSound(SoundEvents.ENTITY_VILLAGER_YES);
	}
	
}
