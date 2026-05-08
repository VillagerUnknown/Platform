package me.villagerunknown.platform.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public class ItemCountMapTypeAdapter extends TypeAdapter<Map<Item, Integer>> {
	
	@Override
	public void write(JsonWriter out, Map<Item, Integer> value) throws IOException {
		out.beginObject();
		
		value.forEach(( item, count ) -> {
			Identifier itemId = BuiltInRegistries.ITEM.getKey( item );
			
			try {
				out.name( itemId.toString() );
				out.value( count );
			} catch (IOException e) {
				throw new RuntimeException(e);
			} // try, catch
		});
		
		out.endObject();
	}
	
	@Override
	public HashMap<Item, Integer> read(JsonReader in) throws IOException {
		HashMap<Item, Integer> itemCounts = new HashMap<>();
		
		in.beginObject();
		
		while (in.hasNext()) {
			itemCounts.put( BuiltInRegistries.ITEM.getValue(Identifier.parse(in.nextName())), in.nextInt() );
		} // while
		
		in.endObject();
		
		return itemCounts;
	}
}
