package uk.co.animandosolutions.mcdev.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import uk.co.animandosolutions.mcdev.starter.config.StarterConfig;

public class DiscordBot {
    public static final StarterConfig CONFIG = StarterConfig.createAndLoad();
    public static final Logger LOGGER = LoggerFactory.getLogger("starter");

    public static void sendMessage(String messageContent) {

        try {
            var client = DiscordClient.create(CONFIG.discordChannelWebhook());

            // var gateway = client.login().block();
            var channel = client.getChannelById(Snowflake.of(1232394958804095090L));
   
            var message = channel.createMessage(messageContent);
            message.block();
        } catch (Exception e) {
            LOGGER.error("Error sending discord message", e);
        }

    }
}
