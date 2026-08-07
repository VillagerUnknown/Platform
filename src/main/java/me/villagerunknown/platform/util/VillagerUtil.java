package me.villagerunknown.platform.util;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerData;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class VillagerUtil {
	
	public static final int DEFAULT_MAX_USES = 12;
	public static final int COMMON_MAX_USES = 16;
	public static final int RARE_MAX_USES = 3;
	public static final int NOVICE_SELL_XP = 1;
	public static final int NOVICE_BUY_XP = 2;
	public static final int APPRENTICE_SELL_XP = 5;
	public static final int APPRENTICE_BUY_XP = 10;
	public static final int JOURNEYMAN_SELL_XP = 10;
	public static final int JOURNEYMAN_BUY_XP = 20;
	public static final int EXPERT_SELL_XP = 15;
	public static final int EXPERT_BUY_XP = 30;
	public static final int MASTER_TRADE_XP = 30;
	public static final float LOW_PRICE_MULTIPLIER = 0.05F;
	public static final float HIGH_PRICE_MULTIPLIER = 0.2F;
	
	public static MerchantOffer buyTradeOffer( int level, ItemCost demand, ItemStack supply ) {
		return new MerchantOffer(
				demand,
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "buy" ),
				getTradeMultiplier( level )
		);
	}
	
	public static MerchantOffer buyTradeOffer( int level, ItemCost demand, ItemCost demand2, ItemStack supply ) {
		return new MerchantOffer(
				demand,
				Optional.of(demand2),
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "buy" ),
				getTradeMultiplier( level )
		);
	}
	
	public static MerchantOffer buyTradeOffer( int level, Item costItem, int costAmount, Item saleItem, int saleAmount ) {
		ItemCost demand = new ItemCost( costItem, costAmount );
		ItemStack supply = new ItemStack( saleItem, saleAmount );
		
		return new MerchantOffer(
				demand,
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "buy" ),
				getTradeMultiplier( level )
		);
	}
	
	public static MerchantOffer buyTradeOffer( int level, Item costItem, int costAmount, Item costItem2, int costAmount2, Item saleItem, int saleAmount ) {
		ItemCost demand = new ItemCost( costItem, costAmount );
		ItemCost demand2 = new ItemCost( costItem2, costAmount2 );
		ItemStack supply = new ItemStack( saleItem, saleAmount );
		
		return new MerchantOffer(
				demand,
				Optional.of(demand2),
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "buy" ),
				getTradeMultiplier( level )
		);
	}
	
	public static MerchantOffer sellTradeOffer( int level, ItemCost demand, ItemStack supply ) {
		return new MerchantOffer(
				demand,
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "sell" ),
				getTradeMultiplier( level )
		);
	}
	
	public static MerchantOffer sellTradeOffer( int level, ItemCost demand, ItemCost demand2, ItemStack supply ) {
		return new MerchantOffer(
				demand,
				Optional.of(demand2),
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "sell" ),
				getTradeMultiplier( level )
		);
	}
	
	public static MerchantOffer sellTradeOffer( int level, Item costItem, int costAmount, Item saleItem, int saleAmount ) {
		ItemCost demand = new ItemCost( costItem, costAmount );
		ItemStack supply = new ItemStack( saleItem, saleAmount );
		
		return new MerchantOffer(
				demand,
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "sell" ),
				getTradeMultiplier( level )
		);
	}
	
	public static MerchantOffer sellTradeOffer( int level, Item costItem, int costAmount, Item costItem2, int costAmount2, Item saleItem, int saleAmount ) {
		ItemCost demand = new ItemCost( costItem, costAmount );
		ItemCost demand2 = new ItemCost( costItem2, costAmount2 );
		ItemStack supply = new ItemStack( saleItem, saleAmount );
		
		return new MerchantOffer(
				demand,
				Optional.of(demand2),
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "sell" ),
				getTradeMultiplier( level )
		);
	}
	
	public static int getMaxTrades( int level ) {
		return switch (level) {
			case 5 -> RARE_MAX_USES;
			case 4, 3 -> COMMON_MAX_USES;
			default -> DEFAULT_MAX_USES;
		};
	}
	
	public static int getTradeXp( int level, String type ) {
		return switch (level) {
			case 5 -> MASTER_TRADE_XP;
			case 4 -> ( Objects.equals(type, "buy") ) ? EXPERT_BUY_XP : EXPERT_SELL_XP;
			case 3 -> ( Objects.equals(type, "buy") ) ? JOURNEYMAN_BUY_XP : JOURNEYMAN_SELL_XP;
			case 2 -> ( Objects.equals(type, "buy") ) ? APPRENTICE_BUY_XP : APPRENTICE_SELL_XP;
			default -> ( Objects.equals(type, "buy") ) ? NOVICE_BUY_XP : NOVICE_SELL_XP;
		};
	}
	
	public static float getTradeMultiplier( int level ) {
		return switch (level) {
			case 5 -> HIGH_PRICE_MULTIPLIER;
			default -> LOW_PRICE_MULTIPLIER;
		};
	}
	
	public static void resetTrades( Villager villager, int minLevel ) {
		int level = villager.getVillagerData().level();
		MerchantOffers offers = new MerchantOffers();
		
		if( minLevel == level ) {
			MerchantOffers villagerOffers = villager.getOffers().copy();
			villagerOffers.removeLast();
			villagerOffers.removeLast();
			
			offers.addAll( villagerOffers );
		} // if
		
		villager.setOffers(null);
		
		for (int i = minLevel; i <= level; i++) {
			villager.setVillagerData( villager.getVillagerData().withLevel( i ) );
			
			offers.addAll( villager.getOffers() );
			
			villager.setOffers(null);
		} // for
		
		villager.setOffers( offers );
	}
	
	public static void resetAllTrades( Villager villager ) {
		resetTrades( villager, VillagerData.MIN_VILLAGER_LEVEL );
	}
	
	public static TagKey<VillagerTrade> createVillagerTradeTagKey(String modId, String path) {
		return (TagKey<VillagerTrade>) TagUtil.createTagKey(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(modId, path));
	}
	
	public static ResourceKey<TradeSet> createVillagerTradeSetResourceKey(String modId, String path) {
		return ResourceKey.create(Registries.TRADE_SET, Identifier.fromNamespaceAndPath(modId, path));
	}
	
	public static class CustomVillager {
		
		public Identifier IDENTIFIER;
		
		public ImmutableList<BlockState> WORKSTATIONS;
		
		public SoundEvent SOUND;
		
		public Holder<VillagerProfession> REGISTRY_ENTRY;
		
		public VillagerProfession PROFESSION;
	
		public CustomVillager( Identifier id, ImmutableList<BlockState> workstations, String professionKey, SoundEvent workSound, @Nullable Int2ObjectMap<ResourceKey<TradeSet>> tradeSetsByLevel ) {
			IDENTIFIER = id;
			WORKSTATIONS = workstations;
			SOUND = workSound;
			REGISTRY_ENTRY = RegistryUtil.registerVillager( id, workstations, professionKey, workSound, tradeSetsByLevel );
			PROFESSION = REGISTRY_ENTRY.value();
		}
		
	}
	
}
