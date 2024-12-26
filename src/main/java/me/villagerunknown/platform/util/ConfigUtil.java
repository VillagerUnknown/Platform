package me.villagerunknown.platform.util;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class ConfigUtil {
	
	public static <T extends ConfigData> T registerConfig( Class<T> configClass ) {
		AutoConfig.register( configClass, GsonConfigSerializer::new );
		return AutoConfig.getConfigHolder( configClass ).getConfig();
	}
	
}
