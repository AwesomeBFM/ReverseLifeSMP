package dev.awesomebfm.reverselifesmp;

import dev.awesomebfm.reverselifesmp.commands.*;
import dev.awesomebfm.reverselifesmp.database.Database;
import dev.awesomebfm.reverselifesmp.items.HeartMagnetItem;
import dev.awesomebfm.reverselifesmp.items.ReviveStoneItem;
import dev.awesomebfm.reverselifesmp.listeners.*;
import dev.awesomebfm.reverselifesmp.managers.items.CustomItemManager;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public final class ReverseLifeSMP extends JavaPlugin {
    public HashMap<UUID, Integer> onlinePlayers = new HashMap<>();
    public boolean locked;
    private static ReverseLifeSMP plugin;

    Database database;

    @Override
    public void onEnable() {
        plugin = this;
        locked = false;

        saveDefaultConfig();

        /*String host = getConfig().getString("database.host");
        String port = getConfig().getString("database.port");
        String username = getConfig().getString("database.username");
        String password = getConfig().getString("database.password");
        String db = getConfig().getString("database.database");
        System.out.println(host + " " + port + " " + username + " " + password + " " + db);
        database = new Database(host, port, username, password, db); */

        // Create database
        database = new Database(
                getConfig().getString("database.host"),
                getConfig().getString("database.port"),
                getConfig().getString("database.username"),
                getConfig().getString("database.password"),
                getConfig().getString("database.database"));
        database.connect();
        try {
            database.initialize();
        } catch (SQLException e) {
            locked = true;
            Bukkit.getLogger().severe("Failed to initialize database! Server cannot be joined by players! Make sure data source is configured in config.yml and that the database is running!");
            throw new RuntimeException(e);
        }

        // Setup Custom Item Manager
        CustomItemManager customItemManager = new CustomItemManager();
        customItemManager.registerItem(new HeartMagnetItem());
        customItemManager.registerItem(new ReviveStoneItem());

        // Register Listeners
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        getServer().getPluginManager().registerEvents(new LoginListener(), this);
        getServer().getPluginManager().registerEvents(new CustomItemManager(), this);
        getServer().getPluginManager().registerEvents(new ItemCraftListener(), this);

        // Register Commands
        getCommand("hearts").setExecutor(new HeartsCommand());
        getCommand("resetdata").setExecutor(new ResetDataCommand());
        getCommand("clearhearts").setExecutor(new ClearHeartsCommand());
        getCommand("undeathban").setExecutor(new UnDeathBanCommand());
        getCommand("deathban").setExecutor(new DeathBanCommand());

        // Setup Menu Manager
        MenuManager.setup(getServer(), this);

    }

    @Override
    public void onDisable() {
        database.disconnect();
    }

    public static ReverseLifeSMP getPlugin() {
        return plugin;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Database getDatabase() {
        return database;
    }

    public void addPlayer(UUID uuid, int hearts) {
        onlinePlayers.put(uuid, hearts);
    }

    public int retrievePlayer(UUID uuid) {
        return onlinePlayers.get(uuid);
    }

    public void updatePlayer(UUID uuid, int hearts) {
        onlinePlayers.put(uuid, hearts);
    }

    public void removePlayer(UUID uuid) {
        onlinePlayers.remove(uuid);
    }

    public boolean containsPlayer(UUID uuid) {
        return onlinePlayers.containsKey(uuid);
    }

}
