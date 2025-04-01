package me.villagerunknown.platform.feature;

import com.mojang.authlib.yggdrasil.ProfileResult;
import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.data.ProfileResultData;
import me.villagerunknown.platform.data.persistent.PersistentProfileResultData;
import me.villagerunknown.platform.util.ProfileUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.*;

public class playerCacheFeature {
	
	public static Map<UUID, ProfileResultData> PLAYERS = new HashMap<>();
	
	public static void execute() {
		registerServerStartedEvent();
		registerPlayerJoinEvent();
	}
	
	private static void registerServerStartedEvent() {
		ServerLifecycleEvents.SERVER_STARTED.register(( server ) -> {
			if( Platform.CONFIG.enablePlayerCaching ) {
				Platform.LOGGER.info("Player caching enabled.");
				
				PersistentProfileResultData serverState = PersistentProfileResultData.getServerState(server);
				
				Platform.LOGGER.info("{} player(s) cached.", serverState.players.size() );
				
				if( !serverState.players.isEmpty() ) {
					PLAYERS = serverState.players;
				} // if
			} else {
				emptyCache();
			} // if, else
		});
	}
	
	private static void registerPlayerJoinEvent() {
		ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
			if( Platform.CONFIG.enablePlayerCaching ) {
				ServerPlayerEntity player = serverPlayNetworkHandler.player;
				UUID playerUuid = player.getUuid();
				
				if( !isCached(playerUuid) ) {
					ProfileResultData playerState = PersistentProfileResultData.getPlayerState( player );
					
					if( null != playerState ) {
						PLAYERS.put( playerState.uuid, new ProfileResultData( playerState.name, playerState.uuid, playerState.result ) );
						
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
	
	public static ProfileResultData cachePlayer( MinecraftServer minecraftServer, String playerName, UUID playerUuid ) {
		ProfileResult profileResult = getUncachedProfileResult(minecraftServer, playerUuid, minecraftServer.shouldEnforceSecureProfile());
		if( null != profileResult ) {
			return cachePlayer( playerName, playerUuid, profileResult );
		} // if
		
		return null;
	}
	
	public static ProfileResultData cachePlayer( String playerName, UUID playerUuid, ProfileResult profileResult ) {
		if( null != profileResult ) {
			ProfileResultData playerData = new ProfileResultData(playerName, playerUuid, profileResult);
			return addToCache(playerUuid, playerData);
		} // if
		
		return null;
	}
	
	public static ProfileResultData cachePlayer( PlayerEntity player ) {
		MinecraftServer server = player.getServer();
		
		if( null != server ) {
			ProfileResult profileResult = getUncachedProfileResult(server, player.getUuid(), server.shouldEnforceSecureProfile());
			
			return cachePlayer( player.getNameForScoreboard(), player.getUuid(), profileResult );
		} // if
		
		return null;
	}
	
	public static ProfileResultData addToCache(UUID uuid, ProfileResultData data ) {
		if( null == data.result ) {
			return data;
		} // if
		
		PLAYERS.put( uuid, data );
		
		Platform.LOGGER.info( "Player cached created: {} ({})", data.name, data.uuid.toString() );
		
		return data;
	}
	
	public static void emptyCache() {
		PLAYERS.clear();
	}
	
}
