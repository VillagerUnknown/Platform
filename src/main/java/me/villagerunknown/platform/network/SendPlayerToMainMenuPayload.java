package me.villagerunknown.platform.network;

import me.villagerunknown.platform.Platform;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SendPlayerToMainMenuPayload() implements CustomPacketPayload {
	
	public static final Identifier SEND_TO_MAIN_MENU_PACKET_ID = Identifier.fromNamespaceAndPath( Platform.MOD_ID, "send_to_main_menu" );
	public static final Type<SendPlayerToMainMenuPayload> ID = new Type<>(SEND_TO_MAIN_MENU_PACKET_ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, SendPlayerToMainMenuPayload> CODEC = StreamCodec.ofMember( SendPlayerToMainMenuPayload::encode, SendPlayerToMainMenuPayload::decode );
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
	public static void encode(SendPlayerToMainMenuPayload payload, FriendlyByteBuf buf) {
	
	}
	
	public static SendPlayerToMainMenuPayload decode(FriendlyByteBuf buf) {
		return new SendPlayerToMainMenuPayload();
	}
	
}