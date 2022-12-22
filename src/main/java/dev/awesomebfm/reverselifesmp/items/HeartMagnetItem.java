package dev.awesomebfm.reverselifesmp.items;

import dev.awesomebfm.reverselifesmp.ReverseLifeSMP;
import dev.awesomebfm.reverselifesmp.managers.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;

public class HeartMagnetItem extends CustomItem {
    private final ReverseLifeSMP plugin = ReverseLifeSMP.getPlugin();
    @Override
    public String getName() {
        return ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Heart Magnet";
    }

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_INGOT;
    }

    @Override
    public List<String> getLore() {
        return List.of(
                "",
                ChatColor.GOLD + "Item Ability: Magnetize " + ChatColor.YELLOW + "" + ChatColor.BOLD + "RIGHT CLICK",
                ChatColor.GRAY + "Consume the magnet and",
                ChatColor.GRAY + "lose 1 extra heart.",
                ChatColor.DARK_GRAY + "Single use item!",
                " ",
                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "EPIC ITEM"
        );
    }

    @Override
    public String getId() {
        return "heart-magnet";
    }

    @Override
    public ShapedRecipe getRecipe(ShapedRecipe recipe) {
        recipe.shape(" T ", "DHD", " A ");
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('H', Material.PLAYER_HEAD);
        recipe.setIngredient('A', Material.ANCIENT_DEBRIS);
        return recipe;
    }

    @Override
    public void execute(PlayerInteractEvent e) {
        int hearts = plugin.retrievePlayer(e.getPlayer().getUniqueId());
        if (hearts <= 0) {
            e.getPlayer().sendMessage(ChatColor.RED + "You don't have any hearts to lose!");
            return;
        }
        hearts--;
        plugin.updatePlayer(e.getPlayer().getUniqueId(), hearts);
        int health = hearts * 2 + 20;
        e.getPlayer().getInventory().removeItem(e.getItem());
        e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        e.getPlayer().sendMessage(ChatColor.GREEN + "You have lost 1 extra heart! You now have " + ChatColor.RED  + hearts + ChatColor.GREEN + " extra hearts.");
    }
}
