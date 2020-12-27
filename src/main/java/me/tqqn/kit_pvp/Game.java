package me.tqqn.kit_pvp;

import co.aikar.commands.BukkitMessageFormatter;
import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import me.tqqn.kit_pvp.commands.Commands;
import me.tqqn.kit_pvp.database.DBGetter;
import me.tqqn.kit_pvp.database.DataBase;
import me.tqqn.kit_pvp.listeners.*;
import me.tqqn.kit_pvp.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class Game extends JavaPlugin {

    private static PaperCommandManager manager;

    private static Game instance;

    public Set<UUID> playerInArena = new HashSet<>();

    private DataBase database;
    private DBGetter dbGetter;

    @Override
    public void onEnable() {
        this.database = new DataBase();
        this.dbGetter = new DBGetter();

        instance = this;

        //try to connect the DataBase
        try {
            database.connect();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database not connected (login info incorrect or this server is not using a database).");

            getServer().getPluginManager().disablePlugin(this);
            System.out.println("Plugin disabled. Database not connected.");
        }

        //if the DataBase is connected throws println to console
        if (database.isConnected()) {
            System.out.println("Database is connected!");
            dbGetter.createTable();
        }

        System.out.println("KitPVP Plugin has been enabled!");

        //Makes config if it doesn't exist.
        doesConfigExist();

        //Register Commands
        registerCommands();

        //Register Events
        registerEvents();

    }

    @Override
    public void onDisable() {
        //Disconnect the DataBase if server/plugin is disabling.
        database.disconnect();

        //clears the data from the inarena arraylist
        playerInArena.clear();
        //teleports all players to lobbyspawn on disable/reload
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(Config.getLobbySpawn());
        }
    }

    public void doesConfigExist() {
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            new Config(this);
        }
    }

    //Registering the Commands + Formats for errors,syntax,info and help
    public void registerCommands() {
        manager = new PaperCommandManager(this);
        manager.enableUnstableAPI("help");

        manager.setFormat(MessageType.ERROR, new BukkitMessageFormatter(ChatColor.RED, ChatColor.YELLOW, ChatColor.RED));
        manager.setFormat(MessageType.SYNTAX, new BukkitMessageFormatter(ChatColor.DARK_GREEN, ChatColor.GREEN, ChatColor.WHITE));
        manager.setFormat(MessageType.INFO, new BukkitMessageFormatter(ChatColor.DARK_GREEN, ChatColor.GREEN, ChatColor.WHITE));
        manager.setFormat(MessageType.HELP, new BukkitMessageFormatter(ChatColor.DARK_GREEN, ChatColor.GREEN, ChatColor.WHITE));

        manager.registerCommand(new Commands(this));

        manager.setDefaultExceptionHandler((command, registeredCommand, sender, args, t) -> {
            getLogger().warning("Error occurred with command " + command.getName());
            return false;
        });
    }

    //Registering the Events
    public void registerEvents() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new OnRespawn(), (this));
        pm.registerEvents(new OnJoin(), (this));
        pm.registerEvents(new SignListener(), (this));
        pm.registerEvents(new GUIListener(), (this));
        pm.registerEvents(new OnDeath(), (this));
        pm.registerEvents(new OnLeave(), (this));
    }

    //checks if the player is in the arena-list
    public boolean playerIsArena(Player player) {
        if (playerInArena.contains(player.getUniqueId())) {
            return true;
        } else {
            return false;
        }
    }

    //adds the player to the arena-list
    public void addPlayerToArena(Player player) {
        playerInArena.add(player.getUniqueId());
    }


    //instance for the DataBase
    public DataBase getDataBase() {
        return database;
    }

    //instance for the DBGetter
    public DBGetter getDBGetter() {
        return dbGetter;
    }

    //instance for the main class (Game)
    public static Game getInstance() {
        return instance;
    }
}