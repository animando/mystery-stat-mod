package uk.co.animandosolutions.mcdev.mysterystat.config;

import io.wispforest.owo.config.annotation.Config;

@Config(name = "mystery-stat", wrapperName = "MysteryStatConfig")
public class ConfigModel {
    public long discordChannelId = -1L;
    public String discordBotToken = "";
}
