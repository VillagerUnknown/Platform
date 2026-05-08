package me.villagerunknown.platform.cmd;

import me.villagerunknown.platform.feature.commandsFeature;
import me.villagerunknown.platform.feature.playerCacheFeature;
import me.villagerunknown.platform.util.MessageUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

public class flushcachesCommand {
	
	public static void execute() {
		playerCacheFeature.emptyCache();
		
		String message = MessageUtil.formComment( "Platform flushed caches..." );
		commandsFeature.COMMAND_SOURCE.sendSystemMessage( Component.nullToEmpty( message ) );
		commandsFeature.playSound(SoundEvents.VILLAGER_YES);
	}
	
}