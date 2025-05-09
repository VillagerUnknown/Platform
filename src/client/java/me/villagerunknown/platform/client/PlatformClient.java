package me.villagerunknown.platform.client;

import net.fabricmc.api.ClientModInitializer;

public class PlatformClient implements ClientModInitializer {
	
	public static boolean nametagsVisible = true;
	public static boolean playerNametagsVisible = true;
	
	@Override
	public void onInitializeClient() {
		// # Register Network Payloads
		PlatformClientPayloads.registerPayloads();
	}
	
}
