package me.villagerunknown.platform.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

public class RegistryUtil {
	
	public static Identifier identifier( @Nullable String modId, String value ) {
		modId = PlatformUtil.getModIdOrDefault( modId );
		
		return Identifier.fromNamespaceAndPath( modId, value );
	}
	
	public static ResourceKey<CreativeModeTab> getItemGroup( Identifier identifier ) {
		return ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), identifier);
	}
	
	public static CreativeModeTab registerItemGroup(ResourceKey<CreativeModeTab> groupRegistry, CreativeModeTab group ) {
		return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, groupRegistry, group);
	}
	
	public static void addItemToGroup( ResourceKey<CreativeModeTab> group, Item item ) {
		CreativeModeTabEvents.modifyOutputEvent( group ).register(fabricItemGroupEntries -> fabricItemGroupEntries.accept( item ));
	}
	
	public static Identifier registerStat(String id, @Nullable String modId, StatFormatter statFormatter) {
		Identifier identifier = identifier(modId, id);
		Registry.register(BuiltInRegistries.CUSTOM_STAT, id, identifier);
		Stats.CUSTOM.get( identifier, statFormatter );
		return identifier;
	}
	
	public static SoundEvent registerSound(String id, @Nullable String modId) {
		Identifier identifier = identifier(modId, id);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createFixedRangeEvent(identifier, 16.0F));
	}
	
	public static Block registerBlock(String id, Block block, @Nullable String modId) {
		return Registry.register(BuiltInRegistries.BLOCK, identifier(modId, id), block);
	}
	
	public static Item registerItem(String id, Item item, @Nullable String modId) {
		return Registry.register(BuiltInRegistries.ITEM, identifier(modId, id), item);
	}
	
	public static Block registerBlockWithItem(String id, Block block, @Nullable String modId) {
		Block registeredBlock = registerBlock(id, block, modId);
		Item registeredItem = registerItem(id, new BlockItem(block, new Item.Properties()), modId);
		
		return registeredBlock;
	}
	
	public static Potion registerPotion(String id, Potion potion, @Nullable String modId) {
		return Registry.register( BuiltInRegistries.POTION, identifier(modId, id), potion );
	}
	
	public static void registerBrewingRecipe( Holder<Potion> potionIngredient, Item itemIngredient, Holder<Potion> potionResult ) {
		FabricPotionBrewingBuilder.BUILD.register(builder -> {
			builder.addMix(
					potionIngredient,
					itemIngredient,
					potionResult
			);
		});
	}
	
	public static EntityType<? extends Entity> registerEntity(String id, EntityType<? extends Entity> entity, @Nullable String modId ) {
		return Registry.register( BuiltInRegistries.ENTITY_TYPE, identifier( modId, id ), entity );
	}
	
	public static Holder<VillagerProfession> registerVillager(Identifier id, ImmutableList<BlockState> workstations, String professionKey, SoundEvent workSound ) {
		registerPointOfInterest( id, workstations, 1, 1 );
		return registerVillagerProfession( id, professionKey, workSound );
	}
	
	public static Holder<VillagerProfession> registerVillager(Identifier id, ImmutableList<BlockState> workstations, String professionKey, SoundEvent workSound, int ticketCount, int searchDistance ) {
		registerPointOfInterest( id, workstations, ticketCount, searchDistance );
		return registerVillagerProfession( id, professionKey, workSound );
	}
	
	public static Holder<PoiType> registerPointOfInterest(Identifier id, ImmutableList<BlockState> workstations, int ticketCount, int searchDistance ) {
//		ResourceKey<PoiType> resourceKey = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, id);
		
		PoiType poiType = new PoiType(new LinkedHashSet<>(workstations), ticketCount, searchDistance);
		
//		Registry.register(Registries.POINT_OF_INTEREST_TYPE, resourceKey, poiType);
		
		return BuiltInRegistries.POINT_OF_INTEREST_TYPE.wrapAsHolder( poiType );
	}
	
	public static Holder<VillagerProfession> registerVillagerProfession(Identifier id, String professionKey, SoundEvent workSound ) {
		ResourceKey<PoiType> poiRegistryKey = ResourceKey.create( Registries.POINT_OF_INTEREST_TYPE, id );
		
		Predicate<Holder<PoiType>> predicate = (entry) -> entry.is( poiRegistryKey );
		
		VillagerProfession profession = new VillagerProfession( Component.translatable("entity.minecraft.villager." + professionKey ), predicate, predicate, ImmutableSet.of(), ImmutableSet.of(), workSound, Int2ObjectMap.ofEntries());
		
		Registry.register( BuiltInRegistries.VILLAGER_PROFESSION, id, profession );
		
		return BuiltInRegistries.VILLAGER_PROFESSION.wrapAsHolder( profession );
	}
	
}
