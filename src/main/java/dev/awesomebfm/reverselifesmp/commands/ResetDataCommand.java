package dev.awesomebfm.reverselifesmp.commands;

import dev.awesomebfm.reverselifesmp.ReverseLifeSMP;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

/*
 * Reset Data Command
 * AwesomeBFM
 * 1/1/2023
 * Purpose:
 *      Allows the console to reset all server data.
 */

public class ResetDataCommand implements CommandExecutor {
    private final ReverseLifeSMP plugin = ReverseLifeSMP.getPlugin();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof ConsoleCommandSender)) {
            sender.sendMessage(ChatColor.RED + "ERROR: For security issues this command can only be ran by the console.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "WARNING: This command will reset ALL deathbans and hearts. If you are sure you want to do this type: " +
                    ChatColor.GREEN + "/resetdata confirm");
            return true;
        }
        sender.sendMessage(ChatColor.GREEN + "Begging the reset of all plugin data, once this process is complete, make sure to restart the server.");
        for (Player p : sender.getServer().getOnlinePlayers()) {
            p.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&4&lReverse &c&lLife &a&lSMP") + "\n" +
                    ChatColor.RED + "Server is restarting for a new update! Please standby.");
        }
        plugin.setLocked(true);
        try {
            plugin.getDatabase().purge();
            sender.sendMessage(ChatColor.GREEN + "Successfully reset all plugin data! Please restart the server.");
        } catch (SQLException e) {
            sender.sendMessage(ChatColor.RED + "ERROR: Failed to purge data from the database. Please run the command again.");
            throw new RuntimeException(e);
        }
        return true;
    }
}
