package dev.awesomebfm.reverselifesmp.managers.items;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;

public abstract class CustomItem {
    public abstract String getName();
    public abstract Material getMaterial();
    public abstract List<String> getLore();
    public abstract String getId();
    public abstract ShapedRecipe getRecipe(ShapedRecipe recipe);
    public abstract void execute(PlayerInteractEvent e);

}
