package me.villagerunknown.platform;

import me.villagerunknown.platform.network.NarratorMessagePayload;
import me.villagerunknown.platform.network.SendPlayerToMainMenuPayload;
import me.villagerunknown.platform.network.ShowPlayerGameMenuPayload;
import me.villagerunknown.platform.network.ToastMessagePayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class PlatformPayloads {
	
	public static void registerPayloads() {
		// # Register Network Payloads
		PayloadTypeRegistry.playS2C().register(ToastMessagePayload.ID, ToastMessagePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(NarratorMessagePayload.ID, NarratorMessagePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(ShowPlayerGameMenuPayload.ID, ShowPlayerGameMenuPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(SendPlayerToMainMenuPayload.ID, SendPlayerToMainMenuPayload.CODEC);
	}
	
}
