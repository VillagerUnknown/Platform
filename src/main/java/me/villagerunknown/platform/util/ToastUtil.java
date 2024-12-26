package me.villagerunknown.platform.util;

import me.villagerunknown.platform.network.ToastMessagePayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class ToastUtil {
	
	public static final Long TOAST_DURATION_SECOND = 1000L;
	public static final Long TOAST_DURATION_MINUTE = 60L * TOAST_DURATION_SECOND;
	
	public static void showToast(ServerPlayerEntity player, String title, String message, Long duration) {
		ServerPlayNetworking.send( player, new ToastMessagePayload( title, message, duration ));
	}
	
}
