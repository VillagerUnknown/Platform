package me.villagerunknown.platform.data.persistent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.villagerunknown.platform.data.ProfileResultData;
import me.villagerunknown.platform.feature.playerCacheFeature;
import me.villagerunknown.platform.util.GsonUtil;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.SavedDataStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.villagerunknown.platform.Platform.MOD_ID;
import static me.villagerunknown.platform.Platform.PLATFORM_ID;

public class PersistentProfileResultData extends AbstractPersistentData {
	
	public HashMap<UUID, ProfileResultData> players = new HashMap<>();
	
	private static final Gson gson = GsonUtil.gsonWithAdapters();
	
	private static final Codec<PersistentProfileResultData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.unboundedMap(
					UUIDUtil.AUTHLIB_CODEC,
					ProfileResultData.CODEC
			).fieldOf("players").forGetter( PersistentProfileResultData::getPlayers )
	).apply( instance, PersistentProfileResultData::new ));
	
	private static final SavedDataType<PersistentProfileResultData> type = new SavedDataType<>(
			Identifier.fromNamespaceAndPath( MOD_ID, "profile_result_data" ),
			PersistentProfileResultData::new,
			CODEC,
			null
	);
	
	PersistentProfileResultData() {}
	
	public PersistentProfileResultData(Map<UUID, ProfileResultData> playerDataMap) {
		players = new HashMap<>(playerDataMap);
	}
	
	public HashMap<UUID, ProfileResultData> getPlayers() {
		return players;
	}
	
	public static PersistentProfileResultData getServerState(MinecraftServer server) {
		SavedDataStorage persistentStateManager = getStateManager( server );
		
		PersistentProfileResultData state = persistentStateManager.computeIfAbsent(type);
		state.setDirty();
		
		return state;
	}
	
	public static ProfileResultData getPlayerState(LivingEntity player) {
		MinecraftServer server = player.level().getServer();
		
		if( null != server ) {
			PersistentProfileResultData serverState = getServerState(server);
			
			if( serverState.players.containsKey( player.getUUID() ) ) {
				return serverState.players.get( player.getUUID() );
			} // if
			
			ProfileResultData playerData = playerCacheFeature.cachePlayer( (Player) player );
			
			if( null != playerData ) {
				serverState.players.put( player.getUUID(), playerData );
			} // if
			
			return playerData;
		} // if
		
		return playerCacheFeature.cachePlayer( (Player) player );
	}
	
}