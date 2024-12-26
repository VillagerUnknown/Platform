package me.villagerunknown.platform.util;

public class ArrayUtil {
	
	public static String[] removeFirstElement(String[] array) {
		String[] newArray = new String[array.length - 1];
		System.arraycopy( array, 1, newArray, 0, newArray.length );
		return newArray;
	}
	
	public static String joinValues( String[] array, String glue ) {
		String value = "";
		
		for (String s : array) {
			value += s + glue;
		} // for
		
		return value;
	}
	
}
