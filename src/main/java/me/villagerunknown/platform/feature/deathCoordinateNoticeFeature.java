package me.villagerunknown.platform.feature;

import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.util.MessageUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;

import java.util.Optional;

public class deathCoordinateNoticeFeature {
	
	public static void execute() {
		ServerPlayerEvents.AFTER_RESPAWN.register((serverPlayerEntity, serverPlayerEntity1, b) -> {
			if( Platform.CONFIG.enableDeathCoordinateNotice ) {
				Optional<GlobalPos> globalPos = serverPlayerEntity.getLastDeathPos();
				if( null != globalPos && globalPos.isPresent() ) {
					BlockPos pos = globalPos.get().pos();
					String message = "You died at: " + (int) (double) pos.getX() + " " + (int) (double) pos.getY() + " " + (int) (double) pos.getZ();
					MessageUtil.sendChatMessage( serverPlayerEntity, MessageUtil.formComment( message ) );
				} // if
			} // if
		});
	}
	
}
