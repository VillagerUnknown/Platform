package me.villagerunknown.platform.network;

import me.villagerunknown.platform.Platform;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ShowPlayerGameMenuPayload() implements CustomPacketPayload {
	
	public static final Identifier SHOW_GAME_MENU_PACKET_ID = Identifier.fromNamespaceAndPath( Platform.MOD_ID, "show_game_menu" );
	public static final Type<ShowPlayerGameMenuPayload> ID = new Type<>(SHOW_GAME_MENU_PACKET_ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, ShowPlayerGameMenuPayload> CODEC = StreamCodec.ofMember( ShowPlayerGameMenuPayload::encode, ShowPlayerGameMenuPayload::decode );
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
	public static void encode(ShowPlayerGameMenuPayload payload, FriendlyByteBuf buf) {
	
	}
	
	public static ShowPlayerGameMenuPayload decode(FriendlyByteBuf buf) {
		return new ShowPlayerGameMenuPayload();
	}
	
}
