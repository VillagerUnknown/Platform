package me.villagerunknown.platform.client;

import net.fabricmc.api.ClientModInitializer;

public class PlatformClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		// # Register Network Payloads
		PlatformClientPayloads.registerPayloads();
	}
	
}
