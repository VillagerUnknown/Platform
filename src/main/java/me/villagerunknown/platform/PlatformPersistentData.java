package me.villagerunknown.platform;

import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.villagerunknown.platform.feature.playerCacheFeature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Uuids;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;

import java.util.*;

import static me.villagerunknown.platform.Platform.MOD_ID;

public class PlatformPersistentData extends PersistentState {
	
	public HashMap<UUID, playerCacheFeature.PlayerData> players = new HashMap<>();
	
	private static final Codec<PlatformPersistentData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.unboundedMap(
					Uuids.CODEC,
					playerCacheFeature.PlayerData.CODEC
			).fieldOf("players").forGetter( PlatformPersistentData::getPlayers )
	).apply( instance, PlatformPersistentData::new ));
	
	PlatformPersistentData() {}
	
	public PlatformPersistentData(Map<UUID, playerCacheFeature.PlayerData> playerDataMap) {
		players = new HashMap<>(playerDataMap);
	}
	
	public HashMap<UUID, playerCacheFeature.PlayerData> getPlayers() {
		return players;
	}
	
	private static PersistentStateType<PlatformPersistentData> type = new PersistentStateType<>(
			MOD_ID,
			(context) -> new PlatformPersistentData(),
			ctx -> CODEC,
			null
	);
	
	public static PlatformPersistentData getServerState(MinecraftServer server) {
		// (Note: arbitrary choice to use 'World.OVERWORLD' instead of 'World.END' or 'World.NETHER'.  Any work)
		PersistentStateManager persistentStateManager = Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getPersistentStateManager();
		
		// The first time the following 'getOrCreate' function is called, it creates a brand new 'StateSaverAndLoader' and
		// stores it inside the 'PersistentStateManager'. The subsequent calls to 'getOrCreate' pass in the saved
		// 'StateSaverAndLoader' NBT on disk to our function 'StateSaverAndLoader::createFromNbt'.
		PlatformPersistentData state = persistentStateManager.getOrCreate(type);
		
		// If state is not marked dirty, when Minecraft closes, 'writeNbt' won't be called and therefore nothing will be saved.
		// Technically it's 'cleaner' if you only mark state as dirty when there was actually a change, but the vast majority
		// of mod writers are just going to be confused when their data isn't being saved, and so it's best just to 'markDirty' for them.
		// Besides, it's literally just setting a bool to true, and the only time there's a 'cost' is when the file is written to disk when
		// there were no actual change to any of the mods state (INCREDIBLY RARE).
		state.markDirty();
		
		return state;
	}
	
	public static playerCacheFeature.PlayerData getPlayerState(LivingEntity player) {
		MinecraftServer server = player.getServer();
		
		if( null != server ) {
			PlatformPersistentData serverState = getServerState(server);
			
			if( serverState.players.containsKey( player.getUuid() ) ) {
				return serverState.players.get( player.getUuid() );
			} // if
			
			playerCacheFeature.PlayerData playerData = playerCacheFeature.cachePlayer( (PlayerEntity) player );
			
			if( null != playerData ) {
				serverState.players.put( player.getUuid(), playerData );
			} // if
			
			return playerData;
		} // if
		
		return playerCacheFeature.cachePlayer( (PlayerEntity) player );
	}
	
}
