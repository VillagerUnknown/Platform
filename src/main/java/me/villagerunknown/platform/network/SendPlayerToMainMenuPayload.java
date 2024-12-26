package me.villagerunknown.platform.network;

import me.villagerunknown.platform.Platform;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SendPlayerToMainMenuPayload() implements CustomPayload {
	
	public static final Identifier SEND_TO_MAIN_MENU_PACKET_ID = Identifier.of( Platform.MOD_ID, "send_to_main_menu" );
	public static final Id<SendPlayerToMainMenuPayload> ID = new Id<>(SEND_TO_MAIN_MENU_PACKET_ID);
	public static final PacketCodec<RegistryByteBuf, SendPlayerToMainMenuPayload> CODEC = PacketCodec.of( SendPlayerToMainMenuPayload::encode, SendPlayerToMainMenuPayload::decode );
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
	public static void encode(SendPlayerToMainMenuPayload payload, PacketByteBuf buf) {
	
	}
	
	public static SendPlayerToMainMenuPayload decode(PacketByteBuf buf) {
		return new SendPlayerToMainMenuPayload();
	}
	
}