package dev.awesomebfm.reverselifesmp.items;

import dev.awesomebfm.reverselifesmp.managers.items.CustomItem;
import dev.awesomebfm.reverselifesmp.menus.ReviveMenu;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;

public class ReviveStoneItem extends CustomItem {
    @Override
    public String getName() {
        return ChatColor.GOLD + "" + ChatColor.BOLD + "Revive Stone";
    }

    @Override
    public Material getMaterial() {
        return Material.COAL;
    }

    @Override
    public List<String> getLore() {
        return List.of(
                "",
                ChatColor.GOLD + "Item Ability: Revive " + ChatColor.YELLOW + "" + ChatColor.BOLD + "RIGHT CLICK",
                ChatColor.GRAY + "Revive a death banned",
                ChatColor.GRAY + "player.",
                ChatColor.DARK_GRAY + "Single use item!",
                " ",
                ChatColor.GOLD + "" + ChatColor.BOLD + "LEGENDARY ITEM"
        );
    }

    @Override
    public String getId() {
        return "revive-stone";
    }

    @Override
    public ShapedRecipe getRecipe(ShapedRecipe recipe) {
        recipe.shape("APA", "DED", "APA");
        recipe.setIngredient('A', Material.ANCIENT_DEBRIS);
        recipe.setIngredient('P', Material.PLAYER_HEAD);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('E', Material.ELYTRA);
        return recipe;
    }

    @Override
    public void execute(PlayerInteractEvent e) {
        e.getPlayer().getInventory().removeItem(e.getItem());
        try {
            MenuManager.openMenu(ReviveMenu.class, e.getPlayer());
        } catch (MenuManagerException | MenuManagerNotSetupException ex) {
            throw new RuntimeException(ex);
        }
    }
}
