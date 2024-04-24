package uk.co.animandosolutions.mcdev.mysterystat.config;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import java.util.function.Consumer;

public class MysteryStatConfig extends ConfigWrapper<uk.co.animandosolutions.mcdev.mysterystat.config.ConfigModel> {

    public final Keys keys = new Keys();

    private final Option<java.lang.Long> discordChannelId = this.optionForKey(this.keys.discordChannelId);
    private final Option<java.lang.Boolean> publishResults = this.optionForKey(this.keys.publishResults);
    private final Option<java.lang.String> discordBotToken = this.optionForKey(this.keys.discordBotToken);

    private MysteryStatConfig() {
        super(uk.co.animandosolutions.mcdev.mysterystat.config.ConfigModel.class);
    }

    private MysteryStatConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(uk.co.animandosolutions.mcdev.mysterystat.config.ConfigModel.class, janksonBuilder);
    }

    public static MysteryStatConfig createAndLoad() {
        var wrapper = new MysteryStatConfig();
        wrapper.load();
        return wrapper;
    }

    public static MysteryStatConfig createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new MysteryStatConfig(janksonBuilder);
        wrapper.load();
        return wrapper;
    }

    public long discordChannelId() {
        return discordChannelId.value();
    }

    public void discordChannelId(long value) {
        discordChannelId.set(value);
    }

    public boolean publishResults() {
        return publishResults.value();
    }

    public void publishResults(boolean value) {
        publishResults.set(value);
    }

    public java.lang.String discordBotToken() {
        return discordBotToken.value();
    }

    public void discordBotToken(java.lang.String value) {
        discordBotToken.set(value);
    }


    public static class Keys {
        public final Option.Key discordChannelId = new Option.Key("discordChannelId");
        public final Option.Key publishResults = new Option.Key("publishResults");
        public final Option.Key discordBotToken = new Option.Key("discordBotToken");
    }
}

