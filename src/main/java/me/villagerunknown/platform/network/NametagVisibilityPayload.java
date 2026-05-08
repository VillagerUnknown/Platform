package me.villagerunknown.platform.network;

import me.villagerunknown.platform.Platform;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record NametagVisibilityPayload( boolean visible, boolean playersVisible ) implements CustomPacketPayload {
	
	public static final Identifier NAMETAG_VISIBILITY_PACKET_ID = Identifier.fromNamespaceAndPath( Platform.MOD_ID, "nametag_visibility" );
	public static final Type<NametagVisibilityPayload> ID = new Type<>(NAMETAG_VISIBILITY_PACKET_ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, NametagVisibilityPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL, NametagVisibilityPayload::visible,
			ByteBufCodecs.BOOL, NametagVisibilityPayload::playersVisible,
			NametagVisibilityPayload::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
