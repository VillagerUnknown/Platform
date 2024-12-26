package me.villagerunknown.platform.feature;

import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.util.MessageUtil;
import me.villagerunknown.platform.util.PlayerUtil;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class welcomeMessageFeature {
	
	public static void execute() {
		ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
			if( Platform.CONFIG.enableWelcomeMessage ) {
				ServerPlayerEntity player = serverPlayNetworkHandler.player;
				if (PlayerUtil.isNewPlayer(player)) {
					MessageUtil.sendChatMessage(player, Platform.CONFIG.welcomeMessageNew);
				} else {
					MessageUtil.sendChatMessage(player, Platform.CONFIG.welcomeMessageReturning);
				} // if, else
			} // if
		});
	}
	
}
