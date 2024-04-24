package uk.co.animandosolutions.mcdev.mysterystat.mixin;

import net.minecraft.server.MinecraftServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MysteryStatMixin {
	private static final Logger LOGGER = LoggerFactory.getLogger("mystery-stat");
	
	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(CallbackInfo info) {

		LOGGER.info("mixin - loadWorld");
		// This code is injected into the start of MinecraftServer.loadWorld()V
	}
}