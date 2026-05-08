package me.villagerunknown.platform.feature;

import com.mojang.brigadier.CommandDispatcher;
import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.cmd.flushcachesCommand;
import me.villagerunknown.platform.cmd.helpCommand;
import me.villagerunknown.platform.cmd.reloadCommand;
import me.villagerunknown.platform.cmd.worldspawnCommand;
import me.villagerunknown.platform.util.EntityUtil;
import me.villagerunknown.platform.util.MessageUtil;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import java.util.HashMap;
import java.util.Map;

public class commandsFeature {
	
	private static final Map<String, Runnable> COMMANDS = new HashMap<>();
	private static final String COMMAND_PREFIX = Platform.MOD_ID + "-";
	public static CommandSourceStack COMMAND_SOURCE;
	
	public static void execute() {
		addCommand("help", helpCommand::execute );
		addCommand("reload", reloadCommand::execute);
		addCommand("worldspawn", worldspawnCommand::execute );
		addCommand("flushcaches", flushcachesCommand::execute );
		
		CommandRegistrationCallback.EVENT.register(commandsFeature::registerCommands);
	}
	
	public static void addCommand(String command, Runnable executable) {
		COMMANDS.put( command, executable );
		
		Platform.LOGGER.info("Command added: {}", COMMAND_PREFIX + command);
	}
	
	private static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection registrationEnvironment) {
		for( Map.Entry<String,Runnable> entry : COMMANDS.entrySet() ) {
			String command = entry.getKey();
			Runnable method = entry.getValue();
			
			registerCommand( command, method, dispatcher, registryAccess, registrationEnvironment );
		} // for
	}
	
	private static void registerCommand(String command, Runnable method, CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection registrationEnvironment) {
		dispatcher.register( Commands.literal( COMMAND_PREFIX + command ).executes( context -> {
			COMMAND_SOURCE = context.getSource();
			
			if( Platform.CONFIG.enableCommands ) {
				try {
					method.run();
				} catch( Exception e ) {
					sendCommandFeedback( command + " command encountered an error!", SoundEvents.VILLAGER_NO );
				} // try, catch
			} else {
				sendCommandFeedback( Platform.MOD.getName() + " commands are currently disabled in the config!", SoundEvents.VILLAGER_NO );
				Platform.LOGGER.info("Tried to execute the `{}` command but {} commands are currently disabled in the config!", COMMAND_PREFIX + command, Platform.MOD.getName());
			} // if, else
			
			return 1;
		} ) );
		
		Platform.LOGGER.info("Command registered: {}", COMMAND_PREFIX + command);
	}
	
	public static void sendCommandFeedback( String message, SoundEvent sound ) {
		// Send a Chat Message
		sendMessage( message );
		
		// Play a Sound
		playSound( sound );
	}
	
	public static void sendMessage( String message ) {
		if( null != COMMAND_SOURCE ) {
			COMMAND_SOURCE.sendSystemMessage(Component.nullToEmpty(MessageUtil.formComment(message)));
		} // if
	}
	
	public static void playSound( SoundEvent sound ) {
		if( Platform.CONFIG.enableCommandFeedbackSounds && null != COMMAND_SOURCE ) {
			ServerPlayer player = COMMAND_SOURCE.getPlayer();
			if( null != player ) {
				EntityUtil.playSound(player, sound, SoundSource.NEUTRAL, 1, 1, true);
			} // if
		} // if
	}
	
}
