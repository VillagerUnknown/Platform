package me.villagerunknown.platform;

import me.shedaniel.autoconfig.ConfigData;
import me.villagerunknown.platform.feature.commandsFeature;
import me.villagerunknown.platform.feature.deathCoordinateNoticeFeature;
import me.villagerunknown.platform.feature.sleepNoticeFeature;
import me.villagerunknown.platform.feature.welcomeMessageFeature;
import me.villagerunknown.platform.manager.featureManager;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;

import java.util.*;

public class Platform implements ModInitializer {
	
	public static String PLATFORM_ID = "villagerunknown";
	public static String PLATFORM_PREFIX = PLATFORM_ID + "-";
	
	public static PlatformMod<PlatformConfigData> MOD = null;
	public static String MOD_ID = null;
	public static Logger LOGGER = null;
	public static PlatformConfigData CONFIG = null;
	
	public static Map<String, PlatformMod<? extends ConfigData>> MODS = new HashMap<>();
	
	public static List<Runnable> LOAD = new ArrayList<>();
	public static List<Runnable> UNLOAD = new ArrayList<>();
	
	@Override
	public void onInitialize() {
		// # Initialize Platform
		init_platform();
	}
	
	public static void define( String id ) {
		PLATFORM_ID = id;
		PLATFORM_PREFIX = PLATFORM_ID + "-";
	}
	
	public static void init_platform() {
		if( null != MOD ) {
			return;
		} // if
		
		MOD = new PlatformMod<>( "platform", Platform.class, PlatformConfigData.class );
		
		MOD_ID = MOD.getModId();
		LOGGER = MOD.getLogger();
		CONFIG = MOD.getConfig();
		
		init();
	}
	
	public static <T extends ConfigData> void init_mod( PlatformMod<T> mod ) {
		if( null == MOD ) {
			init_platform();
		} // if
		
		LOGGER.info("Initializing {} ({})", mod.getName(), mod.getModIdVersion());
	}
	
	private static void init() {
		init_mod( MOD );
		LOGGER.info("For support visit: {}", MOD.getHomepage());
		
		// # Register Network Payloads
		PlatformPayloads.registerPayloads();
		
		// # Activate Features
		featureManager.addFeature( "commands", commandsFeature::execute );
		
		featureManager.addFeature( "welcomeMessage", welcomeMessageFeature::execute );
		
		featureManager.addFeature( "sleepNotice", sleepNoticeFeature::execute );
		featureManager.addFeature( "deathCoordinateNotice", deathCoordinateNoticeFeature::execute );
	}
	
	public static <T extends ConfigData> PlatformMod<T> register(String modId, Class<?> loggerClass, Class<T> configClass) {
		if( null == MOD ) {
			init_platform();
		} // if
		
		PlatformMod<T> mod = new PlatformMod<>( modId, loggerClass, configClass );
		
		mod.getLogger().info("Registering: {}", mod.getModIdVersion());
		
		return register( mod.getModId(), mod );
	}
	
	public static <T extends ConfigData> PlatformMod<T> register( String id, PlatformMod<T> mod ) {
		MODS.put( id, mod );
		return mod;
	}
	
	public static boolean registered( String id ) {
		return MODS.containsKey( id );
	}
	
	public static PlatformMod<? extends ConfigData> registration( String id ) {
		return MODS.get( id );
	}
	
	public static void load() {
		LOGGER.info("Loading {} operations", LOAD.size());
		
		if( !LOAD.isEmpty() ) {
			for (Runnable runnable : LOAD) {
				runnable.run();
			} // for
		} // if
		
		LOGGER.info("Loading complete.");
	}
	
	public static void unload() {
		LOGGER.info("Unloading {} operations", UNLOAD.size());
		
		if( !UNLOAD.isEmpty() ) {
			for (Runnable runnable : UNLOAD) {
				runnable.run();
			} // for
		} // if
		
		LOGGER.info("Unloading complete.");
	}
	
	public static void reload() {
		LOGGER.info("Reloading {} operations", LOAD.size() + UNLOAD.size());
		
		unload();
		load();
		
		LOGGER.info("Reloading complete.");
	}
	
}
