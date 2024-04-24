package uk.co.animandosolutions.mcdev.mysterystat.discord;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;

import static uk.co.animandosolutions.mcdev.mysterystat.config.ConfigWrapper.CONFIG;
import static uk.co.animandosolutions.mcdev.mysterystat.utils.Logger.LOGGER;

public class DiscordBot {
    public static DiscordBot INSTANCE = new DiscordBot();

    private DiscordBot() {

    }

    private boolean checkConfig() {
        return (!CONFIG.discordBotToken().equals("") && CONFIG.discordChannelId() != -1);
    }

    public void sendMessage(String messageContent) {
        if (checkConfig()) {
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
