package me.villagerunknown.platform.util;

import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public class ItemUtil {
	
	/**
	 * Get Item From String
	 * @param string like "minecraft:stone_pickaxe"
	 * @return Item
	 */
	public static Item getItemFromString(String string ){
		String[] parts = string.split(":");
		Identifier id = Identifier.fromNamespaceAndPath( parts[0], parts[1] );
		return BuiltInRegistries.ITEM.getValue( id );
	}
	
}
