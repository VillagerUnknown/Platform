package me.villagerunknown.platform.mixin;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.feature.playerCacheFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(YggdrasilMinecraftSessionService.class)
public abstract class YggdrasilMinecraftSessionServiceMixin implements MinecraftSessionService {
	
	@Inject(method = "fetchProfile(Ljava/util/UUID;Z)Lcom/mojang/authlib/yggdrasil/ProfileResult;", at = @At("HEAD"), cancellable = true, remap = false)
	public void fetchProfile(UUID profileId, boolean requireSecure, CallbackInfoReturnable<ProfileResult> cir) {
		if( null != Platform.CONFIG && Platform.CONFIG.enablePlayerCaching && playerCacheFeature.isCached( profileId ) ) {
			Platform.LOGGER.info("MinecraftSessionService loaded profile from cache: {}", profileId);
			cir.setReturnValue( playerCacheFeature.getCachedProfileResult( profileId ) );
		} // if
	}

}
