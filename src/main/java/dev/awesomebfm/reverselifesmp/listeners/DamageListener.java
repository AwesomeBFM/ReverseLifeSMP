package dev.awesomebfm.reverselifesmp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        //e.setCancelled(true);
    }
}
