package me.villagerunknown.platform.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
	
	public static Path getConfigPath( String fileName ) {
		Path configDir = Paths.get("config");
		return configDir.resolve( fileName );
	}
	
}
