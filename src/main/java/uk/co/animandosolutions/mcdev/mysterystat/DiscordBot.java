package uk.co.animandosolutions.mcdev.mysterystat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import uk.co.animandosolutions.mcdev.mysterystat.config.MysteryStatConfig;

public class DiscordBot {
    public static final MysteryStatConfig CONFIG = MysteryStatConfig.createAndLoad();
    public static final Logger LOGGER = LoggerFactory.getLogger("mystery-stat");

    public static boolean checkConfig() {
        return (CONFIG.discordBotToken().equals("") && CONFIG.discordChannelId() != -1);
    }

    public static void sendMessage(String messageContent) {
        if (DiscordBot.checkConfig()) {
            try {
                var client = DiscordClient.create(CONFIG.discordBotToken());

                // var gateway = client.login().block();
                var channel = client.getChannelById(Snowflake.of(CONFIG.discordChannelId()));
    
                var message = channel.createMessage(messageContent);
                message.block();
            } catch (Exception e) {
                LOGGER.error("Error sending discord message", e);
            }
        }
    }
}
