package dev.awesomebfm.reverselifesmp.listeners;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import dev.awesomebfm.reverselifesmp.ReverseLifeSMP;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.sql.SQLException;

public class RespawnListener implements Listener {
    private final ReverseLifeSMP plugin = ReverseLifeSMP.getPlugin();

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        // give player their hearts
        if (!plugin.containsPlayer(e.getPlayer().getUniqueId())) {
            e.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', "&4&lReverse &c&lLife &a&lSMP") + "\n" +
                    ChatColor.RED + "0x0002 An error occurred while trying to sync you data. Please rejoin the server. If the issue persists please contact an administrator.");
            return;
        }

        try {
            if (plugin.getDatabase().getData(e.getPlayer().getUniqueId()).isDeathBanned()) {
                e.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', "&4&lReverse &c&lLife &a&lSMP") + "\n" +
                        ChatColor.RED + "You have gained too many hearts and have been death banned! See you next season!");
                return;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        int hearts = plugin.retrievePlayer(e.getPlayer().getUniqueId());
        int health = hearts * 2 + 20;
        e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        e.getPlayer().sendMessage(ChatColor.GREEN + "You have gained " + ChatColor.RED + hearts + ChatColor.GREEN + " hearts." +
                "You can get " + ChatColor.RED + (10 - hearts) + ChatColor.GREEN + " more hearts before a death ban!");
    }


}
