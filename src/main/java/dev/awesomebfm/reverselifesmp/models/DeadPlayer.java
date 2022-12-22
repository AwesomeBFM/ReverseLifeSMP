package dev.awesomebfm.reverselifesmp.models;

import me.kodysimpson.simpapi.heads.SkullCreator;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class DeadPlayer {
    UUID uuid;
    String name;
    ItemStack head;

    public DeadPlayer(UUID uuid, String name, ItemStack head) {
        this.uuid = uuid;
        this.name = name;
        this.head = head;
    }

    public DeadPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.head = SkullCreator.itemFromUuid(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getHead() {
        return head;
    }

    public void setHead(ItemStack head) {
        this.head = head;
    }
}
