package me.villagerunknown.platform.feature;

import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.util.MessageUtil;
import me.villagerunknown.platform.util.TimeUtil;
import me.villagerunknown.platform.util.WeatherUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;

public class sleepNoticeFeature {
	
	public static void execute() {
		ServerTickEvents.START_SERVER_TICK.register(minecraftServer -> {
			if( Platform.CONFIG.enableSleepNotice ) {
				long time = TimeUtil.getTimeOfDay( minecraftServer.overworld() );
				if(
					( 12542L == time )
					|| ( WeatherUtil.isRaining( minecraftServer.overworld() ) && 12010L == time )
				) {
					for (ServerPlayer player : minecraftServer.getPlayerList().getPlayers()) {
						if( player.level().equals( minecraftServer.overworld() ) ) {
							MessageUtil.sendChatMessage(player, MessageUtil.formComment( Platform.CONFIG.sleepNoticeMessage ) );
						} // if
					} // for
				} // if
			} // if
		});
	}
	
}
