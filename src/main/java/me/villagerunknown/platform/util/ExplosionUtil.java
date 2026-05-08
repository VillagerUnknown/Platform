package me.villagerunknown.platform.util;

import java.util.List;
import java.util.Objects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ExplosionUtil {
	
	public static void causeExplosion(Level world, Entity entity, float power, boolean createFire, boolean breakBlocks ) {
		int particleCount = (int) Math.ceil(power);
		
		ServerLevel serverWorld = Objects.requireNonNull(world.getServer()).getLevel( world.dimension() );
		if( null != serverWorld ) {
			serverWorld.explode(entity, entity.getX(), entity.getY(), entity.getZ(), (float)4 * power, createFire, Level.ExplosionInteraction.TRIGGER);
			serverWorld.sendParticles(ParticleTypes.EXPLOSION_EMITTER, entity.getX(), entity.getY(), entity.getZ(), particleCount, 0.1, 0.1, 0.1, 1);
			
			if( breakBlocks ) {
				PositionUtil.breakNearbyBlocks( world, entity.blockPosition(), 1, true );
			} // if
			
		} // if
	}
	
}
