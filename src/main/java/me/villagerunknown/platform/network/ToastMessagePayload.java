package me.villagerunknown.platform.network;

import me.villagerunknown.platform.Platform;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ToastMessagePayload( String title, String message, Long duration ) implements CustomPacketPayload {
	
	public static final Identifier TOAST_MESSAGE_PACKET_ID = Identifier.fromNamespaceAndPath( Platform.MOD_ID, "toast_message" );
	public static final Type<ToastMessagePayload> ID = new Type<>(TOAST_MESSAGE_PACKET_ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, ToastMessagePayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8, ToastMessagePayload::title,
			ByteBufCodecs.STRING_UTF8, ToastMessagePayload::message,
			ByteBufCodecs.VAR_LONG, ToastMessagePayload::duration,
			ToastMessagePayload::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
