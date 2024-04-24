package uk.co.animandosolutions.mcdev.starter.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "starter")
@Config(name = "starter", wrapperName = "StarterConfig")
public class ConfigModel {
    public long discordChannelId = -1L;
    public boolean publishResults = false;

    public String discordChannelWebhook = "";
}