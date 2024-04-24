package uk.co.animandosolutions.mcdev.mysterystat.mixin;

import net.minecraft.server.MinecraftServer;

import static uk.co.animandosolutions.mcdev.mysterystat.utils.Logger.LOGGER;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MysteryStatMixin {

	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(CallbackInfo info) {

		LOGGER.info("mixin - loadWorld");
		// This code is injected into the start of MinecraftServer.loadWorld()V
	}
}