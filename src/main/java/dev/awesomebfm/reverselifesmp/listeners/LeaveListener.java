package dev.awesomebfm.reverselifesmp.listeners;

import dev.awesomebfm.reverselifesmp.ReverseLifeSMP;
import dev.awesomebfm.reverselifesmp.models.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class LeaveListener implements Listener {
    private final ReverseLifeSMP plugin = ReverseLifeSMP.getPlugin();

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        int hearts = plugin.retrievePlayer(e.getPlayer().getUniqueId());
        boolean deathBanned = hearts >= 10;
        try {
            plugin.getDatabase().updateData(new PlayerData(e.getPlayer().getUniqueId(), hearts, deathBanned));
            plugin.removePlayer(e.getPlayer().getUniqueId());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
