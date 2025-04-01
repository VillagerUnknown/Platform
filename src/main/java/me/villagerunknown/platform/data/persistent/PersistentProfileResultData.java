package me.villagerunknown.platform.data.persistent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.villagerunknown.platform.data.ProfileResultData;
import me.villagerunknown.platform.feature.playerCacheFeature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Uuids;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static me.villagerunknown.platform.Platform.MOD_ID;

public class PersistentProfileResultData extends PersistentData {
	
	public HashMap<UUID, ProfileResultData> players = new HashMap<>();
	
	private static final Codec<PersistentProfileResultData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.unboundedMap(
					Uuids.CODEC,
					ProfileResultData.CODEC
			).fieldOf("players").forGetter( PersistentProfileResultData::getPlayers )
	).apply( instance, PersistentProfileResultData::new ));
	
	private static PersistentStateType<PersistentProfileResultData> type = new PersistentStateType<>(
			MOD_ID,
			(context) -> new PersistentProfileResultData(),
			ctx -> CODEC,
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
		PersistentStateManager persistentStateManager = getStateManager( server );
		
		PersistentProfileResultData state = persistentStateManager.getOrCreate(type);
		state.markDirty();
		
		return state;
	}
	
	public static ProfileResultData getPlayerState(LivingEntity player) {
		MinecraftServer server = player.getServer();
		
		if( null != server ) {
			PersistentProfileResultData serverState = getServerState(server);
			
			if( serverState.players.containsKey( player.getUuid() ) ) {
				return serverState.players.get( player.getUuid() );
			} // if
			
			ProfileResultData playerData = playerCacheFeature.cachePlayer( (PlayerEntity) player );
			
			if( null != playerData ) {
				serverState.players.put( player.getUuid(), playerData );
			} // if
			
			return playerData;
		} // if
		
		return playerCacheFeature.cachePlayer( (PlayerEntity) player );
	}
	
}
