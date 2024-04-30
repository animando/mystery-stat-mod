package uk.co.animandosolutions.mcdev.mysterystat.discord;

import static java.lang.String.format;
import static java.net.http.HttpResponse.BodySubscribers.ofString;
import static java.nio.charset.Charset.defaultCharset;
import static uk.co.animandosolutions.mcdev.mysterystat.utils.Logger.LOGGER;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.animandosolutions.mcdev.mysterystat.config.DiscordConfig;
import uk.co.animandosolutions.mcdev.mysterystat.utils.Logger;

public class DiscordBot {

    private static final String AUTHORIZATION_PATTERN = "Bot %s";
    private static final String USER_AGENT_URL = "https://github.com/animando/mystery-stat-mod";
    private static final String USER_AGENT_PATTERN = "DiscordBot (%s, %s)";
    private static final String APPLICATION_JSON = "application/json";
    private static final String ENDPOINT = "https://discordapp.com/api/channels/%s/messages";
    private static final int API_VERSION = 10;

    static interface Headers {
        static final String AUTHORIZATION = "Authorization";
        static final String CONTENT_TYPE = "Content-Type";
        static final String USER_AGENT = "User-Agent";
    }

    public static DiscordBot INSTANCE = new DiscordBot();
    
    private DiscordConfig CONFIG = DiscordConfig.INSTANCE;

    static record Body(String content) {
    }

    private DiscordBot() {

    }

    public boolean checkConfig() {
        return !(CONFIG.discordBotToken().equals("") || CONFIG.discordChannelId() == -1);
    }

    public void sendMessage(String messageContent) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            String url = String.format(ENDPOINT, CONFIG.discordChannelId());
            String body = new ObjectMapper().writeValueAsString(new Body(messageContent));
            var request = HttpRequest.newBuilder(new URI(url))
                    .header(Headers.AUTHORIZATION, String.format(AUTHORIZATION_PATTERN, CONFIG.discordBotToken()))
                    .header(Headers.CONTENT_TYPE, APPLICATION_JSON)
                    .header(Headers.USER_AGENT, format(USER_AGENT_PATTERN, USER_AGENT_URL, API_VERSION))
                    .POST(BodyPublishers.ofString(body)).build();

            httpClient.sendAsync(request, r -> ofString(defaultCharset())).thenAccept(this.handler());
        } catch (Exception e) {
            LOGGER.error("Error sending discord message", e);
        }
    }

    private Consumer<? super HttpResponse<String>> handler() {
        return response -> {
            var statusCode = response.statusCode();
            if (statusCode != 200) {
                Logger.LOGGER.error(
                        format("Unsuccessful discord post - status=%20; response=%s", statusCode, response.body()));
            }
        };
    }

}
