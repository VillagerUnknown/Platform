package me.villagerunknown.platform.util;

import me.villagerunknown.platform.Platform;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;

public class EntityUtil {
	
	public static String formActionMessage(Entity entity, Entity damageSourceEntity, String action ) {
		String message;
		String giverName;
		String receiverName;
		
		if( damageSourceEntity instanceof PlayerEntity) {
			// Damage is from a player
			giverName = damageSourceEntity.getNameForScoreboard();
		} else {
			// Damage is from something else
			giverName = LanguageUtil.translateToString(damageSourceEntity.getType().getTranslationKey());
		} // if, else
		
		if( entity.hasCustomName() ) {
			receiverName = entity.getCustomName().getString();
		} else {
			receiverName = LanguageUtil.translateToString(entity.getType().getTranslationKey());
		} // if, else
		
		if( null == receiverName ) {
			message = giverName + " " + action;
		} else {
			message = giverName + " " + action + " " + receiverName ;
		} // if, else
		
		return message +  " [" + entity.getX() + " " + entity.getY() + " " + entity.getZ() + "]";
	}
	
	public static void reportAttackToLog(Logger logger, Entity entity, Entity damageSourceEntity ) {
		logger.info( formActionMessage( entity, damageSourceEntity, "attacked" ) );
	}
	
	public static void reportAttackToChat( ServerPlayerEntity entity, Entity damageSourceEntity ) {
		MinecraftServer server = entity.getServer();
		if( null != server) {
			MessageUtil.broadcastChatMessage(server, formActionMessage(entity, damageSourceEntity, "attacked"));
		} // if
	}
	
	public static void reportKillToLog( Logger logger, Entity entity, Entity damageSourceEntity ) {
		logger.info( formActionMessage( entity, damageSourceEntity, "killed" ) );
	}
	
	public static void reportKillToChat( ServerPlayerEntity entity, Entity damageSourceEntity ) {
		MinecraftServer server = entity.getServer();
		if( null != server) {
			MessageUtil.broadcastChatMessage(server, formActionMessage(entity, damageSourceEntity, "killed"));
		} // if
	}
	
	public static void reportConversionToLog( Logger logger, Entity entity, Entity damageSourceEntity ) {
		logger.info( formActionMessage( entity, damageSourceEntity, "converted" ) );
	}
	
	public static void reportConversionToChat( ServerPlayerEntity entity, Entity damageSourceEntity ) {
		MinecraftServer server = entity.getServer();
		if( null != server) {
			MessageUtil.broadcastChatMessage(server, formActionMessage(entity, damageSourceEntity, "converted"));
		} // if
	}
	
	public static void setHealthToMax(LivingEntity entity) {
		entity.setHealth(entity.getMaxHealth());
	}
	
	public static boolean hasCustomName(Entity entity) {
		return entity.getCustomName() != null && !entity.getCustomName().getString().isEmpty();
	}
	
	public static void teleport(Entity entity, Vec3d newPos) {
		entity.refreshPositionAndAngles(newPos.x, newPos.y, newPos.z, entity.getYaw(), entity.getPitch());
	}
	
	public static List<Block> getNearbyBlocks(Entity entity, int proximity) {
		return PositionUtil.getNearbyBlocks( entity.getEntityWorld(), entity.getBlockPos(), proximity );
	}
	
	public static void causeExplosion(World world, Entity entity, float power, boolean createFire, boolean breakBlocks ) {
		ExplosionUtil.causeExplosion(world, entity, power, createFire, breakBlocks );
	}
	
	public static void playSound(Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean toPlayer) {
		if( entity.isPlayer() ) {
			ServerPlayerEntity player = (ServerPlayerEntity) entity;
			
			if( toPlayer ) {
				player.playSoundToPlayer( sound, category, volume, pitch );
			} else {
				player.playSoundToPlayer( sound, category, volume, pitch ); // @todo Resolve sound issue requiring playSoundToPlayer here
				player.playSound( sound, volume, pitch );
			} // if, else
		} else {
			entity.playSound(sound, volume, pitch);
		} // if, else
	}
	
	public static void spawnParticles(Entity entity, float heightAdjust, ParticleEffect particle, int count, double deltaX, double deltaY, double deltaZ, double speed) {
		ServerWorld serverWorld = Objects.requireNonNull(entity.getServer()).getWorld( entity.getWorld().getRegistryKey() );
		if( null != serverWorld ) {
			serverWorld.spawnParticles( particle, entity.getX(), entity.getY() + heightAdjust, entity.getZ(), count, deltaX, deltaY, deltaZ, speed );
		} // if
	}
	
	public static void addStatusEffect(LivingEntity entity, RegistryEntry statusEffect, Integer duration, Integer level, Boolean ambient, Boolean particles, Boolean icon) {
		StatusEffectInstance effectInstance = new StatusEffectInstance(
				statusEffect,
				duration,
				level,
				ambient,
				particles,
				icon
		);
		entity.addStatusEffect( effectInstance );
	}
	
	public static void removeStatusEffect(LivingEntity entity, RegistryEntry statusEffect) {
		entity.removeStatusEffect( statusEffect );
	}
	
}
