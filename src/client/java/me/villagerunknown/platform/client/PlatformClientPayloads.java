package me.villagerunknown.platform.client;

import com.mojang.text2speech.Narrator;
import me.villagerunknown.platform.network.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

public class PlatformClientPayloads {
	
	public static void registerPayloads() {
		registerToastMessagePayload();
		registerNarratorMessagePayload();
		registerShowGameMenuPayload();
		registerShowMainMenuPayload();
		registerNametagVisibilityPayload();
	}
	
	public static void registerToastMessagePayload() {
		ClientPlayNetworking.registerGlobalReceiver(ToastMessagePayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				Minecraft client = context.client();
				ToastManager toastManager = client.getToastManager();
				
				Long duration = payload.duration();
				
				Toast toast = new SystemToast( new SystemToast.SystemToastId( duration ), (Component) FormattedText.of( payload.title() ), (Component) FormattedText.of( payload.message() ));
				
				toastManager.addToast( toast );
			});
		});
	}
	
	public static void registerNarratorMessagePayload() {
		ClientPlayNetworking.registerGlobalReceiver(NarratorMessagePayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				if( context.client().options.narrator().get().shouldNarrateSystem() ) {
					Narrator.getNarrator().say(payload.message(), false, 0.5F);
				} // if
			});
		});
	}
	
	public static void registerShowGameMenuPayload() {
		ClientPlayNetworking.registerGlobalReceiver(ShowPlayerGameMenuPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				Minecraft client = context.client();
				if( client.isSingleplayer() ) {
					client.setScreen(new PauseScreen(true));
				}
			});
		});
	}
	
	public static void registerShowMainMenuPayload() {
		ClientPlayNetworking.registerGlobalReceiver(SendPlayerToMainMenuPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				Minecraft client = context.client();
				if( client.isSingleplayer() ) {
					client.level.disconnect((Component) FormattedText.of("Show main menu"));
					client.disconnect(new GenericMessageScreen((Component) FormattedText.of("Saving world")), false);
					client.setScreen(new TitleScreen(true));
				}
			});
		});
	}
	
	public static void registerNametagVisibilityPayload() {
		ClientPlayNetworking.registerGlobalReceiver(NametagVisibilityPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				PlatformClient.nametagsVisible = payload.visible();
				PlatformClient.playerNametagsVisible = payload.playersVisible();
			});
		});
	}

}
