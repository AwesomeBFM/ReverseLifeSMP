package dev.awesomebfm.reverselifesmp.commands;

import dev.awesomebfm.reverselifesmp.models.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import dev.awesomebfm.reverselifesmp.ReverseLifeSMP;
import org.bukkit.command.CommandSender;

import java.sql.SQLException;
import java.util.UUID;

/*
 * Un Death Ban Command
 * AwesomeBFM
 * 1/1/2023
 * Purpose:
 *      Allows an admin to remove a player's death ban.
 */

public class UnDeathBanCommand implements CommandExecutor {
    private final ReverseLifeSMP plugin = ReverseLifeSMP.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "ERROR: You must specify a player to unban.");
            return true;
        }
        String player = args[0];
        UUID uuid = Bukkit.getOfflinePlayer(player).getUniqueId();
        if (uuid == null) {
            sender.sendMessage(ChatColor.RED + "ERROR: Player not found.");
            return true;
        }
        try {
            plugin.getDatabase().updateData(new PlayerData(uuid, 0, false));
            plugin.getDatabase().removeDeathBan(uuid);
            sender.sendMessage(ChatColor.GREEN + "Successfully unbanned " + player + ".");
        } catch (SQLException e) {
            sender.sendMessage(ChatColor.RED + "ERROR: Could not unban player.");
            throw new RuntimeException(e);
        }
        return true;
    }
}
