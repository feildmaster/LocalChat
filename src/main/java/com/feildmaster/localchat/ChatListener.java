package com.feildmaster.localchat;

import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatListener implements Listener {
    private Chat plugin;

    public ChatListener(Chat p) {
        plugin = p;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(PlayerChatEvent event) {
        Player sender = event.getPlayer();

        if (plugin.getFactionsPlugin() != null && plugin.getFactionsPlugin().isPlayerFactionChatting(sender)) {
            return;
        }

        if (event.getMessage().startsWith(plugin.getPrefix())) {
            event.setMessage(event.getMessage().substring(plugin.getPrefix().length()));
            event.setFormat(plugin.getFormat());
            return;
        }

        for (Player r : new HashSet<Player>(event.getRecipients())) {
            if (outOfRange(sender.getLocation(), r.getLocation())) {
                event.getRecipients().remove(r);
            }
        }

        if (event.getRecipients().size() == 1) {
            sender.sendMessage(plugin.getMessage());
            event.setCancelled(true);
        }
    }

    private Boolean outOfRange(Location l, Location ll) {
        if (l.equals(ll)) {
            return false;
        } else if (l.getWorld() != ll.getWorld()) {
            return true;
        }
        return l.distanceSquared(ll) > plugin.getRange();
    }
}
