package me.villagerunknown.platform.util;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WeatherUtil {
	
	public static final String WINTER = "winter";
	public static final String SPRING = "spring";
	public static final String SUMMER = "summer";
	public static final String FALL = "fall";
	
	public static void setRaining( World world, boolean status ) {
		WorldUtil.setRaining( world, status );
	}
	
	public static boolean isRaining( World world ) {
		return WorldUtil.isRaining( world );
	}
	
	public static boolean isThundering( World world ) {
		return WorldUtil.isThundering( world );
	}
	
	public static String getCurrentSeason(long worldTime) {
		long yearTime = worldTime / TimeUtil.LENGTH_DAY;
		return (yearTime % 4) == 0 ? SUMMER : (yearTime % 4) == 1 ? FALL : (yearTime % 4) == 2 ? WINTER : SPRING;
	}
	
	public static LightningEntity lightning( World world, BlockPos pos, boolean spawn ) {
		LightningEntity lightning = new LightningEntity( EntityType.LIGHTNING_BOLT, world );
		lightning.setPos( pos.getX(), pos.getY(), pos.getZ() );
		
		if( spawn ) {
			world.spawnEntity( lightning );
		} // if
		
		return lightning;
	}
	
}
