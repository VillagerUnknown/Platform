package me.villagerunknown.platform.feature;

import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.util.TimeUtil;
import me.villagerunknown.platform.util.WorldUtil;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;

public class bedClearsWeatherFeature {
	
	public static void execute() {
		registerUseBlockEvent();
	}
	
	private static void registerUseBlockEvent() {
		UseBlockCallback.EVENT.register((playerEntity, world, hand, blockHitResult ) -> {
			if( world.isClientSide() ) {
				return InteractionResult.PASS;
			} // if
			
			BlockState blockState = world.getBlockState( blockHitResult.getBlockPos() );
			
			if( blockState.is( BlockTags.BEDS ) && WorldUtil.isRaining( world ) ) {
				if(
						Platform.CONFIG.bedInteractionsAlwaysClearWeather
						|| ( Platform.CONFIG.bedInteractionsClearWeatherAtNight && TimeUtil.isNight( world ) )
				) {
					WorldUtil.setRaining( world, false );
				} // if
			} // if
			
			return InteractionResult.PASS;
		});
	}
	
}
