package me.villagerunknown.platform.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;

public class ItemStackUtil {
	
	public static ItemStack createNamedItemStack(Item item, Text customName, int maxStackSize ) {
		ItemStack stack = new ItemStack( item );
		stack.set( DataComponentTypes.CUSTOM_NAME, customName );
		stack.set( DataComponentTypes.MAX_STACK_SIZE, 1 );
		return stack;
	}
	
	public static ItemStack createPotionStack( Text name, RegistryEntry<Potion> potionEffect ) {
		ItemStack potion = new ItemStack( Items.POTION );
		
		potion.set(DataComponentTypes.ITEM_NAME, name );
		potion.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(potionEffect));
		
		return potion;
	}
	
	public static ItemStack createWaterBottleStack() {
		ItemStack water = new ItemStack( Items.POTION, 1 );
		water.set(DataComponentTypes.ITEM_NAME, Text.translatable( "item.minecraft.potion.effect.water" ) );
		return water;
	}
	
}
