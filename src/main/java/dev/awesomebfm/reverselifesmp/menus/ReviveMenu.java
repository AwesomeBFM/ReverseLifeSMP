package dev.awesomebfm.reverselifesmp.menus;

import dev.awesomebfm.reverselifesmp.ReverseLifeSMP;
import dev.awesomebfm.reverselifesmp.models.DeadPlayer;
import dev.awesomebfm.reverselifesmp.models.PlayerData;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReviveMenu extends Menu {
    private final ReverseLifeSMP plugin = ReverseLifeSMP.getPlugin();
    public ReviveMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Revive Player";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getItemMeta() == null) return;

        String uuid = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "uuid"), PersistentDataType.STRING);
        if (uuid == null) return;
        try {
            plugin.getDatabase().removeDeathBan(UUID.fromString(uuid));
            PlayerData playerData = new PlayerData(UUID.fromString(uuid), 0, false);
            plugin.getDatabase().updateData(playerData);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        e.getWhoClicked().closeInventory();
        e.getWhoClicked().sendMessage(ChatColor.GREEN + "Player has been revived!");
    }

    @Override
    public void setMenuItems() {
        // Get all players that are death banned
        ArrayList<DeadPlayer> deadPlayers = new ArrayList<>();
        try {
            deadPlayers = plugin.getDatabase().getDeathBans();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for ( DeadPlayer p : deadPlayers ) {
            ItemStack item = p.getHead();
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + p.getName());
            meta.setLore(List.of(
                    ChatColor.GRAY + "UUID: " + p.getUuid().toString(),
                    "",
                    ChatColor.YELLOW + "Click To Revive"
            ));

            PersistentDataContainer data = meta.getPersistentDataContainer();
            data.set(new NamespacedKey(plugin, "uuid"), PersistentDataType.STRING, p.getUuid().toString());

            item.setItemMeta(meta);

            inventory.addItem(item);
        }

    }
}
