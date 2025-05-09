package me.villagerunknown.platform.network;

import me.villagerunknown.platform.Platform;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record NametagVisibilityPayload( boolean visible, boolean playersVisible ) implements CustomPayload {
	
	public static final Identifier NAMETAG_VISIBILITY_PACKET_ID = Identifier.of( Platform.MOD_ID, "nametag_visibility" );
	public static final Id<NametagVisibilityPayload> ID = new Id<>(NAMETAG_VISIBILITY_PACKET_ID);
	public static final PacketCodec<RegistryByteBuf, NametagVisibilityPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.BOOL, NametagVisibilityPayload::visible,
			PacketCodecs.BOOL, NametagVisibilityPayload::playersVisible,
			NametagVisibilityPayload::new
	);
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
