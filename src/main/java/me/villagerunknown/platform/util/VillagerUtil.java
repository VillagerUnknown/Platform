package me.villagerunknown.platform.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.village.*;

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
	
	public static TradeOffer buyTradeOffer( int level, TradedItem demand, ItemStack supply ) {
		return new TradeOffer(
				demand,
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "buy" ),
				getTradeMultiplier( level )
		);
	}
	
	public static TradeOffer buyTradeOffer( int level, TradedItem demand, TradedItem demand2, ItemStack supply ) {
		return new TradeOffer(
				demand,
				Optional.of(demand2),
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "buy" ),
				getTradeMultiplier( level )
		);
	}
	
	public static TradeOffer buyTradeOffer( int level, Item costItem, int costAmount, Item saleItem, int saleAmount ) {
		TradedItem demand = new TradedItem( costItem, costAmount );
		ItemStack supply = new ItemStack( saleItem, saleAmount );
		
		return new TradeOffer(
				demand,
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "buy" ),
				getTradeMultiplier( level )
		);
	}
	
	public static TradeOffer buyTradeOffer( int level, Item costItem, int costAmount, Item costItem2, int costAmount2, Item saleItem, int saleAmount ) {
		TradedItem demand = new TradedItem( costItem, costAmount );
		TradedItem demand2 = new TradedItem( costItem2, costAmount2 );
		ItemStack supply = new ItemStack( saleItem, saleAmount );
		
		return new TradeOffer(
				demand,
				Optional.of(demand2),
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "buy" ),
				getTradeMultiplier( level )
		);
	}
	
	public static TradeOffer sellTradeOffer( int level, TradedItem demand, ItemStack supply ) {
		return new TradeOffer(
				demand,
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "sell" ),
				getTradeMultiplier( level )
		);
	}
	
	public static TradeOffer sellTradeOffer( int level, TradedItem demand, TradedItem demand2, ItemStack supply ) {
		return new TradeOffer(
				demand,
				Optional.of(demand2),
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "sell" ),
				getTradeMultiplier( level )
		);
	}
	
	public static TradeOffer sellTradeOffer( int level, Item costItem, int costAmount, Item saleItem, int saleAmount ) {
		TradedItem demand = new TradedItem( costItem, costAmount );
		ItemStack supply = new ItemStack( saleItem, saleAmount );
		
		return new TradeOffer(
				demand,
				supply,
				getMaxTrades( level ),
				getTradeXp( level, "sell" ),
				getTradeMultiplier( level )
		);
	}
	
	public static TradeOffer sellTradeOffer( int level, Item costItem, int costAmount, Item costItem2, int costAmount2, Item saleItem, int saleAmount ) {
		TradedItem demand = new TradedItem( costItem, costAmount );
		TradedItem demand2 = new TradedItem( costItem2, costAmount2 );
		ItemStack supply = new ItemStack( saleItem, saleAmount );
		
		return new TradeOffer(
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
	
	public static void resetTrades( VillagerEntity villager ) {
		villager.setOffers(null);
		
		int level = villager.getVillagerData().level();
		TradeOfferList offers = new TradeOfferList();
		
		for (int i = VillagerData.MIN_LEVEL; i <= level; i++) {
			villager.setVillagerData( villager.getVillagerData().withLevel( i ) );
			
			offers.addAll( villager.getOffers() );
			villager.setOffers(null);
		} // for
		
		villager.setOffers( offers );
	}
	
	public static class CustomVillager {
		
		public Identifier IDENTIFIER;
		
		public ImmutableList<BlockState> WORKSTATIONS;
		
		public SoundEvent SOUND;
		
		public RegistryEntry<VillagerProfession> REGISTRY_ENTRY;
		
		public VillagerProfession PROFESSION;
	
		public CustomVillager( Identifier id, ImmutableList<BlockState> workstations, String professionKey, SoundEvent workSound ) {
			IDENTIFIER = id;
			WORKSTATIONS = workstations;
			SOUND = workSound;
			REGISTRY_ENTRY = RegistryUtil.registerVillager( id, workstations, professionKey, workSound );
			PROFESSION = REGISTRY_ENTRY.value();
		}
		
	}
	
}
