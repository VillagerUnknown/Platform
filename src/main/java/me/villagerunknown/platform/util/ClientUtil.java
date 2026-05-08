package me.villagerunknown.platform.util;

import me.villagerunknown.platform.network.SendPlayerToMainMenuPayload;
import me.villagerunknown.platform.network.ShowPlayerGameMenuPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ClientUtil {
	
	public static void showGameMenu(ServerPlayer player) {
		ServerPlayNetworking.send(player, new ShowPlayerGameMenuPayload());
	}
	
	public static void showMainMenu(ServerPlayer player) {
		ServerPlayNetworking.send(player, new SendPlayerToMainMenuPayload());
	}
	
	public static void disconnect(ServerPlayer player, String message) {
		player.connection.disconnect(Component.nullToEmpty(message));
	}
	
}
