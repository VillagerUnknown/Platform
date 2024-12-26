package me.villagerunknown.platform.network;

import me.villagerunknown.platform.Platform;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record NarratorMessagePayload( String message ) implements CustomPayload {
	
	public static final Identifier NARRATOR_MESSAGE_PACKET_ID = Identifier.of( Platform.MOD_ID, "narrator_message" );
	public static final Id<NarratorMessagePayload> ID = new Id<>(NARRATOR_MESSAGE_PACKET_ID);
	public static final PacketCodec<RegistryByteBuf, NarratorMessagePayload> CODEC = PacketCodec.tuple(
			PacketCodecs.STRING, NarratorMessagePayload::message,
			NarratorMessagePayload::new
	);
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
}
