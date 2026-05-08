package me.villagerunknown.platform.data.persistent;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.SavedDataStorage;

import java.util.Objects;

public abstract class AbstractPersistentData extends SavedData {
	
	public static SavedDataStorage getStateManager( MinecraftServer server ) {
		return Objects.requireNonNull(server.getLevel(Level.OVERWORLD)).getDataStorage();
	}
	
}