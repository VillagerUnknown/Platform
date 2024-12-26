package me.villagerunknown.platform.util;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class PlayerUtil {
	
	public static boolean isNewPlayer( ServerPlayerEntity player ) {
		return ( PlayerStatUtil.getStatFromIdentifier(player, Stats.TOTAL_WORLD_TIME) <= 0 );
	}
	
}
