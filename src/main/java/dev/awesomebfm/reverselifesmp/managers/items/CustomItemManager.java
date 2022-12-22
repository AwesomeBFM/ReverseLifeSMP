package dev.awesomebfm.reverselifesmp.managers.items;

import dev.awesomebfm.reverselifesmp.ReverseLifeSMP;
import dev.awesomebfm.reverselifesmp.items.HeartMagnetItem;
import dev.awesomebfm.reverselifesmp.items.ReviveStoneItem;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class CustomItemManager implements Listener {
    private final ReverseLifeSMP plugin = ReverseLifeSMP.getPlugin();
    ArrayList<CustomItem> items = new ArrayList<>();

    public CustomItemManager() {
        items.add(new ReviveStoneItem());
        items.add(new HeartMagnetItem());
    }

    public void registerItem(CustomItem item) {
        ItemStack stack = new ItemStack(item.getMaterial());
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(item.getName());
        meta.setLore(item.getLore());

        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(plugin, "id"), PersistentDataType.STRING, item.getId());
        stack.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, item.getId()), stack);
        recipe = item.getRecipe(recipe);
        Bukkit.addRecipe(recipe);
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getItem() == null) return;
        if (!e.getItem().hasItemMeta()) return;

        if (!e.getItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "id"), PersistentDataType.STRING)) return;

        ItemStack item = e.getItem();
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        String id = data.get(new NamespacedKey(plugin, "id"), PersistentDataType.STRING);
        for (CustomItem customItem : items) {
            if (customItem.getId().equals(id)) {
                customItem.execute(e);
                return;
            }
        }


    }
}
