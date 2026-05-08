package me.villagerunknown.platform.util;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class EnchantmentUtil {
	
	public static ItemEnchantments.Mutable buildEnchantmentEntry(ServerPlayer player, ResourceKey<Enchantment> enchantment, Integer skillLevel) {
		RegistryAccess drm = player.registryAccess();
		Registry<Enchantment> reg = drm.lookupOrThrow(Registries.ENCHANTMENT);
		
		Enchantment enchantmentEntryValue = reg.getValue( enchantment );
		Holder<Enchantment> regEntry = reg.wrapAsHolder( enchantmentEntryValue );
		
		ItemEnchantments.Mutable IECBuilder = new ItemEnchantments.Mutable(
				ItemEnchantments.EMPTY
		);
		IECBuilder.set(regEntry, skillLevel);
		
		return IECBuilder;
	}
	
	public static boolean canReceiveEnchantment( ItemStack stack, Holder<Enchantment> enchantmentEntry ) {
		return stack.getItem().canBeEnchantedWith( stack, enchantmentEntry, null );
	}
	
	public static int getEnchantmentLevel( ItemStack stack, Holder<Enchantment> enchantmentEntry ) {
		return EnchantmentHelper.getEnchantmentsForCrafting( stack ).getLevel( enchantmentEntry );
	}
	
	public static void applyEnchantment( ItemEnchantments.Mutable builder, ItemStack stack, Holder<Enchantment> enchantmentEntry, Integer skillLevel ) {
		ItemEnchantments currentEnchantments = stack.getEnchantments();
		Integer currentLevel = getEnchantmentLevel( stack, enchantmentEntry );
		
		if( currentLevel < skillLevel ) {
			stack.applyComponents(
					DataComponentMap.builder()
							.set(DataComponents.ENCHANTMENTS, currentEnchantments)
							.set(DataComponents.ENCHANTMENTS, builder.toImmutable())
							.build()
			);
		} // if
		
	}
	
}
