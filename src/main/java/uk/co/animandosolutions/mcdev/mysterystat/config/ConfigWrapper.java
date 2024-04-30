package uk.co.animandosolutions.mcdev.mysterystat.config;

import static java.lang.String.format;

import com.uchuhimo.konf.BaseConfig;
import com.uchuhimo.konf.Config;
import com.uchuhimo.konf.ConfigSpec;
import com.uchuhimo.konf.source.toml.TomlProvider;

import net.fabricmc.loader.api.FabricLoader;
import uk.co.animandosolutions.mcdev.mysterystat.utils.Logger;

public class ConfigWrapper {

	private Config konfConfig;

	public ConfigWrapper(final ConfigSpec spec, final String filename) {
	    
		var c = new BaseConfig() {
		};
		c.addSpec(spec);
		var defaultSource = TomlProvider.get().resource(filename, true);
		this.konfConfig = c.withSource(defaultSource);
		var configFile = FabricLoader.getInstance().getConfigDir().resolve(filename).toFile();
		try {
		    if (!configFile.exists()) {
    			configFile.createNewFile();
    			new com.uchuhimo.konf.source.toml.TomlWriter(this.konfConfig).toFile(configFile);
		    }
		} catch (Exception e) {
			Logger.LOGGER.error(format("Error creating %s", filename), e);
		}
		var configFileSource = TomlProvider.get().file(configFile, false);
		this.konfConfig = this.konfConfig.withSource(configFileSource);
	}

	public <T> T getValue(final String key, final Class<T> clazz) {
		return this.konfConfig.get(key);
	}

}
