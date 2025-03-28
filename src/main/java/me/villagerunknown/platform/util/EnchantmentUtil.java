package me.villagerunknown.platform.util;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;

public class EnchantmentUtil {
	
	public static ItemEnchantmentsComponent.Builder buildEnchantmentEntry(ServerPlayerEntity player, RegistryKey<Enchantment> enchantment, Integer skillLevel) {
		DynamicRegistryManager drm = player.getServerWorld().getRegistryManager();
		Registry<Enchantment> reg = drm.getOrThrow(RegistryKeys.ENCHANTMENT);
		
		Enchantment enchantmentEntryValue = reg.get( enchantment );
		RegistryEntry<Enchantment> regEntry = reg.getEntry( enchantmentEntryValue );
		
		ItemEnchantmentsComponent.Builder IECBuilder = new ItemEnchantmentsComponent.Builder(
				ItemEnchantmentsComponent.DEFAULT.withShowInTooltip(true)
		);
		IECBuilder.set(regEntry, skillLevel);
		
		return IECBuilder;
	}
	
	public static boolean canReceiveEnchantment( ItemStack stack, RegistryEntry<Enchantment> enchantmentEntry ) {
		return stack.getItem().canBeEnchantedWith( stack, enchantmentEntry, null );
	}
	
	public static int getEnchantmentLevel( ItemStack stack, RegistryEntry<Enchantment> enchantmentEntry ) {
		return EnchantmentHelper.getEnchantments( stack ).getLevel( enchantmentEntry );
	}
	
	public static void applyEnchantment( ItemEnchantmentsComponent.Builder builder, ItemStack stack, RegistryEntry<Enchantment> enchantmentEntry, Integer skillLevel ) {
		ItemEnchantmentsComponent currentEnchantments = stack.getEnchantments();
		Integer currentLevel = getEnchantmentLevel( stack, enchantmentEntry );
		
		if( currentLevel < skillLevel ) {
			stack.applyComponentsFrom(
					ComponentMap.builder()
							.add(DataComponentTypes.ENCHANTMENTS, currentEnchantments)
							.add(DataComponentTypes.ENCHANTMENTS, builder.build())
							.build()
			);
		} // if
		
	}
	
}
