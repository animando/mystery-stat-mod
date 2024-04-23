package uk.co.animandosolutions.mcdev.starter;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;
import uk.co.animandosolutions.mcdev.starter.config.StarterConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.*;

public class StarterMod implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("starter");
    public static final StarterConfig CONFIG = StarterConfig.createAndLoad();

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello Fabric world!");
        LOGGER.info("Channel %s"+CONFIG.discordChannelId());

        registerFooCommand();

    }

    private void registerFooCommand() {
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> dispatcher.register(
                        literal("foo")
                                .executes(context -> {
                                    // For versions below 1.19, replace "Text.literal" with "new LiteralText".
                                    // For versions below 1.20, remode "() ->" directly.
                                    context.getSource()
                                            .sendFeedback(() -> Text.literal("Called /foo with no arguments"), false);

                                    return 1;
                                })));
    }
}