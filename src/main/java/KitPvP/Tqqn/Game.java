package KitPvP.Tqqn;

import KitPvP.Tqqn.Commands.Commands;
import KitPvP.Tqqn.DB.DBGetter;
import KitPvP.Tqqn.DB.DataBase;
import KitPvP.Tqqn.Listeners.*;
import KitPvP.Tqqn.Utils.Config;
import co.aikar.commands.BukkitMessageFormatter;
import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Game extends JavaPlugin {

    private static PaperCommandManager manager;

    public Config config = new Config(this);

    public DataBase database;
    public DBGetter data;

    @Override
    public void onEnable() {
        this.database = new DataBase();
        this.data = new DBGetter(this);

        new Config(this);

        //try to connect the DataBase
        try {
            database.connect();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database not connected (login info incorrect or this server is not using a database).");
        }

        //if the DataBase is connected throws println to console
        if (database.isConnected()) {
            System.out.println("Database is connected!");
            data.createTable();
        }

        System.out.println("KitPVP Plugin has been enabled! 1.0");

        //Register Commands
        registerCommands();

        //Register Events
        registerEvents();

    }

    @Override
    public void onDisable() {
        //Disconnect the DataBase if server/plugin is disabling.
        database.disconnect();
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
        pm.registerEvents(new onRespawn(),(this));
        pm.registerEvents(new onJoinEvent(this),(this));
        pm.registerEvents(new signListener(),(this));
        pm.registerEvents(new GUIListener(),(this));
        pm.registerEvents(new onDeath(this),(this));
    }
}
