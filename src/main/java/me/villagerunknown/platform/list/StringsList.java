package me.villagerunknown.platform.list;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.util.GsonUtil;
import me.villagerunknown.platform.util.ListUtil;
import me.villagerunknown.platform.util.FileUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class StringsList {
	
	private static String fileName;
	private static List<String> defaultList;
	private static List<String> STRINGS;
	
	public StringsList(List<String> strings ) {
		this.defaultList = strings;
		
		load();
	}
	
	public StringsList(String fileName, List<String> defaultList ) {
		this.fileName = fileName;
		this.defaultList = defaultList;
		
		load();
	}
	
	public void load() {
		if( false == this.fileName.isEmpty() ) {
			loadStringsFromFile( this.fileName, this.defaultList );
		} else {
			STRINGS = this.defaultList;
		} // if, else
	}
	
	private List<?> loadStringsFromFile(String fileName, List<String> defaultList) {
		Path filePath = FileUtil.getConfigPath( fileName );
		File file = filePath.toFile();
		
		if( file.exists() ) {
			try(FileReader reader = new FileReader(file)){
				Gson gson = GsonUtil.gsonForFiles();
				STRINGS = gson.fromJson( reader, new TypeToken<List<String>>(){}.getType() );
				
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
			
			STRINGS = defaultList;
			
			JsonArray jsonArray = new JsonArray();
			for( String str : defaultList ) {
				jsonArray.add( str );
			} // for
			
			Gson gson = new Gson();
			String json = gson.toJson( jsonArray );
			
			try{
				Files.write( filePath, json.getBytes() );
				Platform.LOGGER.info( Platform.MOD_ID + " created file: " + fileName );
			} catch( IOException e ) {
				Platform.LOGGER.error( Platform.MOD_ID + " Error: " + e.getMessage(), e );
			} // try, catch
		} // if, else
		
		return STRINGS;
	}
	
	public String getRandomString() {
		return ListUtil.chooseRandomFromList( STRINGS );
	}
	
}
