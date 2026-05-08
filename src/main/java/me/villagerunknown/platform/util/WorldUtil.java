package me.villagerunknown.platform.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.saveddata.WeatherData;
import net.minecraft.world.phys.AABB;
import java.util.List;

public class WorldUtil {
	
	public static ServerLevel getServerWorld( Level world ) {
		MinecraftServer server = world.getServer();
		
		assert server != null;
		return server.getLevel( world.dimension() );
	}
	
	public static ServerLevel getServerWorld( Level world, ResourceKey<Level> registryKey ) {
		MinecraftServer server = world.getServer();
		
		assert server != null;
		return server.getLevel( registryKey );
	}
	
	public static GameRules getGameRules(Level world ) {
		return getServerWorld( world ).getGameRules();
	}
	
	public static Difficulty getDifficulty( Level world ) {
		return world.getLevelData().getDifficulty();
	}
	
	public static void setRaining( Level world, boolean raining ) {
		ServerLevel serverWorld = getServerWorld(world);
		WeatherData weatherData = serverWorld.getWeatherData();
		
		weatherData.setRaining( raining );
	}
	
	public static void setRainLevel( Level world, Integer rainLevel ) {
		world.setRainLevel( rainLevel );
	}
	
	public static boolean isRaining( Level world ) {
		return world.isRaining();
	}
	
	public static boolean isThundering( Level world ) {
		return world.isThundering();
	}
	
	public static boolean isHardcore( Level world ) {
		return world.getLevelData().isHardcore();
	}
	
	public static Block getBlock( Level world, BlockPos pos ) {
		return world.getBlockState( pos ).getBlock();
	}
	
	public static <T extends Entity> List<T> getEntitiesByType(ServerLevel world, AABB box, Class<T> entityClass ) {
		EntityTypeTest<Entity, T> typeFilter = EntityTypeTest.forClass(entityClass);
		return world.getEntities(
				typeFilter,
				box,
				entity -> true
		);
	}
	
	public static <T extends Entity> List<T> getEntitiesByType( ServerLevel world, Class<T> entityClass ) {
		EntityTypeTest<Entity, T> typeFilter = EntityTypeTest.forClass(entityClass);
		return (List<T>) world.getEntities(
				typeFilter,
				entity -> true
		);
	}
	
	public static boolean dimensionEquals( Level world, Holder<DimensionType> id ) {
		return world.dimensionTypeRegistration().equals( id );
	}
	
	public static boolean dimensionEquals( Level world, String id ) {
		return world.dimensionTypeRegistration().getRegisteredName().equals( id );
	}
	
}
