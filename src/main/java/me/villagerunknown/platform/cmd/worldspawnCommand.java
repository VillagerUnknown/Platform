package me.villagerunknown.platform.cmd;

import me.villagerunknown.platform.feature.commandsFeature;
import me.villagerunknown.platform.util.MessageUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.rule.GameRules;

public class worldspawnCommand {
	
	public static void execute() {
		MinecraftServer server = commandsFeature.COMMAND_SOURCE.getServer();
		
		// Get Spawn Point
		WorldProperties.SpawnPoint spawnPoint = server.getSpawnWorld().getSpawnPoint();
		BlockPos spawnPos = spawnPoint.getPos();
		
		// Get Respawn Radius
		GameRules rules = server.getSpawnWorld().getGameRules();
		Integer spawnRadius = rules.getValue(GameRules.RESPAWN_RADIUS);
		
		
		String message = MessageUtil.formComment( "World Spawn is at " + spawnPos.getX() + " " + spawnPos.getY() + " " + spawnPos.getZ() + " with a spawn radius of " + spawnRadius + " blocks." );
		commandsFeature.COMMAND_SOURCE.sendMessage( Text.of( message ) );
		commandsFeature.playSound(SoundEvents.ENTITY_VILLAGER_YES);
	}
	
}
