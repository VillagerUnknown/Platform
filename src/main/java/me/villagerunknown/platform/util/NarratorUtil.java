package me.villagerunknown.platform.util;

import me.villagerunknown.platform.network.NarratorMessagePayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class NarratorUtil {
	
	public static void narrate(ServerPlayerEntity player, String message) {
		ServerPlayNetworking.send( player, new NarratorMessagePayload( message ));
	}
	
}
