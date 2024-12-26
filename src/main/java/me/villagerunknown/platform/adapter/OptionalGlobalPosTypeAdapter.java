package me.villagerunknown.platform.adapter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.math.GlobalPos;

import java.io.IOException;
import java.util.Optional;

public class OptionalGlobalPosTypeAdapter extends TypeAdapter<Optional<GlobalPos>> {
	
	private final Gson gson = new Gson();
	
	@Override
	public void write(JsonWriter out, Optional<GlobalPos> value) throws IOException {
		if (value.isPresent()) {
			// If the Optional contains a value, serialize the GlobalPos
			gson.toJson(value.get(), GlobalPos.class, out);
		} else {
			// If the Optional is empty, serialize as null
			out.nullValue();
		}
	}
	
	@Override
	public Optional<GlobalPos> read(JsonReader in) throws IOException {
		// Read the JSON element
		JsonElement element = JsonParser.parseReader(in);
		
		// If the element is null, return Optional.empty()
		if (element.isJsonNull()) {
			return Optional.empty();
		}
		
		// Otherwise, deserialize the GlobalPos and return it in an Optional
		GlobalPos globalPos = gson.fromJson(element, GlobalPos.class);
		return Optional.of(globalPos);
	}
}
