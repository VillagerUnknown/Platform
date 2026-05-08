package me.villagerunknown.platform.util;

import me.villagerunknown.platform.Platform;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import java.net.URI;
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
	
	public static Component formClickableMessage(String message, String url ) {
		return Component.literal( message )
				.setStyle(Style.EMPTY.withClickEvent(new ClickEvent.OpenUrl(URI.create( url ))));
	}
	
	public static void showActionBarMessage(ServerPlayer player, String message) {
		player.sendSystemMessage( Component.nullToEmpty( message ), true );
	}
	
	public static void showActionBarMessage(Player player, String message) {
		player.sendSystemMessage( Component.nullToEmpty( message ) );
	}
	
	public static void sendChatMessage(ServerPlayer player, String message) {
		player.sendSystemMessage( Component.nullToEmpty( message ) );
	}
	
	public static void sendChatMessage(Player player, String message) {
		player.sendSystemMessage( Component.nullToEmpty( message ) );
	}
	
	public static void broadcastChatMessage(MinecraftServer server, String message) {
		List<ServerPlayer> players = server.getPlayerList().getPlayers();
		
		for (ServerPlayer player : players) {
			sendChatMessage( player, message );
		} // for
	}
	
}
