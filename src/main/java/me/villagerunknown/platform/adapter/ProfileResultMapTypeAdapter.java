package me.villagerunknown.platform.adapter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import me.villagerunknown.platform.data.ProfileResultData;
import me.villagerunknown.platform.util.GsonUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ProfileResultMapTypeAdapter extends TypeAdapter<Map<UUID, ProfileResultData>> {
	
	private static final Gson gson = new Gson();
	
	@Override
	public void write(JsonWriter out, Map<UUID, ProfileResultData> value) throws IOException {
		out.beginObject();
		
		value.forEach(( uuid, profileResultData ) -> {
			try {
				DataResult<JsonElement> result = ProfileResultData.CODEC.encodeStart( JsonOps.INSTANCE, profileResultData );
				
				Optional<JsonElement> optionalJson = result.result();
				if( optionalJson.isPresent() ) {
					out.name( uuid.toString() );
					
					JsonElement jsonElement = optionalJson.get();
					out.value( jsonElement.toString() );
				} // if
			} catch (IOException e) {
				throw new RuntimeException(e);
			} // try, catch
		});
		
		out.endObject();
	}
	
	@Override
	public Map<UUID, ProfileResultData> read(JsonReader in) throws IOException {
		HashMap<UUID, ProfileResultData> profiles = new HashMap<>();
		
		in.beginObject();
		
		while (in.hasNext()) {
			UUID uuid = UUID.fromString( in.nextName() );
			
			JsonElement jsonElement = gson.fromJson( in.nextString(), JsonObject.class );
			
			ProfileResultData.CODEC.parse( JsonOps.INSTANCE, jsonElement );
			
			DataResult<ProfileResultData> dataResult = ProfileResultData.CODEC.parse( JsonOps.INSTANCE, jsonElement );
			
			Optional<ProfileResultData> result = dataResult.result();
			
			result.ifPresent(profileResultData -> profiles.put(uuid, profileResultData));
		} // while
		
		in.endObject();
		
		return profiles;
	}
}
