package me.villagerunknown.platform.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.util.FileUtil;
import me.villagerunknown.platform.util.GsonUtil;
import me.villagerunknown.platform.util.ListUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringsMapBuilder {
	
	private String fileName;
	private List<String> defaultList;
	private Map<String, String> STRINGS;
	
	public StringsMapBuilder(List<String> strings ) {
		this.defaultList = strings;
		
		load();
	}
	
	public StringsMapBuilder(String fileName, List<String> defaultList ) {
		this.fileName = fileName;
		this.defaultList = defaultList;
		
		load();
	}
	
	public void load() {
		if( false == this.fileName.isEmpty() ) {
			loadStringsFromFile( this.fileName, this.defaultList );
		} else {
			STRINGS = new HashMap<>();
			for (String string : defaultList) {
				STRINGS.putIfAbsent( string, string );
			} // for
		} // if, else
	}
	
	private Map<String,String> loadStringsFromFile(String fileName, List<String> defaultList) {
		Path filePath = FileUtil.getConfigPath( fileName );
		File file = filePath.toFile();
		
		if( file.exists() ) {
			try(FileReader reader = new FileReader(file)){
				Gson gson = GsonUtil.gsonForFiles();
				STRINGS = gson.fromJson( reader, new TypeToken<Map<String, String>>(){}.getType() );
				
				if( STRINGS == null || STRINGS.isEmpty() ) {
					Platform.LOGGER.info( Platform.MOD_ID + " no strings found in: " + fileName );
				} else {
					Platform.LOGGER.info( STRINGS.size() + " strings found in: " + fileName );
				} // if, else
			} catch (Exception e) {
				Platform.LOGGER.error( Platform.MOD_ID + " Error: " + e.getMessage(), e );
			} // try, catch
		} else {
			Platform.LOGGER.info( Platform.MOD_ID + " failed to load file: " + fileName );
			
			STRINGS = new HashMap<>();
			for (String string : defaultList) {
				STRINGS.putIfAbsent( string, string );
			} // for
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson( STRINGS );
			
			try{
				Files.write( filePath, json.getBytes() );
				Platform.LOGGER.info( Platform.MOD_ID + " created file: " + fileName );
			} catch( IOException e ) {
				Platform.LOGGER.error( Platform.MOD_ID + " Error: " + e.getMessage(), e );
			} // try, catch
		} // if, else
		
		return STRINGS;
	}
	
	public Map<String, String> getMap() {
		return STRINGS;
	}
	
	public String getRandomString() {
		return ListUtil.chooseRandomFromList( STRINGS.values().stream().toList() );
	}
	
	public String getString( String key ) {
		if( STRINGS.containsKey( key ) ) {
			return STRINGS.get(key);
		} // if
		
		return key;
	}
	
}
