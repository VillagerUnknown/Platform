package me.villagerunknown.platform.util;

import java.util.Random;

public class MathUtil {
	
	private static final Random rand = new Random();
	
	public static float getRandomWithinRange( float min, float max ) {
		return min + (float) (Math.random() * (max - min));
	}
	
	public static boolean hasChance(float probability) {
		return rand.nextFloat() < probability;
	}
	
	public static int getLevelFromXP( int xp, int baseLevelXP, int baseLevelGrowthFactor ) {
		double level = 1 + Math.log( (double) xp / baseLevelXP ) / Math.log( baseLevelGrowthFactor );
		return (int) Math.floor( level );
	}
	
	public static int getXPFromLevel( int level, int baseLevelXP, int baseSkillLevelGrowthFactor ) {
		return (int) Math.pow( baseLevelXP * baseSkillLevelGrowthFactor, (level - 1) );
	}
	
	public static int getDynamicPrice( int basePrice, int modifier ){
		return (int) (basePrice * (1 + modifier / 100));
	}
	
}
