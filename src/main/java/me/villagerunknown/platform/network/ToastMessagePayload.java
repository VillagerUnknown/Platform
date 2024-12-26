package me.villagerunknown.platform.network;

import me.villagerunknown.platform.Platform;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ToastMessagePayload( String title, String message, Long duration ) implements CustomPayload {
	
	public static final Identifier TOAST_MESSAGE_PACKET_ID = Identifier.of( Platform.MOD_ID, "toast_message" );
	public static final Id<ToastMessagePayload> ID = new Id<>(TOAST_MESSAGE_PACKET_ID);
	public static final PacketCodec<RegistryByteBuf, ToastMessagePayload> CODEC = PacketCodec.tuple(
			PacketCodecs.STRING, ToastMessagePayload::title,
			PacketCodecs.STRING, ToastMessagePayload::message,
			PacketCodecs.VAR_LONG, ToastMessagePayload::duration,
			ToastMessagePayload::new
	);
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
