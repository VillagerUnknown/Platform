package me.villagerunknown.platform.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BoxUtil {
	
	public static AABB createBox( BlockPos pos, int radius ) {
		return new AABB(
				new Vec3( pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius ),
				new Vec3( pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius )
		);
	}
	
}
