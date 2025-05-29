package me.villagerunknown.platform.util;

import me.villagerunknown.platform.Platform;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

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
	
	public static Set<String> getInstalledJars(File dir) {
		if (!dir.exists() || !dir.isDirectory()) {
			return Set.of();
		}
		
		File[] modFiles = dir.listFiles((directory, name) -> name.contains( Platform.PLATFORM_PREFIX ) && name.endsWith(".jar"));
		
		if (modFiles == null) {
			return Set.of();
		}
		
		return Set.of(modFiles).stream()
				.map(file -> file.getName().replaceAll(".jar$", ""))
				.collect(Collectors.toSet());
	}
	
	public static Set<String> getInstalledLibs() {
		File libsDir = new File(FabricLoader.getInstance().getGameDir().getParent().toFile(), "libs");
		
		return getInstalledJars( libsDir );
	}
	
	public static Set<String> getInstalledMods() {
		File modsDir = new File(FabricLoader.getInstance().getGameDir().toFile(), "mods");
		
		return getInstalledJars( modsDir );
	}
	
	public static Set<String> getLoadedMods() {
		return FabricLoader.getInstance().getAllMods().stream()
				.map(modContainer -> modContainer.getMetadata().getId())
				.filter(PlatformUtil::isPlatformDependent)
				.collect(Collectors.toSet());
	}
	
	public static boolean isPlatformDependent( String modId ) {
		if( PLATFORM_ID.equals( "villagerunknown" ) ) {
			return modId.startsWith(PLATFORM_PREFIX) || modId.contains("villagercoin");
		} // if
		
		return modId.startsWith(PLATFORM_PREFIX);
	}
	
}