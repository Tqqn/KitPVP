package KitPvP.Tqqn;

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

    public Config config = new Config(this);
    private static PaperCommandManager manager;

    public DataBase database;
    public DBGetter data;

    @Override
    public void onEnable() {
        this.database = new DataBase();
        this.data = new DBGetter(this);

        try {
            database.connect();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database not connected (login info incorrect or this server is not using a database).");
        }

        if (database.isConnected()) {
            System.out.println("Database is connected!");
            data.createTable();
        }

        System.out.println("KitPVP Plugin has been enabled! 1.0");

        registerCommands();
        registerEvents();

    }

    @Override
    public void onDisable() {
        database.disconnect();
    }

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

    public void registerEvents() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new OnRespawn(),(this));
        pm.registerEvents(new onJoinEvent(this),(this));
        pm.registerEvents(new ArenaSign(),(this));
        pm.registerEvents(new GUIEvent(),(this));
        pm.registerEvents(new onDeath(this),(this));
    }
}
