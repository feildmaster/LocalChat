package feildmaster.LocalChat;

import java.util.Set;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

public class ChatListener extends PlayerListener {
    private Chat plugin;
    private int range;

    public ChatListener(Chat p) {
        plugin = p;
    }

    public void onPlayerChat(PlayerChatEvent event) {
        Player s = event.getPlayer();
        Set<Player> rs = event.getRecipients();
        range = plugin.getRange();

        for(Player r : rs) {
            if(s.getWorld() != r.getWorld() || outOfRange(s.getLocation(), r.getLocation()))
                rs.remove(r);
        }

        if(rs.size() == 1) {
            s.sendMessage(plugin.getMessage());
            event.setCancelled(true);
        }
    }

    /*
     * Is this faster... or slower than
     * location.distance(location)?
     * Or should I just use
     * location.distanceSquared, and Square the range?
     */
    private Boolean outOfRange(Location l, Location ll) {
        if(l == ll) return false;

        if(Math.max(l.getBlockX(), ll.getBlockX())-Math.min(l.getBlockX(), ll.getBlockX())>range) return true;
        if(Math.max(l.getBlockY(), ll.getBlockY())-Math.min(l.getBlockY(), ll.getBlockY())>range) return true;
        if(Math.max(l.getBlockZ(), ll.getBlockZ())-Math.min(l.getBlockZ(), ll.getBlockZ())>range) return true;

        return false;
    }
}
