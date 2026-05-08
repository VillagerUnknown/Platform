package me.villagerunknown.platform.util;

import me.villagerunknown.platform.network.NarratorMessagePayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public class NarratorUtil {
	
	public static void narrate(ServerPlayer player, String message) {
		ServerPlayNetworking.send( player, new NarratorMessagePayload( message ));
	}
	
}
