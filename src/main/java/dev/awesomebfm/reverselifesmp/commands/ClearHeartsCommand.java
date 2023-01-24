package dev.awesomebfm.reverselifesmp.commands;

import dev.awesomebfm.reverselifesmp.ReverseLifeSMP;
import dev.awesomebfm.reverselifesmp.models.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

/*
 * Clear Hearts Command
 * AwesomeBFM
 * 1/1/2023
 * Purpose:
 *      Allows an admin to clear their own or another player's hearts.
*/

public class ClearHeartsCommand implements CommandExecutor {
    private final ReverseLifeSMP plugin = ReverseLifeSMP.getPlugin();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        UUID uuid;
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "ERROR: You must specify a player to clear hearts from.");
                return true;
            }
            uuid = ((Player) sender).getUniqueId();
        } else {
            OfflinePlayer player = sender.getServer().getOfflinePlayer(args[0]);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + "ERROR: Player not found.");
                return true;
            }
            uuid = player.getUniqueId();
        }
        try {
            plugin.getDatabase().updateData(new PlayerData(uuid, 0, false));
        } catch (SQLException e) {
            sender.sendMessage(ChatColor.RED + "ERROR: Could not clear hearts.");
            throw new RuntimeException(e);
        }


        return true;
    }
}
