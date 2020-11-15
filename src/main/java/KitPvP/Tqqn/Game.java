package KitPvP.Tqqn;

import KitPvP.Tqqn.Listeners.ArenaSign;
import KitPvP.Tqqn.Listeners.OnRespawn;
import KitPvP.Tqqn.Listeners.onJoinEvent;
import KitPvP.Tqqn.Utils.Config;
import co.aikar.commands.BukkitMessageFormatter;
import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Game extends JavaPlugin {

    private static Game instance;
    public Config config = new Config(this);
    private static PaperCommandManager manager;
    public static HashMap kits;

    @Override
    public void onEnable() {
        System.out.println("KitPVP Plugin has been enabled! 1.0");

        new Config(this);

        registerCommands();
        registerEvents();

        kits = Config.getKits();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Game getInstance() { return instance; }

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
        pm.registerEvents(new onJoinEvent(),(this));
        pm.registerEvents(new ArenaSign(),(this));
    }
}
