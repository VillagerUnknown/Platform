package me.villagerunknown.platform.util;

public class StringUtil {
	
	public static String capitalize( String string ) {
		return string.substring(0,1).toUpperCase() + string.substring(1);
	}
	
	public static String capitalizeAll( String string ) {
		String[] words = string.split(" ");
		
		StringBuilder sentence = new StringBuilder();
		
		if( words.length > 0 ) {
			for (String word : words) {
				sentence.append( capitalize( word ) ).append(" ");
			} // for
		} // if
		
		return sentence.toString().trim();
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
