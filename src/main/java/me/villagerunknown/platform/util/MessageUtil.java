package me.villagerunknown.platform.util;

import me.villagerunknown.platform.Platform;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.List;

public class MessageUtil {
	
	private static final List<String> VILLAGER_COMMENTS = List.of(
			"Hm.",
			"Hm..",
			"Hrm..",
			"Hm hm!",
			"Hm hrm!",
			"Hrm hm!",
			"Hrm hm hrm!",
			"Hrm hrm hm!",
			"Hm hrm hrm!",
			"Hrm hm hm!",
			"Hm hm hrm!"
	);
	
	public static String formComment( String message ) {
		if( Platform.CONFIG.enableVillagerComments ) {
			return formVillagerComment( message );
		}
		
		return message;
	}
	
	public static String formVillagerComment( String message ) {
		return ListUtil.chooseRandomFromList(VILLAGER_COMMENTS) + " (" + message + ")";
	}
	
	public static Text formClickableMessage(String message, String url ) {
		return Text.literal( message )
				.setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)));
	}
	
	public static void showActionBarMessage(ServerPlayerEntity player, String message) {
		player.sendMessageToClient( Text.of( message ), true );
	}
	
	public static void showActionBarMessage(PlayerEntity player, String message) {
		player.sendMessage( Text.of( message ), true );
	}
	
	public static void sendChatMessage(ServerPlayerEntity player, String message) {
		player.sendMessage( Text.of( message ), false );
	}
	
	public static void sendChatMessage(PlayerEntity player, String message) {
		player.sendMessage( Text.of( message ), false );
	}
	
	public static void broadcastChatMessage(MinecraftServer server, String message) {
		List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
		
		for (ServerPlayerEntity player : players) {
			sendChatMessage( player, message );
		} // for
	}
	
}
