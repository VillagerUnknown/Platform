package me.villagerunknown.platform.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

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
	
	public static RegistryEntry<VillagerProfession> registerVillager(Identifier id, ImmutableList<BlockState> workstations, String professionKey, SoundEvent workSound ) {
		registerPointOfInterest( id, workstations, 1, 1 );
		return registerVillagerProfession( id, professionKey, workSound );
	}
	
	public static RegistryEntry<VillagerProfession> registerVillager(Identifier id, ImmutableList<BlockState> workstations, String professionKey, SoundEvent workSound, int ticketCount, int searchDistance ) {
		registerPointOfInterest( id, workstations, ticketCount, searchDistance );
		return registerVillagerProfession( id, professionKey, workSound );
	}
	
	public static RegistryEntry<PointOfInterestType> registerPointOfInterest(Identifier id, ImmutableList<BlockState> workstations, int ticketCount, int searchDistance ) {
		PointOfInterestType poiType = PointOfInterestHelper.register( id, ticketCount, searchDistance, workstations );
		
		Registry.register( Registries.POINT_OF_INTEREST_TYPE, id, poiType );
		
		return Registries.POINT_OF_INTEREST_TYPE.getEntry( poiType );
	}
	
	public static RegistryEntry<VillagerProfession> registerVillagerProfession(Identifier id, String professionKey, SoundEvent workSound ) {
		RegistryKey<PointOfInterestType> poiRegistryKey = RegistryKey.of( RegistryKeys.POINT_OF_INTEREST_TYPE, id );
		
		Predicate<RegistryEntry<PointOfInterestType>> predicate = (entry) -> entry.matchesKey( poiRegistryKey );
		
		VillagerProfession profession = new VillagerProfession( professionKey, predicate, predicate, ImmutableSet.of(), ImmutableSet.of(), workSound );
		
		Registry.register( Registries.VILLAGER_PROFESSION, id, profession );
		
		return Registries.VILLAGER_PROFESSION.getEntry( profession );
	}
	
}
