package me.villagerunknown.platform;

import me.villagerunknown.platform.network.*;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class PlatformPayloads {
	
	public static void registerPayloads() {
		// # Register Network Payloads
		PayloadTypeRegistry.playS2C().register(ToastMessagePayload.ID, ToastMessagePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(NarratorMessagePayload.ID, NarratorMessagePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(ShowPlayerGameMenuPayload.ID, ShowPlayerGameMenuPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(SendPlayerToMainMenuPayload.ID, SendPlayerToMainMenuPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(NametagVisibilityPayload.ID, NametagVisibilityPayload.CODEC);
	}
	
}
