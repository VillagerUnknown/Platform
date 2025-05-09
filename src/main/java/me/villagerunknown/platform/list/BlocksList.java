package me.villagerunknown.platform.list;

import me.villagerunknown.platform.util.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;

public class BlocksList {
	
	protected ArrayList<Block> blocks = new ArrayList<>();
	
	public void addBlock(Block block) {
		blocks.add( block );
	}
	
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	
	public Block[] getBlocksArray() {
		return blocks.toArray( new Block[0] );
	}
	
	public Block getRandomBlock() {
		return blocks.get((int) MathUtil.getRandomWithinRange( 0, blocks.size() ));
	}
	
}
