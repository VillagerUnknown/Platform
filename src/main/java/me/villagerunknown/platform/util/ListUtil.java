package me.villagerunknown.platform.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ListUtil {
	
	public static List<String> PLAYER_NAMES = List.of(
			"VillagerUnknown"
	);
	
	public static List<String> HUMAN_NAMES = List.of(
			"Alden",
			"Briella",
			"Cedric",
			"Dahlia",
			"Elias",
			"Fiona",
			"Griffin",
			"Harlow",
			"Ivy",
			"Jorvik",
			"Kara",
			"Lyle",
			"Mira",
			"Nash",
			"Olwen",
			"Peregrine",
			"Ronan",
			"Sable",
			"Tamsin",
			"Ulric",
			"Vera",
			"Wren",
			"Xander",
			"Yara",
			"Zara",
			"Thorne",
			"Elyse",
			"Vesper",
			"Brenna",
			"Caius",
			"Dorian",
			"Elowen",
			"Fenric",
			"Galadriel",
			"Harlen",
			"Isolde",
			"Jasmine",
			"Kaelen",
			"Liora",
			"Merrick",
			"Nerissa",
			"Oberon",
			"Phaedra",
			"Rhiannon",
			"Sylvan",
			"Talon",
			"Viveka",
			"Wynne",
			"Xandra",
			"Ysolde",
			"Zephyr"
	);
	
	public static List<String> CAT_NAMES = List.of(
			"Nikola",
			"Fluffy",
			"Orion",
			"Rabbit",
			"Oliver",
			"Daisy",
			"Jasper",
			"Bella",
			"Felix",
			"Luna",
			"Chloe",
			"Leo",
			"Willow",
			"Sebastian",
			"Snickers",
			"Niblet",
			"Pudding",
			"Jellybean",
			"Bubbles",
			"Whiskers",
			"Tater Tot",
			"Peaches",
			"Poppy",
			"Sprout",
			"Nimbus",
			"Quibble",
			"Zephyr",
			"Sprocket",
			"Munchkin",
			"Tofu",
			"Tango",
			"Waffle",
			"Gizmo",
			"Jinxy",
			"Willow",
			"Aspen",
			"Maple",
			"Storm",
			"Sage",
			"River",
			"Luna",
			"Ocean",
			"Poppy",
			"Merlin",
			"Artemis",
			"Thor",
			"Pixie",
			"Loki",
			"Phoenix",
			"Zelda",
			"Willow",
			"Gandalf",
			"Freya"
	);
	
	public static List<String> DOG_NAMES = List.of(
			"Rover",
			"Ralph",
			"Chase",
			"Diesel",
			"Shadow",
			"Raven",
			"Rocky",
			"Hunter",
			"Rebel",
			"Fang",
			"Rogue",
			"Bandit",
			"Thor",
			"Max",
			"Bella",
			"Charlie",
			"Daisy",
			"Duke",
			"Lucy",
			"Rex",
			"Sadie",
			"Sampson",
			"Molly",
			"Biscuit",
			"Peanut",
			"Poppy",
			"Noodle",
			"Tater Tot",
			"Waffles",
			"Sprout",
			"Snickers",
			"Cupcake",
			"Bubbles",
			"Ziggy",
			"Mochi",
			"Sprocket",
			"Widget",
			"Pickles",
			"Tofu",
			"Munchkin",
			"Pudding",
			"Hubble",
			"River",
			"Aspen",
			"Willow",
			"Cedar",
			"Maple",
			"Storm",
			"Ocean",
			"Daisy",
			"Sky",
			"Rocky"
	);
	
	public static List<String> BIRD_NAMES = List.of(
			"Crackers",
			"Clinger",
			"Tori",
			"Kiwi",
			"Apollo",
			"Luna",
			"Ruby",
			"Merlin",
			"Oliver",
			"Bella",
			"Gatsby",
			"Sage",
			"Willow",
			"Twinkie",
			"Twinkle",
			"Pippin",
			"Peep",
			"Snickers",
			"Waffle",
			"Fuzz",
			"Popcorn",
			"Biscuit",
			"Niblet",
			"Bubbles",
			"Sky",
			"Cloud",
			"Aspen",
			"Storm",
			"Coral",
			"Pine",
			"Ember",
			"Breeze",
			"Meadow",
			"Pebble",
			"Phoenix",
			"Zephyr",
			"Merlin",
			"Freya",
			"Thor",
			"Lyra",
			"Valkyrie",
			"Willow",
			"Griffin",
			"Snappy",
			"Bitey",
			"Zuzu",
			"Noodle",
			"Tofu",
			"Wiggly",
			"Doodle",
			"Jinxy",
			"Sprocket",
			"Ziggy",
			"Gizmo",
			"Pickles",
			"Pepper",
			"Mango",
			"Cookie",
			"Coconut",
			"Squawk",
			"Squelch",
			"Chatter",
			"Chitter",
			"Tango",
			"Pesto",
			"Sapphire",
			"Ruby",
			"Topaz",
			"Emerald",
			"Indigo",
			"Opal",
			"Onyx",
			"Pearl",
			"Goldie",
			"Pesky Bird"
	);
	
	public static List<VillagerProfession> VILLAGER_PROFESSIONS = List.of(
			VillagerProfession.ARMORER,
			VillagerProfession.BUTCHER,
			VillagerProfession.CARTOGRAPHER,
			VillagerProfession.CLERIC,
			VillagerProfession.FARMER,
			VillagerProfession.FISHERMAN,
			VillagerProfession.FLETCHER,
			VillagerProfession.LEATHERWORKER,
			VillagerProfession.LIBRARIAN,
			VillagerProfession.MASON,
			VillagerProfession.NITWIT,
			VillagerProfession.SHEPHERD,
			VillagerProfession.TOOLSMITH,
			VillagerProfession.WEAPONSMITH
	);
	
	public static List<String> VILLAGER_PROFESSION_STRINGS = List.of(
			VillagerProfession.ARMORER.id().toLowerCase().replace("minecraft:",""),
			VillagerProfession.BUTCHER.id().toLowerCase().replace("minecraft:",""),
			VillagerProfession.CARTOGRAPHER.id().toLowerCase().replace("minecraft:",""),
			VillagerProfession.CLERIC.id().toLowerCase().replace("minecraft:",""),
			VillagerProfession.FARMER.id().toLowerCase().replace("minecraft:",""),
			VillagerProfession.FISHERMAN.id().toLowerCase().replace("minecraft:",""),
			VillagerProfession.FLETCHER.id().toLowerCase().replace("minecraft:",""),
			VillagerProfession.LEATHERWORKER.id().toLowerCase().replace("minecraft:",""),
			VillagerProfession.LIBRARIAN.id().toLowerCase().replace("minecraft:",""),
			VillagerProfession.MASON.id().toLowerCase().replace("minecraft:",""),
			VillagerProfession.NITWIT.id().toLowerCase().replace("minecraft:",""),
			VillagerProfession.SHEPHERD.id().toLowerCase().replace("minecraft:",""),
			VillagerProfession.TOOLSMITH.id().toLowerCase().replace("minecraft:",""),
			VillagerProfession.WEAPONSMITH.id().toLowerCase().replace("minecraft:","")
	);
	
	public static final List<Block> BEDS = List.of(
			Blocks.BLACK_BED,
			Blocks.BLUE_BED,
			Blocks.BROWN_BED,
			Blocks.CYAN_BED,
			Blocks.GRAY_BED,
			Blocks.GREEN_BED,
			Blocks.LIGHT_BLUE_BED,
			Blocks.LIGHT_GRAY_BED,
			Blocks.LIME_BED,
			Blocks.MAGENTA_BED,
			Blocks.ORANGE_BED,
			Blocks.PINK_BED,
			Blocks.PURPLE_BED,
			Blocks.RED_BED,
			Blocks.WHITE_BED,
			Blocks.YELLOW_BED
	);
	
	public static List<Block> FLAME_SOURCE_BLOCKS = List.of(
			Blocks.LAVA,
			Blocks.FIRE,
			Blocks.CAMPFIRE,
			Blocks.TORCH,
			Blocks.WALL_TORCH,
			Blocks.LAVA_CAULDRON,
			Blocks.JACK_O_LANTERN,
			Blocks.REDSTONE_TORCH,
			Blocks.REDSTONE_WALL_TORCH,
			
			Blocks.CANDLE,
			Blocks.RED_CANDLE,
			Blocks.GREEN_CANDLE,
			Blocks.BLUE_CANDLE,
			Blocks.ORANGE_CANDLE,
			Blocks.CYAN_CANDLE,
			Blocks.PURPLE_CANDLE,
			Blocks.PINK_CANDLE,
			Blocks.BROWN_CANDLE,
			Blocks.LIGHT_BLUE_CANDLE,
			Blocks.LIGHT_GRAY_CANDLE,
			Blocks.GRAY_CANDLE,
			Blocks.BLACK_CANDLE,
			Blocks.LIME_CANDLE,
			Blocks.MAGENTA_CANDLE,
			Blocks.YELLOW_CANDLE,
			Blocks.WHITE_CANDLE,
			
			Blocks.CANDLE_CAKE,
			Blocks.RED_CANDLE_CAKE,
			Blocks.GREEN_CANDLE_CAKE,
			Blocks.BLUE_CANDLE_CAKE,
			Blocks.ORANGE_CANDLE_CAKE,
			Blocks.CYAN_CANDLE_CAKE,
			Blocks.PURPLE_CANDLE_CAKE,
			Blocks.PINK_CANDLE_CAKE,
			Blocks.BROWN_CANDLE_CAKE,
			Blocks.LIGHT_BLUE_CANDLE_CAKE,
			Blocks.LIGHT_GRAY_CANDLE_CAKE,
			Blocks.GRAY_CANDLE_CAKE,
			Blocks.BLACK_CANDLE_CAKE,
			Blocks.LIME_CANDLE_CAKE,
			Blocks.MAGENTA_CANDLE_CAKE,
			Blocks.YELLOW_CANDLE_CAKE,
			Blocks.WHITE_CANDLE_CAKE
	);
	
	public static List<RegistryEntry<StatusEffect>> NEUTRAL_EFFECTS = List.of(
			StatusEffects.GLOWING,
			StatusEffects.SLOW_FALLING
	);
	
	public static List<RegistryEntry<StatusEffect>> POSITIVE_EFFECTS = List.of(
			StatusEffects.ABSORPTION,
			StatusEffects.RESISTANCE,
			StatusEffects.SPEED,
			StatusEffects.HASTE,
			StatusEffects.STRENGTH,
			StatusEffects.INSTANT_HEALTH,
			StatusEffects.JUMP_BOOST,
			StatusEffects.REGENERATION,
			StatusEffects.FIRE_RESISTANCE,
			StatusEffects.WATER_BREATHING,
			StatusEffects.NIGHT_VISION,
			StatusEffects.INVISIBILITY,
			StatusEffects.HEALTH_BOOST,
			StatusEffects.SATURATION,
			StatusEffects.LUCK,
			StatusEffects.CONDUIT_POWER,
			StatusEffects.DOLPHINS_GRACE,
			StatusEffects.HERO_OF_THE_VILLAGE
	);
	
	public static List<RegistryEntry<StatusEffect>> NEGATIVE_EFFECTS = List.of(
			StatusEffects.NAUSEA,
			StatusEffects.WEAKNESS,
			StatusEffects.BLINDNESS,
			StatusEffects.HUNGER,
			StatusEffects.SLOWNESS,
			StatusEffects.MINING_FATIGUE,
			StatusEffects.LEVITATION,
			StatusEffects.UNLUCK,
			StatusEffects.BAD_OMEN,
			StatusEffects.DARKNESS,
			StatusEffects.TRIAL_OMEN,
			StatusEffects.RAID_OMEN,
			StatusEffects.WIND_CHARGED,
			StatusEffects.WEAVING,
			StatusEffects.OOZING,
			StatusEffects.INFESTED
	);
	
	public static List<RegistryEntry<StatusEffect>> HARMFUL_EFFECTS = List.of(
			StatusEffects.INSTANT_DAMAGE,
			StatusEffects.WITHER,
			StatusEffects.POISON
	);
	
	private static final Random rand = new Random();
	
	public static String chooseRandomFromList(List<String> list) {
		if( list == null || list.isEmpty() ) {
			return list.get(rand.nextInt(list.size()));
		} else {
			return list.get(rand.nextInt(list.size()));
		} // if, else
	}
	
	@SafeVarargs
	public static List<RegistryKey<Biome>> buildRegistryKeyBiomeList(List<RegistryKey<Biome>> primary, List<RegistryKey<Biome>>... secondary ) {
		ArrayList<RegistryKey<Biome>> arrayList = new ArrayList<>(primary);
		for (List<RegistryKey<Biome>> list : secondary) {
			arrayList.addAll( list );
		} // for
		return Collections.unmodifiableList( arrayList );
	}
	
	@SafeVarargs
	public static List<RegistryKey<Structure>> buildRegistryKeyStructureList(List<RegistryKey<Structure>> primary, List<RegistryKey<Structure>>... secondary ) {
		ArrayList<RegistryKey<Structure>> arrayList = new ArrayList<>(primary);
		for (List<RegistryKey<Structure>> list : secondary) {
			arrayList.addAll( list );
		} // for
		return Collections.unmodifiableList( arrayList );
	}
	
	@SafeVarargs
	public static List<String> buildStringList(List<String> primary, List<String>... secondary ) {
		ArrayList<String> arrayList = new ArrayList<>(primary);
		for (List<String> list : secondary) {
			arrayList.addAll( list );
		} // for
		return Collections.unmodifiableList( arrayList );
	}
	
}
