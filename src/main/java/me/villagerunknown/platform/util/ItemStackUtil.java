package me.villagerunknown.platform.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;

public class ItemStackUtil {
	
	public static ItemStack createNamedItemStack(Item item, Component customName, int maxStackSize ) {
		ItemStack stack = new ItemStack( item );
		stack.set( DataComponents.CUSTOM_NAME, customName );
		stack.set( DataComponents.MAX_STACK_SIZE, 1 );
		return stack;
	}
	
	public static ItemStack createPotionStack( Component name, Holder<Potion> potionEffect ) {
		ItemStack potion = new ItemStack( Items.POTION );
		
		potion.set(DataComponents.CUSTOM_NAME, name );
		potion.set(DataComponents.POTION_CONTENTS, new PotionContents(potionEffect));
		
		return potion;
	}
	
	public static ItemStack createWaterBottleStack() {
		ItemStack water = new ItemStack( Items.POTION, 1 );
		water.set(DataComponents.CUSTOM_NAME, Component.translatable( "item.minecraft.potion.effect.water" ) );
		return water;
	}
	
}
