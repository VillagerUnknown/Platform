package me.villagerunknown.platform.data.persistent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.villagerunknown.platform.feature.playerCacheFeature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Uuids;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static me.villagerunknown.platform.Platform.MOD_ID;

public class PersistentData extends PersistentState {
	
	public static PersistentStateManager getStateManager( MinecraftServer server ) {
		return Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getPersistentStateManager();
	}
	
}
