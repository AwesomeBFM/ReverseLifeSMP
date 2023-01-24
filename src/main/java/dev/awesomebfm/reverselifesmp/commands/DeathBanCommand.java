package dev.awesomebfm.reverselifesmp.commands;

import dev.awesomebfm.reverselifesmp.ReverseLifeSMP;
import dev.awesomebfm.reverselifesmp.models.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.SQLException;
import java.util.UUID;

/*
 * Death Ban Command
 * AwesomeBFM
 * 1/1/2023
 * Purpose:
 *      Allows an admin to death ban a player
 */

public class DeathBanCommand implements CommandExecutor {
    private final ReverseLifeSMP plugin = ReverseLifeSMP.getPlugin();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "ERROR: You must specify a player to ban.");
            return true;
        }
        String player = args[0];
        UUID uuid = Bukkit.getOfflinePlayer(player).getUniqueId();
        String name = Bukkit.getOfflinePlayer(player).getName();
        if (uuid == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: Player not found.");
            return true;
        }
        try {
            plugin.getDatabase().updateData(new PlayerData(uuid, 10, true));
            plugin.updatePlayer(uuid, 10);
            sender.sendMessage(ChatColor.GREEN + "Successfully banned " + player + ".");
            plugin.getDatabase().addDeathBan(uuid, name);
            if (plugin.containsPlayer(uuid)) {
                Bukkit.getPlayer(uuid).kickPlayer(ChatColor.translateAlternateColorCodes('&', "&4&lReverse &c&lLife &a&lSMP") + "\n" +
                        ChatColor.RED + "You have gained too many hearts and have been death banned! See you next season!");
                Bukkit.broadcast(ChatColor.RED + "Player " + name + " has been death banned for gaining too many hearts!", "rlsmp.default");
            }
        } catch (SQLException e) {
            sender.sendMessage(ChatColor.RED + "ERROR: Could not unban player.");
            throw new RuntimeException(e);
        }
        return true;
    }
}
