package me.villagerunknown.platform.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;

public class WeatherUtil {
	
	public static final String WINTER = "winter";
	public static final String SPRING = "spring";
	public static final String SUMMER = "summer";
	public static final String FALL = "fall";
	
	public static void setRaining( Level world, boolean status ) {
		WorldUtil.setRaining( world, status );
	}
	
	public static boolean isRaining( Level world ) {
		return WorldUtil.isRaining( world );
	}
	
	public static boolean isThundering( Level world ) {
		return WorldUtil.isThundering( world );
	}
	
	public static String getCurrentSeason(long worldTime) {
		long yearTime = worldTime / TimeUtil.LENGTH_DAY;
		return (yearTime % 4) == 0 ? SUMMER : (yearTime % 4) == 1 ? FALL : (yearTime % 4) == 2 ? WINTER : SPRING;
	}
	
	public static LightningBolt lightning( Level world, BlockPos pos, boolean spawn ) {
		LightningBolt lightning = new LightningBolt( EntityTypes.LIGHTNING_BOLT, world );
		lightning.setPosRaw( pos.getX(), pos.getY(), pos.getZ() );
		
		if( spawn ) {
			world.addFreshEntity( lightning );
		} // if
		
		return lightning;
	}
	
}
