package me.villagerunknown.platform.feature;

import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.util.MessageUtil;
import me.villagerunknown.platform.util.TimeUtil;
import me.villagerunknown.platform.util.WeatherUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class sleepNoticeFeature {
	
	public static void execute() {
		ServerTickEvents.START_SERVER_TICK.register(minecraftServer -> {
			if( Platform.CONFIG.enableSleepNotice ) {
				long time = TimeUtil.getTimeOfDay( minecraftServer.getOverworld() );
				if(
					( 12542L == time )
					|| ( WeatherUtil.isRaining( minecraftServer.getOverworld() ) && 12010L == time )
				) {
					for (ServerPlayerEntity player : minecraftServer.getPlayerManager().getPlayerList()) {
						if( player.getWorld().equals( minecraftServer.getOverworld() ) ) {
							MessageUtil.sendChatMessage(player, MessageUtil.formComment( Platform.CONFIG.sleepNoticeMessage ) );
						} // if
					} // for
				} // if
			} // if
		});
	}
	
}
