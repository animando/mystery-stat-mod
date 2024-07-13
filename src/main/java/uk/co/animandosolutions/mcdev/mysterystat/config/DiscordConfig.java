package uk.co.animandosolutions.mcdev.mysterystat.config;

import java.io.IOException;
import java.util.Optional;

public class DiscordConfig {

	public static final DiscordConfig INSTANCE = new DiscordConfig();
    private Optional<ConfigHelper> configHelper;
	
	private DiscordConfig() {

	    try {
	        this.configHelper = Optional.of(new ConfigHelper("mysterystat.toml"));
	    }  catch (IOException e) {
	        this.configHelper = Optional.empty();
	    }
	}

	public long discordChannelId() {
	    return configHelper.map(h -> h.getLongConfigValue("discord.channelId")).orElseThrow();
	}

	public String discordBotToken() {
        return configHelper.map(h -> h.getStringConfigValue("discord.token")).orElseThrow();
	}

}
