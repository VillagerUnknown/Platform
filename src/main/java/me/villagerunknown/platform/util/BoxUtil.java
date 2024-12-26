package me.villagerunknown.platform.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class BoxUtil {
	
	public static Box createBox( BlockPos pos, int radius ) {
		return new Box(
				new Vec3d( pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius ),
				new Vec3d( pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius )
		);
	}
	
}
