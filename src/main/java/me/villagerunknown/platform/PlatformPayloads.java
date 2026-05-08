package me.villagerunknown.platform;

import me.villagerunknown.platform.network.*;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class PlatformPayloads {
	
	public static void registerPayloads() {
		// # Register Network Payloads
		PayloadTypeRegistry.clientboundPlay().register(ToastMessagePayload.ID, ToastMessagePayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(NarratorMessagePayload.ID, NarratorMessagePayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(ShowPlayerGameMenuPayload.ID, ShowPlayerGameMenuPayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(SendPlayerToMainMenuPayload.ID, SendPlayerToMainMenuPayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(NametagVisibilityPayload.ID, NametagVisibilityPayload.CODEC);
	}
	
}
