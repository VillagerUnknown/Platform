package me.villagerunknown.platform.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class LanguageUtil {
	
	public static String translationKey( String type, String modId, String value ) {
		return type + "." + modId + "." + value;
	}
	
	public static MutableComponent translate( String string ) {
		return Component.translatable( string );
	}
	
	public static MutableComponent translate( String string, Object... args ) {
		return Component.translatable( string, args );
	}
	
	public static String translateToString( String string ) {
		return translate( string ).getString();
	}
	
	public static String translateToString( String string, Object... args ) {
		return translate( string, args ).getString();
	}
	
}
