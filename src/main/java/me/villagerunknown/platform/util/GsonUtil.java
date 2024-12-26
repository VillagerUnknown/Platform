package me.villagerunknown.platform.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.adapter.OptionalGlobalPosTypeAdapter;
import me.villagerunknown.platform.adapter.WorldPositionsMapTypeAdapter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class GsonUtil {
	
	public static Map<Type, Object> ADAPTERS = new HashMap<>() {{
		put(new TypeToken<Optional<GlobalPos>>(){}.getType(), new OptionalGlobalPosTypeAdapter());
		put(new TypeToken<Map<String, Set<BlockPos>>>(){}.getType(), new WorldPositionsMapTypeAdapter());
	}};
	
	public static GsonBuilder registerAdapters( GsonBuilder gsonBuilder ) {
		ADAPTERS.forEach(( Type type, Object object ) -> {
			gsonBuilder.registerTypeAdapter( type, object );
		});
		
		return gsonBuilder;
	}
	
	public static Gson gsonForFiles() {
		return new GsonBuilder()
				.setFieldNamingPolicy( FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES )
				.create();
	}
	
	public static Gson gsonWithAdapters() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		
		ADAPTERS.forEach(( Type type, Object object ) -> {
			gsonBuilder.registerTypeAdapter( type, object );
		});
		
		return gsonBuilder.create();
	}
	
	public static Gson gsonWithAdapter( Type type ) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		
		if( ADAPTERS.containsKey( type ) ) {
			gsonBuilder.registerTypeAdapter( type, ADAPTERS.get( type ) );
		} else {
			Platform.LOGGER.warn( "Missing Type Adapter: " + type );
		} // if
		
		return gsonBuilder.create();
	}
	
}
