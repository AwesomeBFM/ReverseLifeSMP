package dev.awesomebfm.reverselifesmp.listeners;

import dev.awesomebfm.reverselifesmp.ReverseLifeSMP;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class ItemCraftListener implements Listener {

    private final ReverseLifeSMP plugin = ReverseLifeSMP.getPlugin();

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        ItemStack result = e.getRecipe().getResult();
        if (result.getItemMeta() == null) return;
        if (result.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "id"), PersistentDataType.STRING) == null) {
            return;
        }
        result.getItemMeta().getPersistentDataContainer().set(new NamespacedKey(plugin, "uuid"), PersistentDataType.STRING, UUID.randomUUID().toString());
    }

}
