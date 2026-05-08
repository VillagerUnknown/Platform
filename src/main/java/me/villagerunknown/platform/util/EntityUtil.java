package me.villagerunknown.platform.util;

import me.villagerunknown.platform.Platform;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;

public class EntityUtil {
	
	public static String formActionMessage(Entity entity, Entity damageSourceEntity, String action ) {
		String message;
		String giverName;
		String receiverName;
		
		if( damageSourceEntity instanceof Player) {
			// Damage is from a player
			giverName = damageSourceEntity.getScoreboardName();
		} else {
			// Damage is from something else
			giverName = LanguageUtil.translateToString(damageSourceEntity.getType().getDescriptionId());
		} // if, else
		
		if( entity.hasCustomName() ) {
			receiverName = entity.getCustomName().getString();
		} else {
			receiverName = LanguageUtil.translateToString(entity.getType().getDescriptionId());
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
	
	public static void reportAttackToChat( ServerPlayer entity, Entity damageSourceEntity ) {
		MinecraftServer server = entity.level().getServer();
		if( null != server) {
			MessageUtil.broadcastChatMessage(server, formActionMessage(entity, damageSourceEntity, "attacked"));
		} // if
	}
	
	public static void reportKillToLog( Logger logger, Entity entity, Entity damageSourceEntity ) {
		logger.info( formActionMessage( entity, damageSourceEntity, "killed" ) );
	}
	
	public static void reportKillToChat( ServerPlayer entity, Entity damageSourceEntity ) {
		MinecraftServer server = entity.level().getServer();
		if( null != server) {
			MessageUtil.broadcastChatMessage(server, formActionMessage(entity, damageSourceEntity, "killed"));
		} // if
	}
	
	public static void reportConversionToLog( Logger logger, Entity entity, Entity damageSourceEntity ) {
		logger.info( formActionMessage( entity, damageSourceEntity, "converted" ) );
	}
	
	public static void reportConversionToChat( ServerPlayer entity, Entity damageSourceEntity ) {
		MinecraftServer server = entity.level().getServer();
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
	
	public static void teleport(Entity entity, Vec3 newPos) {
		entity.snapTo(newPos.x, newPos.y, newPos.z, entity.getYRot(), entity.getXRot());
	}
	
	public static List<Block> getNearbyBlocks(Entity entity, int proximity) {
		return PositionUtil.getNearbyBlocks( entity.level(), entity.blockPosition(), proximity );
	}
	
	public static void causeExplosion(Level world, Entity entity, float power, boolean createFire, boolean breakBlocks ) {
		ExplosionUtil.causeExplosion(world, entity, power, createFire, breakBlocks );
	}
	
	public static void playSound(Entity entity, SoundEvent sound, SoundSource category, float volume, float pitch, boolean toPlayer) {
		Level world = entity.level();
		
		if( world.isClientSide() ) {
			return;
		} // if
		
		if( entity.isAlwaysTicking() ) {
			if( toPlayer ) {
				world.playSound( entity, entity.blockPosition(), sound, category, volume, pitch );
			} else {
				world.playSound( null, entity.blockPosition(), sound, category, volume, pitch );
			} // if, else
		} else {
			world.playSound( null, entity.blockPosition(), sound, category, volume, pitch );
		} // if, else
	}
	
	public static void spawnParticles(Entity entity, float heightAdjust, ParticleOptions particle, int count, double deltaX, double deltaY, double deltaZ, double speed) {
		ServerLevel serverWorld = Objects.requireNonNull(entity.level().getServer()).getLevel( entity.level().dimension() );
		if( null != serverWorld ) {
			serverWorld.sendParticles( particle, entity.getX(), entity.getY() + heightAdjust, entity.getZ(), count, deltaX, deltaY, deltaZ, speed );
		} // if
	}
	
	public static void addStatusEffect(LivingEntity entity, Holder statusEffect, Integer duration, Integer level, Boolean ambient, Boolean particles, Boolean icon) {
		MobEffectInstance effectInstance = new MobEffectInstance(
				statusEffect,
				duration,
				level,
				ambient,
				particles,
				icon
		);
		entity.addEffect( effectInstance );
	}
	
	public static void removeStatusEffect(LivingEntity entity, Holder statusEffect) {
		entity.removeEffect( statusEffect );
	}
	
	public static void simulateDeath(LivingEntity entity) {
		// Make the villager invisible (dead)
		EntityUtil.addStatusEffect(entity, MobEffects.INVISIBILITY, 7, 1, false, false, false);
		
		// Add particles to the world simulating death
		EntityUtil.spawnParticles( entity, 1, ParticleTypes.CLOUD.getType(), 20, 0.1, 0.1, 0.1, 0.005 );
	}
	
	public static void simulateTotemDeath(LivingEntity entity) {
		// Play totem sound
		EntityUtil.playSound( entity, SoundEvents.TOTEM_USE, SoundSource.NEUTRAL, 1.0F, 1.0F, false );
		
		// Add particles to the world simulating death
		EntityUtil.spawnParticles( entity, 1, ParticleTypes.TOTEM_OF_UNDYING, 20, 0.5, 1, 0.5, 0.5 );
	}
	
}
