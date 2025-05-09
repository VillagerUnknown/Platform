package me.villagerunknown.platform.feature;

import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.util.TimeUtil;
import me.villagerunknown.platform.util.WorldUtil;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;

public class bedClearsWeatherFeature {
	
	public static void execute() {
		registerUseBlockEvent();
	}
	
	private static void registerUseBlockEvent() {
		UseBlockCallback.EVENT.register((playerEntity, world, hand, blockHitResult ) -> {
			if( world.isClient() ) {
				return ActionResult.PASS;
			} // if
			
			BlockState blockState = world.getBlockState( blockHitResult.getBlockPos() );
			
			if( blockState.isIn( BlockTags.BEDS ) && WorldUtil.isRaining( world ) ) {
				if(
						Platform.CONFIG.bedInteractionsAlwaysClearWeather
						|| ( Platform.CONFIG.bedInteractionsClearWeatherAtNight && TimeUtil.isNight( world ) )
				) {
					WorldUtil.setRaining( world, false );
				} // if
			} // if
			
			return ActionResult.PASS;
		});
	}
	
}
