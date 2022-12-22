package dev.awesomebfm.reverselifesmp.commands;

import dev.awesomebfm.reverselifesmp.ReverseLifeSMP;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HeartsCommand implements CommandExecutor {
    private final ReverseLifeSMP plugin = ReverseLifeSMP.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        int hearts = plugin.retrievePlayer(((Player) sender).getUniqueId());
        sender.sendMessage(ChatColor.GREEN + "You have gained " + ChatColor.RED + hearts + ChatColor.GREEN + " hearts." +
                "You can get " + ChatColor.RED + (10 - hearts) + ChatColor.GREEN + " more hearts before a death ban!");
        return true;
    }
}
