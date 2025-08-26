package eu.minetown.simplebungeehub;

import eu.minetown.simplebungeehub.commands.Hub;
import eu.minetown.simplebungeehub.utils.BungeeConfig;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

public final class Main extends Plugin {
    public static Configuration config;

    @Override
    public void onEnable() {

        config = BungeeConfig.loadAndSave(this, "config.yml");
        getLogger().info("Config loaded.");

        getProxy().getPluginManager().registerCommand(this, new Hub(this));
        getLogger().info("Commands registered.");

        getLogger().info("Plugin enabled.");
    }
 
    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }
}
