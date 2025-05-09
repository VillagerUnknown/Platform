package me.villagerunknown.platform.data.persistent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.villagerunknown.platform.data.ProfileResultData;
import me.villagerunknown.platform.feature.playerCacheFeature;
import me.villagerunknown.platform.util.GsonUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Uuids;
import net.minecraft.world.PersistentStateManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.villagerunknown.platform.Platform.MOD_ID;

public class PersistentProfileResultData extends AbstractPersistentData {
	
	public HashMap<UUID, ProfileResultData> players = new HashMap<>();
	
	private static final Gson gson = GsonUtil.gsonWithAdapters();
	
	private static final Codec<PersistentProfileResultData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.unboundedMap(
					Uuids.CODEC,
					ProfileResultData.CODEC
			).fieldOf("players").forGetter( PersistentProfileResultData::getPlayers )
	).apply( instance, PersistentProfileResultData::new ));
	
	private static final Type<PersistentProfileResultData> type = new Type<>(
			PersistentProfileResultData::new,
			PersistentProfileResultData::createFromNbt,
			null
	);
	
	PersistentProfileResultData() {}
	
	public PersistentProfileResultData(Map<UUID, ProfileResultData> playerDataMap) {
		players = new HashMap<>(playerDataMap);
	}
	
	@Override
	public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		nbt.putString("players", gson.toJson( players, new TypeToken<Map<UUID, ProfileResultData>>() {}.getType() ));
		
		return nbt;
	}
	
	public static PersistentProfileResultData createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		PersistentProfileResultData state = new PersistentProfileResultData();

		state.players = gson.fromJson( tag.getString("players"), new TypeToken<Map<UUID, ProfileResultData>>() {}.getType() );
		
		return state;
	}
	
	public HashMap<UUID, ProfileResultData> getPlayers() {
		return players;
	}
	
	public static PersistentProfileResultData getServerState(MinecraftServer server) {
		PersistentStateManager persistentStateManager = getStateManager( server );
		
		PersistentProfileResultData state = persistentStateManager.getOrCreate(type, MOD_ID);
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