package me.villagerunknown.platform.feature;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.PlatformPersistentData;
import me.villagerunknown.platform.util.ProfileUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Uuids;
import net.minecraft.util.dynamic.Codecs;

import java.util.*;

public class playerCacheFeature {
	
	public static Map<UUID, PlayerData> PLAYERS = new HashMap<>();
	
	public static void execute() {
		registerServerStartedEvent();
		registerPlayerJoinEvent();
	}
	
	private static void registerServerStartedEvent() {
		ServerLifecycleEvents.SERVER_STARTED.register(( server ) -> {
			if( Platform.CONFIG.enablePlayerCaching ) {
				Platform.LOGGER.info("Player caching enabled.");
				
				PlatformPersistentData serverState = PlatformPersistentData.getServerState(server);
				
				Platform.LOGGER.info("{} player(s) cached.", serverState.players.size() );
				
				if( !serverState.players.isEmpty() ) {
					PLAYERS = serverState.players;
				} // if
			} // if
		});
	}
	
	private static void registerPlayerJoinEvent() {
		ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
			if( Platform.CONFIG.enablePlayerCaching ) {
				ServerPlayerEntity player = serverPlayNetworkHandler.player;
				UUID playerUuid = player.getUuid();
				
				if( !isCached(playerUuid) ) {
					PlayerData playerState = PlatformPersistentData.getPlayerState( player );
					
					if( null != playerState ) {
						PLAYERS.put( playerState.uuid, new PlayerData( playerState.name, playerState.uuid, playerState.result ) );
						
						Platform.LOGGER.info( "Player cache loaded from server: {} ({})", playerState.name, playerState.uuid.toString() );
					} else {
						cachePlayer( player );
					} // if, else
				} else {
					Platform.LOGGER.info( "Player cache loaded from instance: {} ({})", player.getNameForScoreboard(), player.getUuidAsString() );
				} // if, else
			} // if
		});
	}
	
	public static ProfileResult getUncachedProfileResult(MinecraftServer server, UUID uuid, boolean requireSecure ) {
		return ProfileUtil.fetchProfile( server, uuid, requireSecure );
	}
	
	public static ProfileResult getCachedProfileResult( UUID uuid ) {
		return PLAYERS.get( uuid ).result;
	}
	
	public static boolean isCached( UUID uuid ) {
		return PLAYERS.containsKey( uuid );
	}
	
	public static PlayerData cachePlayer( MinecraftServer minecraftServer, String playerName, UUID playerUuid ) {
		ProfileResult profileResult = getUncachedProfileResult(minecraftServer, playerUuid, minecraftServer.shouldEnforceSecureProfile());
		if( null != profileResult ) {
			return cachePlayer( playerName, playerUuid, profileResult );
		} // if
		
		return null;
	}
	
	public static PlayerData cachePlayer( String playerName, UUID playerUuid, ProfileResult profileResult ) {
		if( null != profileResult ) {
			PlayerData playerData = new PlayerData(playerName, playerUuid, profileResult);
			return addToCache(playerUuid, playerData);
		} // if
		
		return null;
	}
	
	public static PlayerData cachePlayer( PlayerEntity player ) {
		MinecraftServer server = player.getServer();
		
		if( null != server ) {
			ProfileResult profileResult = getUncachedProfileResult(server, player.getUuid(), server.shouldEnforceSecureProfile());
			
			return cachePlayer( player.getNameForScoreboard(), player.getUuid(), profileResult );
		} // if
		
		return null;
	}
	
	public static PlayerData addToCache( UUID uuid, PlayerData data ) {
		if( null == data.result ) {
			return data;
		} // if
		
		PLAYERS.put( uuid, data );
		
		Platform.LOGGER.info( "Player cached created: {} ({})", data.name, data.uuid.toString() );
		
		return data;
	}
	
	public static class PlayerData {
		
		public static final Codec<ProfileResult> PROFILE_RESULT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codecs.GAME_PROFILE_WITH_PROPERTIES.fieldOf("profile").forGetter(ProfileResult::profile)
		).apply(instance, playerCacheFeature.PlayerData::newProfileResult));
		
		public static final Codec<PlayerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codec.STRING.fieldOf("name").forGetter(player -> player.name),
				Uuids.CODEC.fieldOf("uuid").forGetter(player -> player.uuid),
				PROFILE_RESULT_CODEC.fieldOf("profile").forGetter( p -> p.result )
		).apply(instance, playerCacheFeature.PlayerData::new));
		
		public String name;
		
		public UUID uuid;
		
		public ProfileResult result;
		
		public PlayerData(String playerName, UUID playerUuid, ProfileResult profileResult) {
			name = playerName;
			uuid = playerUuid;
			result = profileResult;
		}
		
		public static ProfileResult newProfileResult(GameProfile profile) {
			return new ProfileResult( profile );
		}
		
	}
	
}
