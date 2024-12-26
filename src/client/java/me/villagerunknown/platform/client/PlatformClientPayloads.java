package me.villagerunknown.platform.client;

import com.mojang.text2speech.Narrator;
import me.villagerunknown.platform.network.NarratorMessagePayload;
import me.villagerunknown.platform.network.SendPlayerToMainMenuPayload;
import me.villagerunknown.platform.network.ShowPlayerGameMenuPayload;
import me.villagerunknown.platform.network.ToastMessagePayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.Text;

public class PlatformClientPayloads {
	
	public static void registerPayloads() {
		registerToastMessagePayload();
		registerNarratorMessagePayload();
		registerShowGameMenuPayload();
		registerShowMainMenuPayload();
	}
	
	public static void registerToastMessagePayload() {
		ClientPlayNetworking.registerGlobalReceiver(ToastMessagePayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				MinecraftClient client = MinecraftClient.getInstance();
				ToastManager toastManager = client.getToastManager();
				
				Long duration = payload.duration();
				
				SystemToast.Type type = new SystemToast.Type( duration );
				
				Toast toast = new SystemToast( type, Text.of( payload.title() ), Text.of( payload.message() ) );
				
				toastManager.add( toast );
			});
		});
	}
	
	public static void registerNarratorMessagePayload() {
		ClientPlayNetworking.registerGlobalReceiver(NarratorMessagePayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				if( context.client().options.getNarrator().getValue().shouldNarrateSystem() ) {
					Narrator.getNarrator().say(payload.message(), false);
				} // if
			});
		});
	}
	
	public static void registerShowGameMenuPayload() {
		ClientPlayNetworking.registerGlobalReceiver(ShowPlayerGameMenuPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				MinecraftClient client = MinecraftClient.getInstance();
				if( client.isInSingleplayer() ) {
					client.setScreen(new GameMenuScreen(true));
				}
			});
		});
	}
	
	public static void registerShowMainMenuPayload() {
		ClientPlayNetworking.registerGlobalReceiver(SendPlayerToMainMenuPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				MinecraftClient client = MinecraftClient.getInstance();
				if( client.isInSingleplayer() ) {
					client.world.disconnect();
					client.disconnect(new MessageScreen(Text.of("Saving world")));
					client.setScreen(new TitleScreen(true));
				}
			});
		});
	}

}
