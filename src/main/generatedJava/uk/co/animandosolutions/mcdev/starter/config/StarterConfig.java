package uk.co.animandosolutions.mcdev.starter.config;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import java.util.function.Consumer;

public class StarterConfig extends ConfigWrapper<uk.co.animandosolutions.mcdev.starter.config.ConfigModel> {

    public final Keys keys = new Keys();

    private final Option<java.lang.Integer> discordChannelId = this.optionForKey(this.keys.discordChannelId);
    private final Option<java.lang.Boolean> publishResults = this.optionForKey(this.keys.publishResults);
    private final Option<java.lang.String> discordChannelWebhook = this.optionForKey(this.keys.discordChannelWebhook);

    private StarterConfig() {
        super(uk.co.animandosolutions.mcdev.starter.config.ConfigModel.class);
    }

    private StarterConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(uk.co.animandosolutions.mcdev.starter.config.ConfigModel.class, janksonBuilder);
    }

    public static StarterConfig createAndLoad() {
        var wrapper = new StarterConfig();
        wrapper.load();
        return wrapper;
    }

    public static StarterConfig createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new StarterConfig(janksonBuilder);
        wrapper.load();
        return wrapper;
    }

    public int discordChannelId() {
        return discordChannelId.value();
    }

    public void discordChannelId(int value) {
        discordChannelId.set(value);
    }

    public boolean publishResults() {
        return publishResults.value();
    }

    public void publishResults(boolean value) {
        publishResults.set(value);
    }

    public java.lang.String discordChannelWebhook() {
        return discordChannelWebhook.value();
    }

    public void discordChannelWebhook(java.lang.String value) {
        discordChannelWebhook.set(value);
    }


    public static class Keys {
        public final Option.Key discordChannelId = new Option.Key("discordChannelId");
        public final Option.Key publishResults = new Option.Key("publishResults");
        public final Option.Key discordChannelWebhook = new Option.Key("discordChannelWebhook");
    }
}

