package me.villagerunknown.platform.manager;

import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.util.PlatformUtil;

import java.util.*;

public class featureManager {
	
	public static Map<Integer, Feature> FEATURES = new TreeMap<>();
	
	public static final int HALF_MAX_INT_VALUE = (int) Integer.MAX_VALUE / 2;
	public static final int QUARTER_MAX_INT_VALUE = (int) Integer.MAX_VALUE / 4;
	public static final int PADDED_LAST_INT_VALUE = (int) QUARTER_MAX_INT_VALUE * 3;
	
	public static void addFeatureFirst( String featureName, Runnable method ) {
		addFeature( featureName, method, getNextClosestQueuePosition( 0 ) );
	}
	
	public static void addFeatureMiddle( String featureName, Runnable method ) {
		addFeature( featureName, method, getNextClosestQueuePosition( HALF_MAX_INT_VALUE ) );
	}
	
	public static void addFeatureLast( String featureName, Runnable method, boolean absolute ) {
		if( absolute ) {
			addFeature(featureName, method, getPreviousClosestQueuePosition(Integer.MAX_VALUE));
		} else {
			addFeatureLast( featureName, method );
		} // if, else
	}
	
	public static void addFeatureLast( String featureName, Runnable method ) {
		addFeature( featureName, method, getNextClosestQueuePosition( PADDED_LAST_INT_VALUE ) );
	}
	
	public static void addFeature( String featureName, Runnable method ) {
		addFeatureMiddle( featureName, method );
	}
	
	public static void addFeature( String featureName, Runnable method, int loadPosition ) {
		FEATURES.put( loadPosition, new Feature( featureName, method, loadPosition ) );
		
		Platform.LOGGER.info("Feature queued: {} ({})", featureName, loadPosition);
	}
	
	public static void loadFeatures() {
		int loadedMods = PlatformUtil.getLoadedMods().size();
		
		Platform.LOGGER.info("Mods loaded: {}/{}", Platform.MODS.size(), loadedMods);
		
		if( Platform.MODS.size() == loadedMods ) {
			Platform.LOGGER.info("Loaded {} mod(s). Loading {} feature(s)...", loadedMods, FEATURES.size() );
			
			FEATURES.forEach((loadPosition, feature) -> {
				feature.RUNNABLE.run();
				
				Platform.LOGGER.info("Feature loaded: {}", feature.ID);
			});
			
			Platform.LOGGER.info("Loaded {} feature(s) from {} mod(s).", FEATURES.size(), loadedMods);
		} // if
	}
	
	public static int getNextClosestQueuePosition( int value ) {
		if( !FEATURES.isEmpty() && null != FEATURES.get(value) ) {
			for (int i = value; i < Integer.MAX_VALUE; i++) {
				if( null == FEATURES.get( i ) ) {
					return i;
				} // if
			} // for
		} // if
		
		return value;
	}
	
	public static int getPreviousClosestQueuePosition( int value ) {
		if( !FEATURES.isEmpty() && null != FEATURES.get(value) ) {
			for (int i = value; i > 0; i--) {
				if( null == FEATURES.get( i ) ) {
					return i;
				} // if
			} // for
		} // if
		
		return value;
	}
	
	public static class Feature {
		
		String ID;
		
		int LOAD_POSITION;
		
		Runnable RUNNABLE;
		
		public Feature( String id, Runnable runnable, int loadPosition ) {
			ID = id;
			RUNNABLE = runnable;
			LOAD_POSITION = loadPosition;
		}
		
	}
	
}