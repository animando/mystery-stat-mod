package uk.co.animandosolutions.mcdev.mysterystat;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;
import uk.co.animandosolutions.mcdev.mysterystat.discord.DiscordBot;

import static net.minecraft.server.command.CommandManager.*;
import static uk.co.animandosolutions.mcdev.mysterystat.utils.Logger.LOGGER;

public class MysteryStatMod implements ModInitializer {

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Loaded MysteryStat mod");

        registerFooCommand();
        DiscordBot.sendMessage("mod initialized");

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