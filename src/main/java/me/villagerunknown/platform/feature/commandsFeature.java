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
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class commandsFeature {
	
	private static final Map<String, Runnable> COMMANDS = new HashMap<>();
	private static final String COMMAND_PREFIX = Platform.MOD_ID + "-";
	public static ServerCommandSource COMMAND_SOURCE;
	
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
	
	private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
		for( Map.Entry<String,Runnable> entry : COMMANDS.entrySet() ) {
			String command = entry.getKey();
			Runnable method = entry.getValue();
			
			registerCommand( command, method, dispatcher, registryAccess, registrationEnvironment );
		} // for
	}
	
	private static void registerCommand(String command, Runnable method, CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
		dispatcher.register( CommandManager.literal( COMMAND_PREFIX + command ).executes( context -> {
			COMMAND_SOURCE = context.getSource();
			
			if( Platform.CONFIG.enableCommands ) {
				try {
					method.run();
				} catch( Exception e ) {
					sendCommandFeedback( command + " command encountered an error!", SoundEvents.ENTITY_VILLAGER_NO );
				} // try, catch
			} else {
				sendCommandFeedback( Platform.MOD.getName() + " commands are currently disabled in the config!", SoundEvents.ENTITY_VILLAGER_NO );
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
			COMMAND_SOURCE.sendMessage(Text.of(MessageUtil.formComment(message)));
		} // if
	}
	
	public static void playSound( SoundEvent sound ) {
		if( Platform.CONFIG.enableCommandFeedbackSounds && null != COMMAND_SOURCE ) {
			ServerPlayerEntity player = COMMAND_SOURCE.getPlayer();
			if( null != player ) {
				EntityUtil.playSound(player, sound, SoundCategory.NEUTRAL, 1, 1, true);
			} // if
		} // if
	}
	
}
