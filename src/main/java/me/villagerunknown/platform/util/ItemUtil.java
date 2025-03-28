package me.villagerunknown.platform.util;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class ItemUtil {
	
	/**
	 * Get Item From String
	 * @param string like "minecraft:stone_pickaxe"
	 * @return Item
	 */
	public static Item getItemFromString(String string ){
		String[] parts = string.split(":");
		Identifier id = Identifier.of( parts[0], parts[1] );
		Optional<RegistryEntry.Reference<Item>> entry = Registries.ITEM.getEntry(id);
		return entry.map(RegistryEntry.Reference::value).orElse(null);
	}
	
}
