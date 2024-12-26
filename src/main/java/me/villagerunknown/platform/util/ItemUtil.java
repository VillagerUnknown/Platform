package me.villagerunknown.platform.util;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;

public class ItemUtil {
	
	/**
	 * Get Item From String
	 * @param string like "minecraft:stone_pickaxe"
	 * @return Item
	 */
	public static Item getItemFromString(String string ){
		String[] parts = string.split(":");
		Identifier id = Identifier.of( parts[0], parts[1] );
		return Registries.ITEM.get( id );
	}
	
}
