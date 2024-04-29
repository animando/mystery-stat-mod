package uk.co.animandosolutions.mcdev.mysterystat.config;

import com.uchuhimo.konf.Config;
import com.uchuhimo.konf.BaseConfig;
import com.uchuhimo.konf.source.toml.TomlProvider;

import net.fabricmc.loader.api.FabricLoader;
import uk.co.animandosolutions.mcdev.mysterystat.utils.Logger;

public class ConfigWrapper {


	public static final ConfigWrapper CONFIG = new ConfigWrapper();
	private Config konfConfig;

	private ConfigWrapper() {

		var c = new BaseConfig() {
		};
		c.addSpec(DiscordSpec.spec);
		var defaultSource = TomlProvider.get().resource("mysterystat.toml", true);
		var configFileSource = TomlProvider.get().file(FabricLoader.getInstance().getConfigDir().resolve("mysterystat.toml").toFile(), false);
		this.konfConfig = c.withSource(defaultSource).withSource(configFileSource);
	}

	public void checkConfig() {
		Logger.LOGGER.info("" + this.konfConfig);
	}

	public long discordChannelId() {
		return this.konfConfig.get("discord.channelId");
	}

	public String discordBotToken() {
		return this.konfConfig.get("discord.token");
	}

}
