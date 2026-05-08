package me.villagerunknown.platform.util;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public class PotionsUtil {
	
	public static final ItemStack HEALING_POTION = createPotionStack( Component.translatable("item.minecraft.potion.effect.healing"), Potions.HEALING );
	public static final ItemStack STRONG_HEALING_POTION = createPotionStack( Component.translatable("item.minecraft.potion.effect.healing"), Potions.STRONG_HEALING );
	public static final ItemStack REGENERATION_POTION = createPotionStack( Component.translatable("item.minecraft.potion.effect.regeneration"), Potions.REGENERATION );
	public static final ItemStack STRONG_REGENERATION_POTION = createPotionStack( Component.translatable("item.minecraft.potion.effect.regeneration"), Potions.STRONG_REGENERATION );
	public static final ItemStack LONG_REGENERATION_POTION = createPotionStack( Component.translatable("item.minecraft.potion.effect.regeneration"), Potions.LONG_REGENERATION );
	
	public static ItemStack createPotionStack(Component name, Holder<Potion> potionEffect ) {
		return ItemStackUtil.createPotionStack( name, potionEffect );
	}
	
}
