package feildmaster.LocalChat;

import com.massivecraft.factions.Factions;
import java.io.File;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

public class Chat extends JavaPlugin {
    private int range = 1000;
    private String message = "No one within range of your voice...";
    private Factions factions;

    public void onLoad() {
        if(!(new File(getDataFolder(),"config.yml").exists())) {
            getConfiguration().setProperty("range", range);
            getConfiguration().setProperty("null_message", message);
            getConfiguration().save();
        }

        factions = (Factions)getServer().getPluginManager().getPlugin("Factions");
    }

    public void onDisable() {
    }

    public void onEnable() {
        loadConfig();

        getServer().getPluginManager().registerEvent(Type.PLAYER_CHAT, new ChatListener(this), Priority.Low, this);

        getServer().getLogger().info("[LocalChat] All chats are now ranged");
    }

    private void loadConfig() {
        range = getConfiguration().getInt("range", range);
        message = getConfiguration().getString("null_message", message);
    }

    public int getRange() {
        return range;
    }

    public String getMessage() {
        return message;
    }

    public Factions getFactionsPlugin() {
        return factions;
    }
}
