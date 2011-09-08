package feildmaster.LocalChat;

import com.massivecraft.factions.Factions;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class Chat extends JavaPlugin {
    private int range = 1000;
    private String message = "No one within range of your voice...";
    private String prefix = "!";
    private String format = "%1$s shouts: %2$s";
    private Factions factions;
    private Configuration config;

    public void onLoad() {
        config = getConfiguration();
        factions = (Factions)getServer().getPluginManager().getPlugin("Factions");
    }

    public void onDisable() {
    }

    public void onEnable() {
        loadConfig();

        getServer().getPluginManager().registerEvent(Type.PLAYER_CHAT, new ChatListener(this), Priority.Lowest, this);

        getServer().getLogger().info("[LocalChat] All chats are now ranged");
    }

    private void loadConfig() {
        config.load();
        range = (int) Math.pow(config.getInt("local.range", range), 2);
        message = config.getString("local.null_message", message);
        prefix = config.getString("global.prefix", prefix);
        format = config.getString("global.format", format);
        config.save();
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

    public Factions getFactionsPlugin() {
        return factions;
    }
}
