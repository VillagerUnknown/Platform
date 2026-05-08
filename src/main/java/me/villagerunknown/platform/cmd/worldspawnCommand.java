package me.villagerunknown.platform.cmd;

import me.villagerunknown.platform.feature.commandsFeature;
import me.villagerunknown.platform.util.MessageUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.LevelData;

public class worldspawnCommand {
	
	public static void execute() {
		MinecraftServer server = commandsFeature.COMMAND_SOURCE.getServer();
		
		// Get Spawn Point
		LevelData.RespawnData spawnPoint = server.findRespawnDimension().getRespawnData();
		BlockPos spawnPos = spawnPoint.pos();
		
		// Get Respawn Radius
		GameRules rules = server.findRespawnDimension().getGameRules();
		Integer spawnRadius = rules.get(GameRules.RESPAWN_RADIUS);
		
		
		String message = MessageUtil.formComment( "World Spawn is at " + spawnPos.getX() + " " + spawnPos.getY() + " " + spawnPos.getZ() + " with a spawn radius of " + spawnRadius + " blocks." );
		commandsFeature.COMMAND_SOURCE.sendSystemMessage( Component.nullToEmpty( message ) );
		commandsFeature.playSound(SoundEvents.VILLAGER_YES);
	}
	
}
