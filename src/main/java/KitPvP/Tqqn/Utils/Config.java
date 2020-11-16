package KitPvP.Tqqn.Utils;

import KitPvP.Tqqn.Game;
import KitPvP.Tqqn.Kits;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Config {

    private static Game game;

    public Config(Game game) {
        Config.game = game;


    }

    public static HashMap getKits() {
        HashMap<String, Kits> foundKits = new HashMap<>();
        for (String kit : game.getConfig().getConfigurationSection("kits.").getKeys(false)) {
            Kits foundKit = new Kits(getKitName(kit), getKitDisplay(kit), Material.getMaterial(getKitGuiMaterial(kit)));
            String name = foundKit.getName();
            foundKits.put(name, foundKit);
        }
        return foundKits;
    }

    public static Inventory getKitItems(String path, Inventory inv) {
        for (String items : game.getConfig().getStringList("kits." + path + ".items")) {
            String[] split = items.split(";");
            Material material = Material.getMaterial(split[0]);
            int amount = Integer.parseInt(split[1]);

            ItemStack kit = new ItemStack(material,amount);
            inv.addItem(kit);
        }
        return inv;
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

     public static Set<String> getSection() {
        return game.getConfig().getConfigurationSection("kits").getKeys(false);
     }
    public static String getKitDisplay(String kit) {
        return game.getConfig().getString(Color.translate("kits." + kit + ".inventory.gui-display"));
    }

    public static String getKitGuiMaterial(String kit) {
        return game.getConfig().getString("kits." + kit + ".inventory.gui-material");
    }

    public static String getKitName(String kit) {
        return game.getConfig().getString("kits." + kit + ".inventory.name");
    }
    public static List<String> getStringList(String path) {
        return game.getConfig().getStringList("kits." + path + ".items");
    }
    public static String getStringArmor(String path,String armor) {
        return game.getConfig().getString("kits." + path + armor);
    }
    public static String getString(String path) {
        return game.getConfig().getString(path);
    }

    public static void giveKit(Player player,String key) {
        for (String items : game.getConfig().getStringList("kits." + key + ".items")) {
            String[] split = items.split(";");
            Material mat = Material.getMaterial(split[0]);
            int amount = Integer.parseInt(split[1]);

            ItemStack kit = new ItemStack(mat,amount);

            player.getInventory().addItem(kit);
            player.closeInventory();
        }
    }
    public static void giveKitArmor(Player player, String key) {
        String helmet = game.getConfig().getString("kits." + key + ".armor.helmet").toUpperCase();
        String chestplate = game.getConfig().getString("kits." + key + ".armor.chestplate").toUpperCase();
        String leggings = game.getConfig().getString("kits." + key + ".armor.leggings").toUpperCase();
        String boots = game.getConfig().getString("kits." + key + ".armor.boots").toUpperCase();

        player.getInventory().setHelmet(new ItemStack(helmet != null ? Material.matchMaterial(helmet) : Material.AIR));
        player.getInventory().setChestplate(new ItemStack(chestplate != null ? Material.matchMaterial(chestplate) : Material.AIR));
        player.getInventory().setLeggings(new ItemStack(leggings != null ? Material.matchMaterial(leggings) : Material.AIR));
        player.getInventory().setBoots(new ItemStack(boots != null ? Material.matchMaterial(boots) : Material.AIR));
        player.updateInventory();
    }
    public static String getHost() {
        return game.getConfig().getString("database.host");
    }
    public static String getPort() {
        return game.getConfig().getString("database.port");
    }
    public static String getDBname() {
        return game.getConfig().getString("database.DBname");
    }
    public static String getUsername() {
        return game.getConfig().getString("database.username");
    }
    public static String getPassword() {
        return game.getConfig().getString("database.password");
    }


}
