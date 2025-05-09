package me.villagerunknown.platform.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.adapter.ItemCountMapTypeAdapter;
import me.villagerunknown.platform.adapter.OptionalGlobalPosTypeAdapter;
import me.villagerunknown.platform.adapter.ProfileResultMapTypeAdapter;
import me.villagerunknown.platform.adapter.WorldPositionsMapTypeAdapter;
import me.villagerunknown.platform.data.ProfileResultData;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;

import java.lang.reflect.Type;
import java.util.*;

public class GsonUtil {
	
	public static final Map<Type, Object> ADAPTERS = new HashMap<>() {{
		put(new TypeToken<Map<Item, Integer>>(){}.getType(), new ItemCountMapTypeAdapter());
		put(new TypeToken<Optional<GlobalPos>>(){}.getType(), new OptionalGlobalPosTypeAdapter());
		put(new TypeToken<Map<UUID, ProfileResultData>>(){}.getType(), new ProfileResultMapTypeAdapter());
		put(new TypeToken<Map<String, Set<BlockPos>>>(){}.getType(), new WorldPositionsMapTypeAdapter());
	}};
	
	public static void registerAdapters( GsonBuilder gsonBuilder ) {
		if( null != ADAPTERS && !ADAPTERS.isEmpty() ) {
			ADAPTERS.forEach(gsonBuilder::registerTypeAdapter);
		} else {
			Platform.LOGGER.warn( "GSON Type Adapters are missing. Verify that included type adapters are working properly." );
		} // if, else
	}
	
	public static Gson gsonForFiles() {
		return new GsonBuilder()
				.setFieldNamingPolicy( FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES )
				.create();
	}
	
	public static Gson gsonWithAdapters() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		
		registerAdapters(gsonBuilder);
		
		return gsonBuilder.create();
	}
	
	public static Gson gsonWithAdapter( Type type ) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		
		if( null != ADAPTERS && ADAPTERS.containsKey( type ) ) {
			gsonBuilder.registerTypeAdapter( type, ADAPTERS.get( type ) );
		} else {
			Platform.LOGGER.warn( "Missing GSON Type Adapter: " + type );
		} // if
		
		return gsonBuilder.create();
	}
	
}
