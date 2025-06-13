package me.villagerunknown.platform.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PositionUtil {
	
	private static final Random rand = new Random();
	
	public static BlockPos findSafeSpawnPosition(World world, BlockPos startPos, int range) {
		List<BlockPos> safePositions = new ArrayList<>();
		
		if( isSafeSpawnLocation( world, startPos ) && hasSafeBlockBelow( world, startPos ) ) {
			return startPos;
		} // if
		
		for (int dx = -range; dx <= range; dx++) {
			for (int dy = -range; dy <= range; dy++) {
				for (int dz = -range; dz <= range; dz++) {
					BlockPos checkPos = startPos.add(dx, dy, dz);
					if (isSafeSpawnLocation(world, checkPos)) {
						BlockPos safeBlockPos = findSafeBlockBelow( world, checkPos, world.getBottomY() );
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
	
	private static boolean isSafeSpawnLocation(World world, BlockPos pos) {
		return isClearSpace(world, pos) && !isDangerousPosition(world, pos);
	}
	
	private static boolean isClearSpace(World world, BlockPos pos) {
		Chunk chunk = world.getChunk( pos );
		BlockView blockView = world.getChunkAsView( chunk.getPos().x, chunk.getPos().z );
		
		return !world.getBlockState( pos ).shouldSuffocate( blockView, pos )
				&& !world.getBlockState( pos.up() ).shouldSuffocate( blockView, pos.up() );
	}
	
	private static boolean isDangerousPosition(World world, BlockPos pos) {
		return world.getBlockState(pos).isOf(Blocks.LAVA) || world.getBlockState(pos).isOf(Blocks.FIRE);
	}
	
	public static BlockPos findSolidBlockBelow(World world, BlockPos startPos, int bottomYLimit) {
		BlockPos currentPos = startPos.down();
		
		for(int y = startPos.getY(); y > bottomYLimit; y--) {
			if( hasSolidBlockBelow( world, currentPos ) ) {
				return currentPos.up();
			}
			
			currentPos = currentPos.down();
		}
		
		return startPos;
	}
	
	public static boolean hasSolidBlockBelow(World world, BlockPos pos) {
		return ( world.getBlockState( pos.down() ).isSolidBlock( world, pos.down() ) );
	}
	
	public static BlockPos findSafeBlockBelow(World world, BlockPos startPos, int bottomYLimit) {
		BlockPos currentPos = startPos.down();
		
		for(int y = startPos.getY(); y > bottomYLimit; y--) {
			
			if( hasSafeBlockBelow( world, currentPos ) ) {
				return currentPos.up();
			}
			
			currentPos = currentPos.down();
		}
		
		return startPos;
	}
	
	public static boolean hasSafeBlockBelow(World world, BlockPos pos) {
		return ( ( hasSolidBlockBelow( world, pos ) || !world.getBlockState( pos.down() ).isAir() ) && world.getBlockState( pos.down() ).getBlock() != Blocks.LAVA );
	}
	
	public static BlockPos findNearestBlock(Entity entity, Block block, int radius) {
		BlockPos entityPos = entity.getBlockPos();
		MinecraftServer server = entity.getServer();
		
		if( null == server ) {
			return entity.getBlockPos();
		} // if
		
		ServerWorld world = server.getWorld(entity.getWorld().getRegistryKey());
		
		if( null == world ) {
			return entity.getBlockPos();
		} // if
		
		BlockPos closestBlockPos = null;
		double closestDistance = Double.MAX_VALUE;
		
		for (int x = entityPos.getX() - radius; x <= entityPos.getX() + radius; x++) {
			for (int z = entityPos.getZ() - radius; z <= entityPos.getZ() + radius; z++) {
				for (int y = entityPos.getY() - 2; y <= entityPos.getY() + 2; y++) {
					BlockPos pos = new BlockPos(x, y, z);
					if ( world.getBlockState(pos).getBlock() == block ) {
						double distance = entityPos.getSquaredDistance(pos);
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
		BlockPos entityPos = entity.getBlockPos();
		MinecraftServer server = entity.getServer();
		
		if( null == server ) {
			return entity.getBlockPos();
		} // if
		
		ServerWorld world = server.getWorld( entity.getWorld().getRegistryKey() );
		
		if( null == world ) {
			return entity.getBlockPos();
		} // if
		
		BlockPos closestBedPos = null;
		double closestDistance = Double.MAX_VALUE;
		
		for (int x = entityPos.getX() - radius; x <= entityPos.getX() + radius; x++) {
			for (int z = entityPos.getZ() - radius; z <= entityPos.getZ() + radius; z++) {
				for (int y = entityPos.getY() - 2; y <= entityPos.getY() + 2; y++) {
					BlockPos pos = new BlockPos(x, y, z);
					if ( ListUtil.BEDS.contains( world.getBlockState(pos).getBlock() ) ) {
						double distance = entityPos.getSquaredDistance(pos);
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
	
	public static List<Block> getNearbyBlocks(World world, BlockPos pos, int proximity) {
		Box nearbySearchArea = BoxUtil.createBox( pos, proximity );
		
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
	
	public static boolean isNearFlameSource( List<Block> nearbyBlocks ) {
		for (Block nearbyBlock : nearbyBlocks) {
			if( ListUtil.FLAME_SOURCE_BLOCKS.contains( nearbyBlock ) ) {
				return true;
			} // if
		} // for
		
		return false;
	}
	
}
