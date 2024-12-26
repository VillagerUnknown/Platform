package me.villagerunknown.platform.manager;

import me.villagerunknown.platform.Platform;

import java.util.HashMap;
import java.util.Map;

public class featureManager {
	
	Map<String, Runnable> FEATURES = new HashMap<>();
	
	public static void addFeature( String featureName, Runnable method ) {
		Platform.LOGGER.info( "Feature added: " + featureName );
		
		method.run();
	}
	
}
