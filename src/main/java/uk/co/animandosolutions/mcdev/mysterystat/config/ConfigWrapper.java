package uk.co.animandosolutions.mcdev.mysterystat.config;

public class ConfigWrapper {
    private final MysteryStatConfig _config = MysteryStatConfig.createAndLoad();

    public static final ConfigWrapper CONFIG = new ConfigWrapper();

    private ConfigWrapper() {

    }
    
    public void checkConfig() {
    	
    }

    public long discordChannelId() {
        return _config.discordChannelId();
    }
    
    public String discordBotToken() {
        return _config.discordBotToken();
    }
    
}
