package me.villagerunknown.platform.util;

import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.Block;

public class PlayerStatUtil {
	
	public static int getStatFromStatType(ServerPlayer player, StatType statistic, Object statTypeKey ) {
		return player.getStats().getValue( statistic.get( statTypeKey ) );
	}
	
	public static int getStatFromIdentifier( ServerPlayer player, Identifier statistic ) {
		return player.getStats().getValue( Stats.CUSTOM.get( statistic ) );
	}
	
	public static int getTotalMinedBlockCount(ServerPlayer player) {
		int totalMinedBlocks = 0;
		
		// Loop through all blocks in the block registry
		Set<Block> blockSet = BuiltInRegistries.BLOCK.stream().collect(Collectors.toSet());
		
		// Iterate through each block and sum the number of blocks mined by the player
		for (Block block : blockSet) {
			// Get the block's registry ID
			Identifier blockId = BuiltInRegistries.BLOCK.getKey(block);
			
			// Retrieve the total number of mined blocks for the current block
			int minedBlockCount = player.getStats().getValue(Stats.BLOCK_MINED.get(block));
			
			// Add the mined count for this block to the total
			totalMinedBlocks += minedBlockCount;
		}
		
		return totalMinedBlocks;
	}
	
}
