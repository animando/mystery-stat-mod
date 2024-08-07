package uk.co.animandosolutions.mcdev.mysterystat;

import static uk.co.animandosolutions.mcdev.mysterystat.utils.Logger.LOGGER;

import net.fabricmc.api.ModInitializer;
import uk.co.animandosolutions.mcdev.mysterystat.command.CommandHandler;
import uk.co.animandosolutions.mcdev.mysterystat.config.DiscordConfig;
import uk.co.animandosolutions.mcdev.mysterystat.discord.DiscordBot;

public class MysteryStatMod implements ModInitializer {

    @SuppressWarnings("unused")
	private DiscordBot discordBot = DiscordBot.INSTANCE;

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Loaded MysteryStat mod");

        LOGGER.info(String.format("Discord token: %s", DiscordConfig.INSTANCE.discordBotToken()));
        LOGGER.info(String.format("Discord channel: %s", DiscordConfig.INSTANCE.discordChannelId()));
        CommandHandler.INSTANCE.registerCommands();
    }
} 