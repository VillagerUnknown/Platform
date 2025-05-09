package me.villagerunknown.platform;

import me.shedaniel.autoconfig.ConfigData;
import me.villagerunknown.platform.util.ConfigUtil;
import me.villagerunknown.platform.util.PlatformUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ContactInformation;
import net.fabricmc.loader.api.metadata.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

public class PlatformMod <T extends ConfigData> {
	
	private String MOD_ID = null;
	
	private Logger LOGGER = null;
	
	private T CONFIG = null;
	
	public PlatformMod(String modId, Class<?> loggerClass) {
		MOD_ID = PlatformUtil.formModId( modId );
		LOGGER = LoggerFactory.getLogger( loggerClass );
	}
	
	public PlatformMod(String modId, Class<?> loggerClass, Class<T> configClass) {
		MOD_ID = PlatformUtil.formModId( modId );
		LOGGER = LoggerFactory.getLogger( loggerClass );
		CONFIG = ConfigUtil.registerConfig( configClass );
	}
	
	public String getModId() {
		return MOD_ID;
	}
	
	public String getModIdVersion() {
		return MOD_ID + "-" + getVersion();
	}
	
	public Logger getLogger() {
		return LOGGER;
	}
	
	public T getConfig() {
		return CONFIG;
	}
	
	public Optional<ModContainer> getContainer() {
		return FabricLoader.getInstance().getModContainer(MOD_ID);
	}
	
	public String getVersion() {
		return getContainer()
				.map(modContainer -> modContainer.getMetadata().getVersion().getFriendlyString())
				.orElse("0.0.0");
	}
	
	public String getName() {
		return getContainer()
				.map(modContainer -> modContainer.getMetadata().getName())
				.orElse("[Missing Name]");
	}
	
	public String getDescription() {
		return getContainer()
				.map(modContainer -> modContainer.getMetadata().getDescription())
				.orElse("[Missing Description]");
	}
	
	public Optional<Collection<Person>> getAuthors() {
		return getContainer()
				.map(modContainer -> modContainer.getMetadata().getAuthors());
	}
	
	public Optional<ContactInformation> getContact() {
		return getContainer()
				.map(modContainer -> modContainer.getMetadata().getContact());
	}
	
	public String getContact( String type ) {
		Optional<ContactInformation> contact = getContact();
		String url = "";
		
		if( contact.isPresent() ) {
			Optional<String> property = contact.get().get(type);
			if( property.isPresent() ) {
				url = property.get();
			}
		}
		
		return url;
	}
	
	public String getHomepage() {
		return getContact( "homepage" );
	}
	
	public String getSourcesURL() {
		return getContact( "sources" );
	}
	
	public String getIssuesURL() {
		return getContact( "issues" );
	}
	
}