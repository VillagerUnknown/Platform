package me.villagerunknown.platform.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class LanguageUtil {
	
	public static String translationKey( String type, String modId, String value ) {
		return type + "." + modId + "." + value;
	}
	
	public static MutableText translate( String string ) {
		return Text.translatable( string );
	}
	
	public static MutableText translate( String string, Object... args ) {
		return Text.translatable( string, args );
	}
	
	public static String translateToString( String string ) {
		return translate( string ).getString();
	}
	
	public static String translateToString( String string, Object... args ) {
		return translate( string, args ).getString();
	}
	
}
