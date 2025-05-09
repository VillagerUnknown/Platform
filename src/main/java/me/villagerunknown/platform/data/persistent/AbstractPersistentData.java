package me.villagerunknown.platform.data.persistent;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.Objects;

public abstract class AbstractPersistentData extends PersistentState {
	
	public static PersistentStateManager getStateManager( MinecraftServer server ) {
		return Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getPersistentStateManager();
	}
	
}