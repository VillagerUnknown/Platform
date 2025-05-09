package me.villagerunknown.platform.feature;

import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.network.NametagVisibilityPayload;
import me.villagerunknown.platform.util.TimeUtil;
import me.villagerunknown.platform.util.WorldUtil;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;

public class hideNametagsFeature {
	
	public static void execute() {
		registerPlayerJoinEvent();
	}
	
	private static void registerPlayerJoinEvent() {
		ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
			ServerPlayNetworking.send(serverPlayNetworkHandler.player, new NametagVisibilityPayload( !Platform.CONFIG.hideNametags, !Platform.CONFIG.hidePlayerNametags ));
		});
	}
	
}
