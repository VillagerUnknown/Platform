package me.villagerunknown.platform.util;

import me.villagerunknown.platform.network.SendPlayerToMainMenuPayload;
import me.villagerunknown.platform.network.ShowPlayerGameMenuPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ClientUtil {
	
	public static void showGameMenu(ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, new ShowPlayerGameMenuPayload());
	}
	
	public static void showMainMenu(ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, new SendPlayerToMainMenuPayload());
	}
	
	public static void disconnect(ServerPlayerEntity player, String message) {
		player.networkHandler.disconnect(Text.of(message));
	}
	
}
