package me.villagerunknown.platform.network;

import me.villagerunknown.platform.Platform;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record NarratorMessagePayload( String message ) implements CustomPacketPayload {
	
	public static final Identifier NARRATOR_MESSAGE_PACKET_ID = Identifier.fromNamespaceAndPath( Platform.MOD_ID, "narrator_message" );
	public static final Type<NarratorMessagePayload> ID = new Type<>(NARRATOR_MESSAGE_PACKET_ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, NarratorMessagePayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8, NarratorMessagePayload::message,
			NarratorMessagePayload::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
}
