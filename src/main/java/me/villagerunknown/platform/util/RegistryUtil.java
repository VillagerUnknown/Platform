package me.villagerunknown.platform.util;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class RegistryUtil {
	
	public static Identifier identifier( @Nullable String modId, String value ) {
		modId = PlatformUtil.getModIdOrDefault( modId );
		
		return Identifier.of( modId, value );
	}
	
	public static RegistryKey<ItemGroup> getItemGroup( Identifier identifier ) {
		return RegistryKey.of(Registries.ITEM_GROUP.getKey(), identifier);
	}
	
	public static ItemGroup registerItemGroup(RegistryKey<ItemGroup> groupRegistry, ItemGroup group ) {
		return Registry.register(Registries.ITEM_GROUP, groupRegistry, group);
	}
	
	public static void addItemToGroup( RegistryKey<ItemGroup> group, Item item ) {
		ItemGroupEvents.modifyEntriesEvent( group ).register(fabricItemGroupEntries -> fabricItemGroupEntries.add( item ));
	}
	
	public static Identifier registerStat(String id, @Nullable String modId, StatFormatter statFormatter) {
		Identifier identifier = identifier(modId, id);
		Registry.register(Registries.CUSTOM_STAT, id, identifier);
		Stats.CUSTOM.getOrCreateStat( identifier, statFormatter );
		return identifier;
	}
	
	public static SoundEvent registerSound(String id, @Nullable String modId) {
		Identifier identifier = identifier(modId, id);
		return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
	}
	
	public static Block registerBlock(String id, Block block, @Nullable String modId) {
		return Registry.register(Registries.BLOCK, identifier(modId, id), block);
	}
	
	public static Item registerItem(String id, Item item, @Nullable String modId) {
		return Registry.register(Registries.ITEM, identifier(modId, id), item);
	}
	
	public static Block registerBlockWithItem(String id, Block block, @Nullable String modId) {
		Block registeredBlock = registerBlock(id, block, modId);
		Item registeredItem = registerItem(id, new BlockItem(block, new Item.Settings()), modId);
		
		return registeredBlock;
	}
	
	public static Potion registerPotion(String id, Potion potion, @Nullable String modId) {
		return Registry.register( Registries.POTION, identifier(modId, id), potion );
	}
	
	public static void registerBrewingRecipe( RegistryEntry<Potion> potionIngredient, Item itemIngredient, RegistryEntry<Potion> potionResult ) {
		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(
					potionIngredient,
					itemIngredient,
					potionResult
			);
		});
	}
	
	public static EntityType<? extends Entity> registerEntity(String id, EntityType<? extends Entity> entity, @Nullable String modId ) {
		return Registry.register( Registries.ENTITY_TYPE, identifier( modId, id ), entity );
	}
	
}
