package uk.co.animandosolutions.mcdev.starter.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "starter")
@Config(name = "starter", wrapperName = "StarterConfig")
public class ConfigModel {
    public int discordChannelId = -1;
    public boolean publishResults = false;

    public String discordChannelWebhook = "";
}