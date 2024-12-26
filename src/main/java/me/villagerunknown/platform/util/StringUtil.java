package me.villagerunknown.platform.util;

public class StringUtil {
	
	public static String capitalize( String string ) {
		return string.substring(0,1).toUpperCase() + string.substring(1);
	}
	
	public static boolean isSingular( int number ) {
		return (number <= 1);
	}
	
	public static boolean isPlural( int number ) {
		return !isSingular( number );
	}
	
	public static String getSingularOrPlural( int number, String singular, String plural ) {
		return ( isSingular( number ) ) ? singular : plural;
	}
	
}
