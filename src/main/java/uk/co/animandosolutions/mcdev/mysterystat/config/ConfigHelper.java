package uk.co.animandosolutions.mcdev.mysterystat.config;

import static net.fabricmc.loader.api.FabricLoader.getInstance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

import net.fabricmc.loader.api.FabricLoader;

public class ConfigHelper {
    private String fileName;
    private TomlParseResult tomlParseResult;

    public ConfigHelper(String fileName) throws IOException {
        this.fileName = fileName;
        this.tomlParseResult = this.readOrCreateTomlFile();
    }
    
    private Path getConfigPath() {
        return Paths.get(FabricLoader.getInstance().getConfigDir().toString(), fileName);
    }
    
    private boolean fileExists() {
        return getConfigPath().toFile().exists();
    }
    
    private InputStream getDefaultFileAsStream() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(fileName);
    }
    
    private void createFileFromDefault() throws IOException {
        var defaultStream = getDefaultFileAsStream();
        FileOutputStream fos = new FileOutputStream(getConfigPath().toFile());
        defaultStream.transferTo(fos);
        
    }

    private TomlParseResult readOrCreateTomlFile() throws IOException {
        if (!fileExists()) {
            createFileFromDefault();
        }
        return Toml.parse(getConfigPath());
    }
    
    public String getStringConfigValue(String tomlPath) {
        return tomlParseResult.getString(tomlPath);
    }
    
    public Long getLongConfigValue(String tomlPath) {
        return tomlParseResult.getLong(tomlPath);
    }
}
