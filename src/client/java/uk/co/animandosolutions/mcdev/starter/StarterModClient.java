package uk.co.animandosolutions.mcdev.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;

public class StarterModClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("starter");

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		LOGGER.info("Initialize starter mod client");
	}
}