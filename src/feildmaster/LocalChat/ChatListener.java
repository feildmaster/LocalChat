package feildmaster.LocalChat;

import java.util.HashSet;
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
        Player sender = event.getPlayer();

        if(plugin.getFactionsPlugin() != null && plugin.getFactionsPlugin().isPlayerFactionChatting(sender)) return;

        if(event.getMessage().startsWith(plugin.getPrefix())) {
            event.setMessage(event.getMessage().substring(plugin.getPrefix().length()));
            event.setFormat(plugin.getFormat());
            return;
        }

        range = plugin.getRange();

        for(Player r : new HashSet<Player>(event.getRecipients()))
            if(sender.getWorld() != r.getWorld() || outOfRange(sender.getLocation(), r.getLocation()))
                event.getRecipients().remove(r);

        if(event.getRecipients().size() == 1) {
            sender.sendMessage(plugin.getMessage());
            event.setCancelled(true);
        }
    }

    private Boolean outOfRange(Location l, Location ll) {
        if(l.equals(ll))
            return false;
        if(l.distanceSquared(ll)>range)
            return true;
        
        return false;
    }
}
