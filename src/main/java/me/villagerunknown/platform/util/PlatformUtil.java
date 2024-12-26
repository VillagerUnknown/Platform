package me.villagerunknown.platform.util;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import static me.villagerunknown.platform.Platform.PLATFORM_ID;
import static me.villagerunknown.platform.Platform.PLATFORM_PREFIX;

public class PlatformUtil {

	public static Identifier formIdentifier( String modString ) {
		return Identifier.of( PLATFORM_ID, modString );
	}
	
	public static String getModIdOrDefault( @Nullable String modId ) {
		if( null == modId ) {
			modId = PLATFORM_ID;
		} // if
		
		return modId;
	}
	
	public static String formModId( String modString ) {
		return PLATFORM_PREFIX + modString;
	}
	
	public static String getModStringFromId( String modId ) {
		return modId.replace( PLATFORM_PREFIX, "" );
	}
	
}
