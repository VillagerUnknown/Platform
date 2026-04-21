package me.villagerunknown.platform.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.List;
import java.util.Objects;

public class ExplosionUtil {
	
	public static void causeExplosion(World world, Entity entity, float power, boolean createFire, boolean breakBlocks ) {
		int particleCount = (int) Math.ceil(power);
		
		ServerWorld serverWorld = Objects.requireNonNull(world.getServer()).getWorld( world.getRegistryKey() );
		if( null != serverWorld ) {
			serverWorld.createExplosion(entity, entity.getX(), entity.getY(), entity.getZ(), (float)4 * power, createFire, World.ExplosionSourceType.TRIGGER);
			serverWorld.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, entity.getX(), entity.getY(), entity.getZ(), particleCount, 0.1, 0.1, 0.1, 1);
			
			if( breakBlocks ) {
				PositionUtil.breakNearbyBlocks( world, entity.getBlockPos(), 1, true );
			} // if
			
		} // if
	}
	
}
