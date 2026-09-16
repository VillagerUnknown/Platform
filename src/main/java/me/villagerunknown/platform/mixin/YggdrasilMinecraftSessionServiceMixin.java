package me.villagerunknown.platform.mixin;

import com.mojang.authlib.minecraft.SessionService;
import com.mojang.authlib.services.MinecraftServicesSessionService;
import com.mojang.authlib.services.ProfileResult;
import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.feature.playerCacheFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(MinecraftServicesSessionService.class)
public abstract class YggdrasilMinecraftSessionServiceMixin implements SessionService {
	
	@Inject(method = "fetchProfile", at = @At("HEAD"), cancellable = true, remap = false)
	public void fetchProfile(UUID profileId, boolean requireSecure, CallbackInfoReturnable<ProfileResult> cir) {
		if( null != Platform.CONFIG && Platform.CONFIG.enablePlayerCaching ) {
			if (playerCacheFeature.isCached(profileId)) {
				Platform.LOGGER.info("SessionService loaded profile from cache: {}", profileId);
				cir.setReturnValue(playerCacheFeature.getCachedProfileResult(profileId));
			} else {
				Platform.LOGGER.info("SessionService requested profile from service: {}", profileId);
			} // if, else
		} // if
	}
	
}