package me.villagerunknown.platform.util;

import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

public class LocatorUtil {
	
	public static Pair<BlockPos, Holder<Biome>> locateBiome( Player player, ResourceKey<Biome> registryKey, int searchRadius, int horizontalBlockCheckInterval, int verticalBlockCheckInterval ) {
		return LocatorUtil.locateBiome( WorldUtil.getServerWorld( player.level() ), player.blockPosition(), registryKey, searchRadius, horizontalBlockCheckInterval, verticalBlockCheckInterval );
	}
	
	public static Pair<BlockPos, Holder<Biome>> locateBiome( ServerLevel serverWorld, BlockPos blockPos, ResourceKey<Biome> registryKey, int searchRadius, int horizontalBlockCheckInterval, int verticalBlockCheckInterval ) {
		Registry<Biome> registry = serverWorld.registryAccess().lookupOrThrow(Registries.BIOME);
		
		Holder<Biome> biome = registry.wrapAsHolder( registry.getValue( registryKey ) );
		
		if( null != biome ) {
			Pair<BlockPos, Holder<Biome>> pair = serverWorld.findClosestBiome3d( (b) -> b == biome, blockPos, searchRadius, horizontalBlockCheckInterval, verticalBlockCheckInterval );
			
			if( null != pair ) {
				return pair;
			} // if
		} // if
		
		return null;
	}
	
	public static Pair<BlockPos, Holder<Structure>> locateStructure( Player player, ResourceKey<Structure> registryKey, int searchRadius ) {
		return LocatorUtil.locateStructure( WorldUtil.getServerWorld( player.level() ), player.blockPosition(), registryKey, searchRadius );
	}
	
	public static Pair<BlockPos, Holder<Structure>> locateStructure( ServerLevel serverWorld, BlockPos blockPos, ResourceKey<Structure> registryKey, int searchRadius ) {
		Registry<Structure> registry = serverWorld.registryAccess().lookupOrThrow(Registries.STRUCTURE);
		
		Optional<Holder.Reference<Structure>> structure = registry.get( registryKey.identifier() );
		
		if( structure.isPresent() ) {
			HolderSet<Structure> registryEntryList = HolderSet.direct(structure.get());
			
			Pair<BlockPos, Holder<Structure>> pair = serverWorld.getChunkSource().getGenerator().findNearestMapStructure(serverWorld, registryEntryList, blockPos, searchRadius, false);
			
			if( null != pair ) {
				return pair;
			} // if
		}
		
		return null;
	}
	
}
