package me.villagerunknown.platform.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.List;

public class WorldUtil {
	
	public static ServerWorld getServerWorld( World world ) {
		MinecraftServer server = world.getServer();
		
		assert server != null;
		return server.getWorld( world.getRegistryKey() );
	}
	
	public static ServerWorld getServerWorld( World world, RegistryKey<World> registryKey ) {
		MinecraftServer server = world.getServer();
		
		assert server != null;
		return server.getWorld( registryKey );
	}
	
	public static GameRules getGameRules( World world ) {
		return getServerWorld( world ).getLevelProperties().getGameRules();
	}
	
	public static Difficulty getDifficulty( World world ) {
		return getServerWorld( world ).getLevelProperties().getDifficulty();
	}
	
	public static void setRaining( World world, boolean raining ) {
		getServerWorld( world ).getLevelProperties().setRaining( raining );
	}
	
	public static boolean isRaining( World world ) {
		return getServerWorld( world ).getLevelProperties().isRaining();
	}
	
	public static boolean isThundering( World world ) {
		return getServerWorld( world ).getLevelProperties().isThundering();
	}
	
	public static boolean isHardcore( World world ) {
		return getServerWorld( world ).getLevelProperties().isHardcore();
	}
	
	public static Block getBlock( World world, BlockPos pos ) {
		return getServerWorld( world ).getBlockState( pos ).getBlock();
	}
	
	public static <T extends Entity> List<T> getEntitiesByType(ServerWorld world, Box box, Class<T> entityClass ) {
		TypeFilter<Entity, T> typeFilter = TypeFilter.instanceOf(entityClass);
		return world.getEntitiesByType(
				typeFilter,
				box,
				entity -> true
		);
	}
	
	public static <T extends Entity> List<T> getEntitiesByType( ServerWorld world, Class<T> entityClass ) {
		TypeFilter<Entity, T> typeFilter = TypeFilter.instanceOf(entityClass);
		return (List<T>) world.getEntitiesByType(
				typeFilter,
				entity -> true
		);
	}
	
	public static boolean dimensionEquals( World world, RegistryEntry<DimensionType> id ) {
		return getServerWorld( world ).getDimensionEntry().equals( id );
	}
	
	public static boolean dimensionEquals( World world, String id ) {
		return getServerWorld( world ).getDimensionEntry().getIdAsString().equals( id );
	}
	
}
