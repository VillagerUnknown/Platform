package me.villagerunknown.platform.util;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;

public class PotionsUtil {
	
	public static final ItemStack HEALING_POTION = createPotionStack( Text.translatable("item.minecraft.potion.effect.healing"), Potions.HEALING );
	public static final ItemStack STRONG_HEALING_POTION = createPotionStack( Text.translatable("item.minecraft.potion.effect.healing"), Potions.STRONG_HEALING );
	public static final ItemStack REGENERATION_POTION = createPotionStack( Text.translatable("item.minecraft.potion.effect.regeneration"), Potions.REGENERATION );
	public static final ItemStack STRONG_REGENERATION_POTION = createPotionStack( Text.translatable("item.minecraft.potion.effect.regeneration"), Potions.STRONG_REGENERATION );
	public static final ItemStack LONG_REGENERATION_POTION = createPotionStack( Text.translatable("item.minecraft.potion.effect.regeneration"), Potions.LONG_REGENERATION );
	
	public static ItemStack createPotionStack(Text name, RegistryEntry<Potion> potionEffect ) {
		return ItemStackUtil.createPotionStack( name, potionEffect );
	}
	
}
