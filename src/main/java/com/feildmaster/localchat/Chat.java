package com.feildmaster.localchat;

import com.massivecraft.factions.P;
import org.bukkit.plugin.java.JavaPlugin;

public class Chat extends JavaPlugin {
    private int range = 1000;
    private final ChatListener listener = new ChatListener(this);
    private String message;
    private String prefix;
    private String format;
    private P factions;

    public void onLoad() {
        factions = (P) getServer().getPluginManager().getPlugin("Factions");
        loadConfig();
    }

    public void onEnable() {
        getServer().getPluginManager().registerEvents(listener, this);

        getLogger().info("All chats are now ranged");
    }

    private void loadConfig() {
        range = (int) Math.pow(getConfig().getInt("local.range"), 2);
        message = getConfig().getString("local.null_message");
        prefix = getConfig().getString("global.prefix");
        format = getConfig().getString("global.format");
    }

    public int getRange() {
        return range;
    }
    public String getPrefix() {
        return prefix;
    }
    public String getFormat() {
        return format;
    }
    public String getMessage() {
        return message;
    }

    public P getFactionsPlugin() {
        return factions;
    }
}
