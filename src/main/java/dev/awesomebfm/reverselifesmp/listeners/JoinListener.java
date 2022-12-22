package dev.awesomebfm.reverselifesmp.listeners;

import dev.awesomebfm.reverselifesmp.ReverseLifeSMP;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final ReverseLifeSMP plugin = ReverseLifeSMP.getPlugin();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        // give player their hearts
        if (!plugin.containsPlayer(e.getPlayer().getUniqueId())) {
            e.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', "&4&lReverse &c&lLife &a&lSMP") + "\n" +
                    ChatColor.RED + "0x0002 An error occurred while trying to sync you data. Please rejoin the server. If the issue persists please contact an administrator.");
            return;
        }
        int hearts = plugin.retrievePlayer(e.getPlayer().getUniqueId());
        int health = hearts * 2 + 20;
        e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
    }
}
