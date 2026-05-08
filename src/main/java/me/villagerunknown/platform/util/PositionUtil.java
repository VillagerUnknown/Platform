package me.villagerunknown.platform.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.AABB;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PositionUtil {
	
	private static final Random rand = new Random();
	
	public static BlockPos findSafeSpawnPosition(Level world, BlockPos startPos, int range) {
		List<BlockPos> safePositions = new ArrayList<>();
		
		if( isSafeSpawnLocation( world, startPos ) && hasSafeBlockBelow( world, startPos ) ) {
			return startPos;
		} // if
		
		for (int dx = -range; dx <= range; dx++) {
			for (int dy = -range; dy <= range; dy++) {
				for (int dz = -range; dz <= range; dz++) {
					BlockPos checkPos = startPos.offset(dx, dy, dz);
					if (isSafeSpawnLocation(world, checkPos)) {
						BlockPos safeBlockPos = findSafeBlockBelow( world, checkPos, world.getMinY() );
						if( !safePositions.contains( safeBlockPos ) ) {
							safePositions.add(safeBlockPos);
						} // if
					} // if
				} // for
			} // for
		} // for
		
		if( !safePositions.isEmpty() ) {
			return safePositions.get( rand.nextInt( safePositions.size() ) );
		} // if
		
		return startPos;
	}
	
	private static boolean isSafeSpawnLocation(Level world, BlockPos pos) {
		return isClearSpace(world, pos) && !isDangerousPosition(world, pos);
	}
	
	private static boolean isClearSpace(Level world, BlockPos pos) {
		ChunkAccess chunk = world.getChunk( pos );
		BlockGetter blockView = world.getChunkForCollisions( chunk.getPos().x(), chunk.getPos().z() );
		
		return !world.getBlockState( pos ).isSuffocating( blockView, pos )
				&& !world.getBlockState( pos.above() ).isSuffocating( blockView, pos.above() );
	}
	
	private static boolean isDangerousPosition(Level world, BlockPos pos) {
		return world.getBlockState(pos).is(Blocks.LAVA) || world.getBlockState(pos).is(Blocks.FIRE);
	}
	
	public static BlockPos findSolidBlockBelow(Level world, BlockPos startPos, int bottomYLimit) {
		BlockPos currentPos = startPos.below();
		
		for(int y = startPos.getY(); y > bottomYLimit; y--) {
			if( hasSolidBlockBelow( world, currentPos ) ) {
				return currentPos.above();
			}
			
			currentPos = currentPos.below();
		}
		
		return startPos;
	}
	
	public static boolean hasSolidBlockBelow(Level world, BlockPos pos) {
		return ( world.getBlockState( pos.below() ).isRedstoneConductor( world, pos.below() ) );
	}
	
	public static BlockPos findSafeBlockBelow(Level world, BlockPos startPos, int bottomYLimit) {
		BlockPos currentPos = startPos.below();
		
		for(int y = startPos.getY(); y > bottomYLimit; y--) {
			
			if( hasSafeBlockBelow( world, currentPos ) ) {
				return currentPos.above();
			}
			
			currentPos = currentPos.below();
		}
		
		return startPos;
	}
	
	public static boolean hasSafeBlockBelow(Level world, BlockPos pos) {
		return ( ( hasSolidBlockBelow( world, pos ) || !world.getBlockState( pos.below() ).isAir() ) && world.getBlockState( pos.below() ).getBlock() != Blocks.LAVA );
	}
	
	public static BlockPos findNearestBlock(Entity entity, Block block, int radius) {
		BlockPos entityPos = entity.blockPosition();
		MinecraftServer server = entity.level().getServer();
		
		if( null == server ) {
			return entity.blockPosition();
		} // if
		
		ServerLevel world = server.getLevel(entity.level().dimension());
		
		if( null == world ) {
			return entity.blockPosition();
		} // if
		
		BlockPos closestBlockPos = null;
		double closestDistance = Double.MAX_VALUE;
		
		for (int x = entityPos.getX() - radius; x <= entityPos.getX() + radius; x++) {
			for (int z = entityPos.getZ() - radius; z <= entityPos.getZ() + radius; z++) {
				for (int y = entityPos.getY() - 2; y <= entityPos.getY() + 2; y++) {
					BlockPos pos = new BlockPos(x, y, z);
					if ( world.getBlockState(pos).getBlock() == block ) {
						double distance = entityPos.distSqr(pos);
						if (distance < closestDistance) {
							closestDistance = distance;
							closestBlockPos = pos;
						}
					}
				}
			}
		}
		return closestBlockPos;
	}
	
	public static BlockPos findNearestBed(Entity entity, int radius) {
		BlockPos entityPos = entity.blockPosition();
		MinecraftServer server = entity.level().getServer();
		
		if( null == server ) {
			return entity.blockPosition();
		} // if
		
		ServerLevel world = server.getLevel( entity.level().dimension() );
		
		if( null == world ) {
			return entity.blockPosition();
		} // if
		
		BlockPos closestBedPos = null;
		double closestDistance = Double.MAX_VALUE;
		
		for (int x = entityPos.getX() - radius; x <= entityPos.getX() + radius; x++) {
			for (int z = entityPos.getZ() - radius; z <= entityPos.getZ() + radius; z++) {
				for (int y = entityPos.getY() - 2; y <= entityPos.getY() + 2; y++) {
					BlockPos pos = new BlockPos(x, y, z);
					if ( ListUtil.BEDS.contains( world.getBlockState(pos).getBlock() ) ) {
						double distance = entityPos.distSqr(pos);
						if (distance < closestDistance) {
							closestDistance = distance;
							closestBedPos = pos;
						}
					}
				}
			}
		}
		return closestBedPos;
	}
	
	public static List<Block> getNearbyBlocks(Level world, BlockPos pos, int proximity) {
		AABB nearbySearchArea = BoxUtil.createBox( pos, proximity );
		
		List<Block> nearbyBlocks = new ArrayList<>();
		
		for (int x = (int) nearbySearchArea.minX; x <= nearbySearchArea.maxX; x++) {
			for (int y = (int) nearbySearchArea.minY; y <= nearbySearchArea.maxY; y++) {
				for (int z = (int) nearbySearchArea.minZ; z <= nearbySearchArea.maxZ; z++) {
					BlockPos blockPos = new BlockPos(x, y, z);
					
					BlockState state = world.getBlockState(blockPos);
					
					nearbyBlocks.add(state.getBlock());
				} // for
			} // for
		} // for
		
		return nearbyBlocks;
	}
	
	public static void breakNearbyBlocks(Level world, BlockPos pos, int proximity, boolean dropBlock) {
		AABB nearbySearchArea = BoxUtil.createBox( pos, proximity );
		
		for (int x = (int) nearbySearchArea.minX; x <= nearbySearchArea.maxX; x++) {
			for (int y = (int) nearbySearchArea.minY; y <= nearbySearchArea.maxY; y++) {
				for (int z = (int) nearbySearchArea.minZ; z <= nearbySearchArea.maxZ; z++) {
					BlockPos blockPos = new BlockPos(x, y, z);
					
					world.destroyBlock( blockPos, dropBlock );
				} // for
			} // for
		} // for
	}
	
	public static boolean isNearFlameSource( List<Block> nearbyBlocks ) {
		for (Block nearbyBlock : nearbyBlocks) {
			if( ListUtil.FLAME_SOURCE_BLOCKS.contains( nearbyBlock ) ) {
				return true;
			} // if
		} // for
		
		return false;
	}
	
}
