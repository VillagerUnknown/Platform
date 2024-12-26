package me.villagerunknown.platform.util;

import net.minecraft.world.World;

public class TimeUtil {
	
	public static final long TIME_DAY = 0L;
	public static final long TIME_NOON = 6000L;
	public static final long TIME_SUNSET = 12000L;
	public static final long TIME_NIGHT = 13000L;
	public static final long TIME_MIDNIGHT = 18000L;
	public static final long TIME_SUNRISE = 23000L;
	
	public static final long LENGTH_DAY = 24000L;
	
	public static long getTime(World world) {
		return world.getTime();
	}
	
	public static long getTimeOfDay(World world) {
		return world.getTimeOfDay();
	}
	
	public static boolean isDay(World world) {
		return world.isDay();
	}
	
	public static boolean isNight(World world) {
		return world.isNight();
	}
	
	public static boolean isDayTime( World world ) {
		return ( world.getTimeOfDay() < TIME_SUNSET || world.getTimeOfDay() >= TIME_SUNRISE );
	}
	
	public static boolean isNightTime( World world ) {
		return !isDayTime( world );
	}
	
}
