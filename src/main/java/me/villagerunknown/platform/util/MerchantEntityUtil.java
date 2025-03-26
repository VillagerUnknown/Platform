package me.villagerunknown.platform.util;

import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerData;

import java.util.Optional;

public class MerchantEntityUtil {
	
	public static TradeOffer addTrade(MerchantEntity entity, TradedItem tradedItem1, ItemStack sellItem, int maxUses, int xpValue, float priceMultiplier ) {
		TradeOffer customTrade = new TradeOffer(tradedItem1, sellItem, maxUses, xpValue, priceMultiplier);
		entity.getOffers().add(customTrade);
		return customTrade;
	}
	
	public static TradeOffer addTrade(MerchantEntity entity, TradedItem tradedItem1, TradedItem tradedItem2, ItemStack sellItem, int maxUses, int xpValue, float priceMultiplier ) {
		TradeOffer customTrade = new TradeOffer(tradedItem1, Optional.ofNullable(tradedItem2), sellItem, maxUses, xpValue, priceMultiplier);
		entity.getOffers().add(customTrade);
		return customTrade;
	}
	
	public static void clearTrades( MerchantEntity entity ) {
		entity.getOffers().clear();
	}
	
}
