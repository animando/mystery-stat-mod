package uk.co.animandosolutions.mcdev.mysterystat.config;

import com.uchuhimo.konf.ConfigSpec;
import com.uchuhimo.konf.RequiredItem;

public class DiscordConfig {
    static class Spec {
        public static final ConfigSpec spec = new ConfigSpec("discord");

        public static final RequiredItem<Long> channelId = new RequiredItem<>(spec, "channelId") {
        };

        public static final RequiredItem<String> token = new RequiredItem<>(spec, "token") {
        };

    }

	public static final DiscordConfig INSTANCE = new DiscordConfig();
	
	
	private ConfigWrapper _config;

	private DiscordConfig() {
	    this._config = new ConfigWrapper(Spec.spec, "mysterystat.toml");
	}

	public long discordChannelId() {
		return this._config.getValue("discord.channelId", Long.class);
	}

	public String discordBotToken() {
        return this._config.getValue("discord.token", String.class);
	}

}
