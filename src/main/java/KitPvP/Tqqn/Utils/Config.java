package KitPvP.Tqqn.Utils;

import KitPvP.Tqqn.Game;
import KitPvP.Tqqn.Kits;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Config {

    private static Game game;

    public Config(Game game) {
        Config.game = game;

        game.getConfig().options().copyDefaults();
        game.saveDefaultConfig();
    }

    public static HashMap getKits() {
        HashMap<String, Kits> foundKits = new HashMap<>();
        for (String kit : game.getConfig().getConfigurationSection("kits.").getKeys(true)) {
            Kits foundKit = new Kits(getKitName(kit), getKitDisplay(kit), getKitGuiMaterial(kit));
            String name = foundKit.getName();
            foundKits.put(name, foundKit);
        }
        return foundKits;
    }

    public static void saveKit(Kits kit) {
        game.getConfig().set("kits." + kit + ".name", kit.getName());
        game.getConfig().set("kits." + kit + ".gui-display", kit.getDisplay());
        game.getConfig().set("kits." + kit + ".gui-material", kit.getMaterial());
    }


    public static Location getLobbySpawn() {
        return new Location(
                Bukkit.getWorld(game.getConfig().getString("lobby-spawn.world")),
                game.getConfig().getDouble("lobby-spawn.x"),
                game.getConfig().getDouble("lobby-spawn.y"),
                game.getConfig().getDouble("lobby-spawn.z"),
                game.getConfig().getInt("lobby-spawn.yaw"),
                game.getConfig().getInt("lobby-spawn.pitch"));
    }

    public static Location getArenaSpawn() {
        return new Location(
                Bukkit.getWorld(game.getConfig().getString("arena-spawn.world")),
                game.getConfig().getDouble("arena-spawn.x"),
                game.getConfig().getDouble("arena-spawn.y"),
                game.getConfig().getDouble("arena-spawn.z"),
                game.getConfig().getInt("arena-spawn.yaw"),
                game.getConfig().getInt("arena-spawn.pitch"));
    }

    public static void setLobbySpawn(Player player) {
        game.getConfig().set("lobby-spawn.world", player.getWorld().getName());
        game.getConfig().set("lobby-spawn.x", player.getLocation().getX());
        game.getConfig().set("lobby-spawn.y", player.getLocation().getY());
        game.getConfig().set("lobby-spawn.z", player.getLocation().getZ());
        game.getConfig().set("lobby-spawn.yaw", player.getLocation().getYaw());
        game.getConfig().set("lobby-spawn.pitch", player.getLocation().getPitch());
        game.saveConfig();
    }

    public static void setArenaSpawn(Player player) {
        game.getConfig().set("arena-spawn.world", player.getWorld().getName());
        game.getConfig().set("arena-spawn.x", player.getLocation().getX());
        game.getConfig().set("arena-spawn.y", player.getLocation().getY());
        game.getConfig().set("arena-spawn.z", player.getLocation().getZ());
        game.getConfig().set("arena-spawn.yaw", player.getLocation().getYaw());
        game.getConfig().set("arena-spawn.pitch", player.getLocation().getPitch());
        game.saveConfig();
    }

    public static String getSignLine0() {
        return game.getConfig().getString("arenasign.line1");
    }

    public static String getSignLine1() {
        return game.getConfig().getString("arenasign.line2");
    }

    public static String getSignLine2() {
        return game.getConfig().getString("arenasign.line3");
    }

    public static String getSignLine3() {
        return game.getConfig().getString("arenasign.line4");
    }

    public static String getSignKey() {
        return game.getConfig().getString("arenasign.key");
    }

    //  public static ConfigurationSection getSection(String path) {
    //      return game.getConfig().getcon
    //  }
    public static String getKitDisplay(String kit) {
        return game.getConfig().getString(Color.translate("kits." + kit + "inventory.gui-display"));
    }

    public static Material getKitGuiMaterial(String kit) {
        return Material.getMaterial(game.getConfig().getString("kits." + kit + "inventory.gui-material"));
    }

    public static String getKitName(String kit) {
        return game.getConfig().getString("kits." + kit + "inventory.name");
    }
}
