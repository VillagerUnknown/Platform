package me.villagerunknown.platform.adapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class WorldPositionsMapTypeAdapter extends TypeAdapter<Map<String, Set<BlockPos>>> {
	
	private final Gson gson = new Gson();
	
	@Override
	public void write(JsonWriter out, Map<String, Set<BlockPos>> value) throws IOException {
		out.beginObject();
		
		value.forEach(( String rkWorld, Set<BlockPos> positions ) -> {
			try {
				out.name(rkWorld);
				
				out.beginArray();
				
				for (BlockPos blockPos : positions) {
					gson.toJson(blockPos, BlockPos.class, out);
				} // for
				
				out.endArray();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} // try, catch
		});
		
		out.endObject();
	}
	
	@Override
	public Map<String, Set<BlockPos>> read(JsonReader in) throws IOException {
		Map<String, Set<BlockPos>> resultMap = new HashMap<>();
		
		in.beginObject();
		
		while (in.hasNext()) {
			String keyString = in.nextName();
			
			Identifier id = Identifier.tryParse(keyString);
			
			if( null != id && !id.toString().contains("null") ) {
				ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, id);
				
				Set<BlockPos> blockPosSet = new HashSet<>();
				
				in.beginArray();
				
				while( in.hasNext() ) {
					BlockPos blockPos = gson.fromJson( in, BlockPos.class );
					blockPosSet.add( blockPos );
				} // while
				
				in.endArray();
				
				resultMap.put(keyString, blockPosSet);
			} // if
		}
		
		in.endObject();
		
		return resultMap;
	}

}
