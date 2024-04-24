package uk.co.animandosolutions.mcdev.mysterystat.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "mystery-stat")
@Config(name = "mystery-stat", wrapperName = "MysteryStatConfig")
public class ConfigModel {
    public long discordChannelId = -1L;
    public boolean publishResults = false;
    public String discordBotToken = "";
}