package me.villagerunknown.platform;

import me.shedaniel.autoconfig.ConfigData;
import me.villagerunknown.platform.feature.*;
import me.villagerunknown.platform.manager.featureManager;
import me.villagerunknown.platform.util.PlatformUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;

import java.util.*;

public class Platform implements ModInitializer {
	
	public static String PLATFORM_ID = "villagerunknown";
	public static String PLATFORM_PREFIX = PLATFORM_ID + "-";
	
	public static PlatformMod<PlatformConfigData> MOD = null;
	public static String MOD_ID = null;
	public static Logger LOGGER = null;
	public static PlatformConfigData CONFIG;
	
	public static Map<String, PlatformMod<? extends ConfigData>> MODS = new HashMap<>();
	
	public static List<Runnable> LOAD = new ArrayList<>();
	public static List<Runnable> UNLOAD = new ArrayList<>();
	
	public static final boolean IS_DEV_ENV = FabricLoader.getInstance().isDevelopmentEnvironment();
	
	@Override
	public void onInitialize() {
		// # Initialize Platform
		init_platform();
	}
	
	public static boolean isDevEnv() {
		return IS_DEV_ENV;
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
		
		LOGGER.info("Loading {} mod(s).", PlatformUtil.getLoadedMods().size());
		
		if( isDevEnv() ) {
			LOGGER.info("Development environment detected.");
		} // if
		
		init_mod( MOD );
		
		// # Register Network Payloads
		PlatformPayloads.registerPayloads();
		
		// # Activate Primary Features
		featureManager.addFeatureFirst( "commands", commandsFeature::execute );
		featureManager.addFeatureFirst( "playerCache", playerCacheFeature::execute );
		
		// # Activate Extra Features
		featureManager.addFeature( "welcomeMessage", welcomeMessageFeature::execute );
		featureManager.addFeature( "sleepNotice", sleepNoticeFeature::execute );
		featureManager.addFeature( "deathCoordinateNotice", deathCoordinateNoticeFeature::execute );
		featureManager.addFeature( "bedClearsWeather", bedClearsWeatherFeature::execute );
		featureManager.addFeature( "hideNametags", hideNametagsFeature::execute );
		
		// # Load Features
		featureManager.loadFeatures();
	}
	
	public static <T extends ConfigData> void init_mod( PlatformMod<T> mod ) {
		if( null == MOD ) {
			init_platform();
		} // if
		
		MODS.put( mod.getModIdVersion(), mod );
		
		LOGGER.info("Initializing {} ({})", mod.getName(), mod.getModIdVersion());
		LOGGER.info("For support visit: {}", mod.getIssuesURL());
	}
	
	public static <T extends ConfigData> PlatformMod<T> register( PlatformMod<T> mod ) {
		mod.getLogger().info("Registering: {}", mod.getModIdVersion());
		
		return mod;
	}
	
	public static <T extends ConfigData> PlatformMod<T> register(String modId, Class<?> loggerClass) {
		PlatformMod<T> mod = new PlatformMod<>( modId, loggerClass );
		
		return register( mod );
	}
	
	public static <T extends ConfigData> PlatformMod<T> register(String modId, Class<?> loggerClass, Class<T> configClass) {
		PlatformMod<T> mod = new PlatformMod<>( modId, loggerClass, configClass );
		
		return register( mod );
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
