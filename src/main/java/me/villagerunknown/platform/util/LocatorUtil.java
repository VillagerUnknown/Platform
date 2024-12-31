package me.villagerunknown.platform.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;

import java.util.Optional;

public class LocatorUtil {
	
	public static Pair<BlockPos, RegistryEntry<Biome>> locateBiome( PlayerEntity player, RegistryKey<Biome> registryKey, int searchRadius, int horizontalBlockCheckInterval, int verticalBlockCheckInterval ) {
		return LocatorUtil.locateBiome( WorldUtil.getServerWorld( player.getWorld() ), player.getBlockPos(), registryKey, searchRadius, horizontalBlockCheckInterval, verticalBlockCheckInterval );
	}
	
	public static Pair<BlockPos, RegistryEntry<Biome>> locateBiome( ServerWorld serverWorld, BlockPos blockPos, RegistryKey<Biome> registryKey, int searchRadius, int horizontalBlockCheckInterval, int verticalBlockCheckInterval ) {
		Registry<Biome> registry = serverWorld.getRegistryManager().get(RegistryKeys.BIOME);
		
		RegistryEntry<Biome> biome = registry.getEntry( registry.get( registryKey ) );
		
		if( null != biome ) {
			Pair<BlockPos, RegistryEntry<Biome>> pair = serverWorld.locateBiome( (b) -> b == biome, blockPos, searchRadius, horizontalBlockCheckInterval, verticalBlockCheckInterval );
			
			if( null != pair ) {
				return pair;
			} // if
		} // if
		
		return null;
	}
	
	public static Pair<BlockPos, RegistryEntry<Structure>> locateStructure( PlayerEntity player, RegistryKey<Structure> registryKey, int searchRadius ) {
		return LocatorUtil.locateStructure( WorldUtil.getServerWorld( player.getWorld() ), player.getBlockPos(), registryKey, searchRadius );
	}
	
	public static Pair<BlockPos, RegistryEntry<Structure>> locateStructure( ServerWorld serverWorld, BlockPos blockPos, RegistryKey<Structure> registryKey, int searchRadius ) {
		Registry<Structure> registry = serverWorld.getRegistryManager().get(RegistryKeys.STRUCTURE);
		
		Optional<RegistryEntry.Reference<Structure>> structure = registry.getEntry( registryKey );
		
		if( structure.isPresent() ) {
			RegistryEntryList<Structure> registryEntryList = RegistryEntryList.of(structure.get());
			
			Pair<BlockPos, RegistryEntry<Structure>> pair = serverWorld.getChunkManager().getChunkGenerator().locateStructure(serverWorld, registryEntryList, blockPos, searchRadius, false);
			
			if( null != pair ) {
				return pair;
			} // if
		}
		
		return null;
	}
	
}
