package me.villagerunknown.platform;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "villagerunknown-platform")
public class PlatformConfigData implements me.shedaniel.autoconfig.ConfigData {
	
	/**
	 * General
	 */
	
	@ConfigEntry.Category("General")
	public boolean enableVillagerComments = false;
	
	/**
	 * Commands
	 */
	
	@ConfigEntry.Category("Commands")
	public boolean enableCommands = true;
	
	@ConfigEntry.Category("Commands")
	public boolean enableCommandFeedbackSounds = true;
	
	/**
	 * Caching
	 */
	
	@ConfigEntry.Category("Caching")
	public boolean enablePlayerCaching = true;
	
	@ConfigEntry.Category("Caching")
	public boolean flushCacheOnServerRestart = false;
	
	/**
	 * Greeting
	 */
	
	@ConfigEntry.Category("Greeting")
	public boolean enableWelcomeMessage = false;
	
	@ConfigEntry.Category("Greeting")
	public String welcomeMessageNew = "Welcome! o7";
	
	@ConfigEntry.Category("Greeting")
	public String welcomeMessageReturning = "Welcome back! o7";
	
	/**
	 * Notices
	 */
	
	@ConfigEntry.Category("Notices")
	public boolean enableSleepNotice = false;
	
	@ConfigEntry.Category("Notices")
	public String sleepNoticeMessage = "Time to sleep!";
	
	@ConfigEntry.Category("Notices")
	public boolean enableDeathCoordinateNotice = false;
	
	/**
	 * Weather
	 */
	
	@ConfigEntry.Category("Weather")
	public boolean bedInteractionsAlwaysClearWeather = false;
	
	@ConfigEntry.Category("Weather")
	public boolean bedInteractionsClearWeatherAtNight = false;
	
	/**
	 * Nametags
	 */
	
	@ConfigEntry.Category("Nametags")
	public boolean hideNametags = false;
	
	@ConfigEntry.Category("Nametags")
	public boolean hidePlayerNametags = false;
	
	
}
