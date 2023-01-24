package dev.awesomebfm.reverselifesmp.listeners;

import dev.awesomebfm.reverselifesmp.ReverseLifeSMP;
import dev.awesomebfm.reverselifesmp.models.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.SQLException;

public class DeathListener implements Listener {
    private final ReverseLifeSMP plugin = ReverseLifeSMP.getPlugin();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        // Give player a heart
        int hearts = plugin.retrievePlayer(e.getEntity().getUniqueId());
        hearts++;
        plugin.updatePlayer(e.getEntity().getUniqueId(), hearts);

        // Check if the player need be death banned
        if (hearts >= 10) {
            Bukkit.getServer().broadcastMessage(ChatColor.GREEN + e.getEntity().getName() + ChatColor.RED + " gained too many hearts and has been death banned!");
            try {
                plugin.getDatabase().updateData(new PlayerData(e.getEntity().getUniqueId(), hearts, true));
                plugin.getDatabase().addDeathBan(e.getEntity().getUniqueId(), e.getEntity().getName());
                Bukkit.broadcast(ChatColor.RED + "Player " + e.getEntity().getName() + " has been death banned for gaining too many hearts!", "rlsmp.default");
            } catch (SQLException ex) {
                e.getEntity().kickPlayer(ChatColor.translateAlternateColorCodes('&', "&4&lReverse &c&lLife &a&lSMP") + "\n" +
                        ChatColor.RED + "0x0003 An error occurred in your connection. Please reconnect. If the issue persists please contact an administrator.");
                throw new RuntimeException(ex);
            }
        }
    }

}
