package me.villagerunknown.platform.client.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import me.villagerunknown.platform.Platform;
import me.villagerunknown.platform.PlatformMod;
import me.villagerunknown.platform.client.PlatformClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {
	
	@Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
	private void renderLabelIfPresent(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float tickDelta, CallbackInfo ci) {
		if( !PlatformClient.nametagsVisible || ( entity.isPlayer() && !PlatformClient.playerNametagsVisible ) ) {
			ci.cancel();
		} // if
	}

}
