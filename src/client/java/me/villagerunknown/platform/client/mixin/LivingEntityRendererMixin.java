package me.villagerunknown.platform.client.mixin;

import me.villagerunknown.platform.client.PlatformClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {
	
	@Inject(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
	private void hasLabel(T livingEntity, CallbackInfoReturnable<Boolean> cir) {
		if( !PlatformClient.nametagsVisible || ( livingEntity.isPlayer() && !PlatformClient.playerNametagsVisible ) ) {
			cir.setReturnValue(false);
		} // if
	}

}
