package me.villagerunknown.platform.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

public class StringsListBuilder {
	
	private String fileName;
	private List<String> defaultList;
	private List<String> STRINGS;
	
	public StringsListBuilder(List<String> strings ) {
		this.defaultList = strings;
		
		load();
	}
	
	public StringsListBuilder(String fileName, List<String> defaultList ) {
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
		
		String message = "";
		
		if( file.exists() ) {
			try(FileReader reader = new FileReader(file)){
				Gson gson = GsonUtil.gsonForFiles();
				STRINGS = gson.fromJson( reader, new TypeToken<List<String>>(){}.getType() );
				
				if( STRINGS == null || STRINGS.isEmpty() ) {
					message = "No strings found in: " + fileName;
				} else {
					message = STRINGS.size() + " strings found in: " + fileName;
				} // if, else
			} catch (Exception e) {
				message = "Error: " + e.getMessage();
			} // try, catch
		} else {
			STRINGS = defaultList;
			
			JsonArray jsonArray = new JsonArray();
			for( String str : defaultList ) {
				jsonArray.add( str );
			} // for
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson( jsonArray );
			
			try{
				Files.write( filePath, json.getBytes() );
				message = "Created file: " + fileName;
			} catch( IOException e ) {
				message = "Error: " + e.getMessage();
			} // try, catch
		} // if, else
		
		if( null != Platform.LOGGER && !message.isEmpty() ) {
			Platform.LOGGER.info( message );
		} // if
		
		return STRINGS;
	}
	
	public List<String> getList() {
		return STRINGS;
	}
	
	public String getRandomString() {
		return ListUtil.chooseRandomFromList( STRINGS );
	}
	
}
