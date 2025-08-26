package eu.minetown.simplebungeehub.commands;

import eu.minetown.simplebungeehub.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;

public class Hub extends Command{
    private final Main plugin;

    public Hub(Main plugin) {

            super((String) Main.config.getList("Settings.commands").get(0), null, commandAliases());

        this.plugin = plugin;
    }

    private static String[] commandAliases(){

        List<String> commands = Main.config.getStringList("Settings.commands");


        return commands.toArray(new String[0]);

    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)){
            sender.sendMessage("§cCommand is only for players.");
        }else{
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(p.hasPermission(Main.config.getString("Settings.permission")) || Main.config.getString("Settings.permission") == null){

                ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(Main.config.getString("Settings.server"));
                if(serverInfo !=null) {
                    if(!p.getServer().getInfo().equals(serverInfo)) {
                        p.connect(serverInfo);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.config.getString("Messages.send")));
                    }else{
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.config.getString("Messages.already-on-server")));
                    }
                }else{
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.config.getString("Messages.server-offline")));
                }
            }else{
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.config.getString("Messages.no-permission")));
            }

        }
    }
}
