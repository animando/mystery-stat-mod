package uk.co.animandosolutions.mcdev.mysterystat.config;

import com.uchuhimo.konf.ConfigSpec;
import com.uchuhimo.konf.RequiredItem;

public class DiscordSpec {
	public static final ConfigSpec spec = new ConfigSpec("discord");

	public static final RequiredItem<Long> channelId = new RequiredItem<>(spec, "channelId") {
	};

	public static final RequiredItem<String> token = new RequiredItem<>(spec, "token") {
	};

}
