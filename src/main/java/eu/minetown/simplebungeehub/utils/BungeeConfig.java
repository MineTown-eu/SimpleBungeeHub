package eu.minetown.simplebungeehub.utils;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BungeeConfig {

    /**
     * Loads a configuration file. If it doesn't exist, it creates it from the plugin's resources.
     *
     * @param plugin   The main plugin instance.
     * @param fileName The name of the configuration file (e.g., "config.yml").
     * @return The loaded Configuration object.
     */
    public static Configuration loadAndSave(Plugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);

        if (!file.exists()) {
            try {
                // Ensure the data folder exists.
                if (!plugin.getDataFolder().exists()) {
                    plugin.getDataFolder().mkdir();
                }
                // Copy the default config from the plugin's resources.
                try (InputStream in = plugin.getResourceAsStream(fileName)) {
                    if (in == null) {
                        // If no resource is found, create an empty file to avoid errors.
                        file.createNewFile();
                    } else {
                        Files.copy(in, file.toPath());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
            // Return an empty configuration on a load error to prevent NullPointerExceptions.
            return new Configuration();
        }
    }

    /**
     * Saves a configuration object to a file.
     *
     * @param plugin   The main plugin instance.
     * @param config   The Configuration object to save.
     * @param fileName The name of the file to save to (e.g., "config.yml").
     */
    public static void save(Plugin plugin, Configuration config, String fileName) {
        try {
            File file = new File(plugin.getDataFolder(), fileName);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}