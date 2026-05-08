package me.villagerunknown.platform.util;

import net.minecraft.world.level.Level;

public class TimeUtil {
	
	public static final long TIME_DAY = 0L;
	public static final long TIME_NOON = 6000L;
	public static final long TIME_SUNSET = 12000L;
	public static final long TIME_NIGHT = 13000L;
	public static final long TIME_MIDNIGHT = 18000L;
	public static final long TIME_SUNRISE = 23000L;
	
	public static final long LENGTH_DAY = 24000L;
	
	public static long getTime(Level world) {
		return world.getGameTime();
	}
	
	public static long getTimeOfDay(Level world) {
		return world.getOverworldClockTime();
	}
	
	public static boolean isDay(Level world) {
		return world.isBrightOutside();
	}
	
	public static boolean isNight(Level world) {
		return world.isDarkOutside();
	}
	
	public static boolean isDayTime( Level world ) {
		return ( getTimeOfDay(world) < TIME_SUNSET || getTimeOfDay(world) >= TIME_SUNRISE );
	}
	
	public static boolean isNightTime( Level world ) {
		return !isDayTime( world );
	}
	
}
