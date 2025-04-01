package me.villagerunknown.platform.util;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.ProfileResult;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

public class ProfileUtil {
	
	public static final UUID DEFAULT_UUID = UUID.fromString("5aa7ebdf-c67b-4643-9214-3015d269dedb");
	
	public static ProfileResult fetchProfile(MinecraftServer server, UUID uuid, boolean requireSecure ) {
		MinecraftSessionService sessionService = server.getSessionService();
		return sessionService.fetchProfile(uuid, requireSecure);
	}
	
}
