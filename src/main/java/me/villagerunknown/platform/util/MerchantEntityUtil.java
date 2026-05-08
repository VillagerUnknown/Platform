package me.villagerunknown.platform.util;

import java.util.Optional;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

public class MerchantEntityUtil {
	
	public static MerchantOffer addTrade(AbstractVillager entity, ItemCost tradedItem1, ItemStack sellItem, int maxUses, int xpValue, float priceMultiplier ) {
		MerchantOffer customTrade = new MerchantOffer(tradedItem1, sellItem, maxUses, xpValue, priceMultiplier);
		entity.getOffers().add(customTrade);
		return customTrade;
	}
	
	public static MerchantOffer addTrade(AbstractVillager entity, ItemCost tradedItem1, ItemCost tradedItem2, ItemStack sellItem, int maxUses, int xpValue, float priceMultiplier ) {
		MerchantOffer customTrade = new MerchantOffer(tradedItem1, Optional.ofNullable(tradedItem2), sellItem, maxUses, xpValue, priceMultiplier);
		entity.getOffers().add(customTrade);
		return customTrade;
	}
	
	public static void clearTrades( AbstractVillager entity ) {
		entity.getOffers().clear();
	}
	
}
