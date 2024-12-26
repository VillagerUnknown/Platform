package me.villagerunknown.platform.util;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

import java.util.Set;
import java.util.stream.Collectors;

public class PlayerStatUtil {
	
	public static int getStatFromStatType(ServerPlayerEntity player, StatType statistic, Object statTypeKey ) {
		return player.getStatHandler().getStat( statistic.getOrCreateStat( statTypeKey ) );
	}
	
	public static int getStatFromIdentifier( ServerPlayerEntity player, Identifier statistic ) {
		return player.getStatHandler().getStat( Stats.CUSTOM.getOrCreateStat( statistic ) );
	}
	
	public static int getTotalMinedBlockCount(ServerPlayerEntity player) {
		int totalMinedBlocks = 0;
		
		// Loop through all blocks in the block registry
		Set<Block> blockSet = Registries.BLOCK.stream().collect(Collectors.toSet());
		
		// Iterate through each block and sum the number of blocks mined by the player
		for (Block block : blockSet) {
			// Get the block's registry ID
			Identifier blockId = Registries.BLOCK.getId(block);
			
			// Retrieve the total number of mined blocks for the current block
			int minedBlockCount = player.getStatHandler().getStat(Stats.MINED.getOrCreateStat(block));
			
			// Add the mined count for this block to the total
			totalMinedBlocks += minedBlockCount;
		}
		
		return totalMinedBlocks;
	}
	
}
