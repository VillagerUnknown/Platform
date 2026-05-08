package me.villagerunknown.platform.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;

public class PlayerUtil {
	
	public static boolean isNewPlayer( ServerPlayer player ) {
		return ( PlayerStatUtil.getStatFromIdentifier(player, Stats.TOTAL_WORLD_TIME) <= 0 );
	}
	
}
