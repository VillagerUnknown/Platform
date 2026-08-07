package me.villagerunknown.platform.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

public class TagUtil {
	
	public static <T> TagKey<?> createTagKey(ResourceKey<? extends Registry<T>> registry, Identifier id) {
		return TagKey.create(registry, id);
	}
	
}
