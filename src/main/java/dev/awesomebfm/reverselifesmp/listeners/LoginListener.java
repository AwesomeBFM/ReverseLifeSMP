package dev.awesomebfm.reverselifesmp.listeners;

import dev.awesomebfm.reverselifesmp.ReverseLifeSMP;
import dev.awesomebfm.reverselifesmp.models.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.SQLException;

public class LoginListener implements Listener {
    private final ReverseLifeSMP plugin = ReverseLifeSMP.getPlugin();
    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        // Check to see if the server is disabled
        if (plugin.isLocked()) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', "&4&lReverse &c&lLife &a&lSMP") + "\n" +
                    ChatColor.RED + "Server is restarting for a new update! Please standby.");
            return;
        }

        // Retrieve (or create) player data
        try {
            PlayerData data = plugin.getDatabase().getData(e.getPlayer().getUniqueId());

            // If the player object is null, create a new one
            if (data == null) {
                data = new PlayerData(e.getPlayer().getUniqueId(), 0, false);
                plugin.getDatabase().createData(data);
            }

            // Check if the player is death banned and kick them if they are
            if (data.isDeathBanned()) {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', "&4&lReverse &c&lLife &a&lSMP") + "\n" +
                        ChatColor.RED + "You have gained too many hearts and have been death banned! See you next season!");
                return;
            }

            // Check to see if they should be death banned
            if (data.getHearts() == 10) {
                data.setDeathBanned(true);
                plugin.getDatabase().updateData(data);
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', "&4&lReverse &c&lLife &a&lSMP") + "\n" +
                        ChatColor.RED + "You have gained too many hearts and have been death banned! See you next season!");
                return;
            }

            plugin.addPlayer(data.getUuid(), data.getHearts());

        } catch (SQLException ex) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', "&4&lReverse &c&lLife &a&lSMP") + "\n" +
                    ChatColor.RED + "0x0001 An error occurred while trying to fetch your data. If the issue persists please contact an administrator.");
            throw new RuntimeException(ex);
        }
    }


}
