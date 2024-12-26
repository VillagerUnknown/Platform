package me.villagerunknown.platform.network;

import me.villagerunknown.platform.Platform;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ShowPlayerGameMenuPayload() implements CustomPayload {
	
	public static final Identifier SHOW_GAME_MENU_PACKET_ID = Identifier.of( Platform.MOD_ID, "show_game_menu" );
	public static final Id<ShowPlayerGameMenuPayload> ID = new Id<>(SHOW_GAME_MENU_PACKET_ID);
	public static final PacketCodec<RegistryByteBuf, ShowPlayerGameMenuPayload> CODEC = PacketCodec.of( ShowPlayerGameMenuPayload::encode, ShowPlayerGameMenuPayload::decode );
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
	public static void encode(ShowPlayerGameMenuPayload payload, PacketByteBuf buf) {
	
	}
	
	public static ShowPlayerGameMenuPayload decode(PacketByteBuf buf) {
		return new ShowPlayerGameMenuPayload();
	}
	
}
