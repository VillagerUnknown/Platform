package me.villagerunknown.platform.list;

import me.villagerunknown.platform.util.MathUtil;
import net.minecraft.item.Item;

import java.util.ArrayList;

public class ItemsList {
	
	protected ArrayList<Item> items = new ArrayList<>();
	
	public void addItem(Item item) {
		items.add( item );
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public Item[] getItemsArray() {
		return items.toArray( new Item[0] );
	}
	
	public Item getRandomItem() {
		return items.get((int) MathUtil.getRandomWithinRange( 0, items.size() ));
	}
	
}
