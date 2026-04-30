package me.villagerunknown.platform.cmd;

import me.villagerunknown.platform.feature.commandsFeature;
import me.villagerunknown.platform.util.MessageUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldProperties;

public class worldspawnCommand {
	
	public static void execute() {
		MinecraftServer server = commandsFeature.COMMAND_SOURCE.getServer();
		WorldProperties.SpawnPoint spawnPoint = server.getOverworld().getSpawnPoint();
		BlockPos spawnPos = spawnPoint.getPos();
		GameRules.IntRule spawnRadius = server.getGameRules().get(GameRules.SPAWN_RADIUS );
		
		String message = MessageUtil.formComment( "World Spawn is at " + spawnPos.getX() + " " + spawnPos.getY() + " " + spawnPos.getZ() + " with a spawn radius of " + spawnRadius.get() + " blocks." );
		commandsFeature.COMMAND_SOURCE.sendMessage( Text.of( message ) );
		commandsFeature.playSound(SoundEvents.ENTITY_VILLAGER_YES);
	}
	
}
