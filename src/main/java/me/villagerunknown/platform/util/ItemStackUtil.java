package me.villagerunknown.platform.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class ItemStackUtil {
	
	public static ItemStack createNamedItemStack(Item item, Text customName, int maxStackSize ) {
		ItemStack stack = new ItemStack( item );
		stack.set( DataComponentTypes.CUSTOM_NAME, customName );
		stack.set( DataComponentTypes.MAX_STACK_SIZE, 1 );
		return stack;
	}
	
}
