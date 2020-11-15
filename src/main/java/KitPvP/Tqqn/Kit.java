package KitPvP.Tqqn;

import KitPvP.Tqqn.Utils.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Kit {
    private Game game;

    public Kit(Game game) {
        this.game = game;
    }

    public void createKit(Player player, String kitName) {
        PlayerInventory inv = player.getInventory();
        if (game.getConfig().getConfigurationSection("kits." + kitName) != null) {
            player.sendMessage(Color.translate("&3This kit already exists!"));
        }
        String path = "kits." + kitName + ".";
        game.getConfig().createSection("kits." + kitName);

        for (int i = 0; i < 36; i++) {
            ItemStack is = inv.getItem(i);

            if (is.getType() == Material.AIR)
                continue;
            String slot = path + "items." + i;

            game.getConfig().set(slot + ".type", is.getType().toString().toLowerCase());
            game.getConfig().set(slot + ".amount", is.getAmount());

            if (!is.hasItemMeta())
                continue;
            if (is.getItemMeta().hasDisplayName())
                game.getConfig().set(slot + ".name", is.getItemMeta().getDisplayName());
            if (is.getItemMeta().hasLore())
                game.getConfig().set(slot + ".lore", is.getItemMeta().getLore());

            if (is.getItemMeta().hasEnchants()) {
                Map<Enchantment, Integer> enchants = is.getEnchantments();
                List<String> enchantList = new ArrayList<String>();
                for (Enchantment e : is.getEnchantments().keySet()) {
                    int level = enchants.get(e);
                    enchantList.add(e.getName().toLowerCase() + ":" + level);
                }
                game.getConfig().set(slot + ".enchants", enchantList);
            }
        }

        game.getConfig().set(path + "armor.helmet", inv.getHelmet() != null ? inv.getHelmet().getType().toString().toLowerCase() : "air");

        game.getConfig().set(path + "armor.chestplate", inv.getChestplate() != null ? inv.getChestplate().getType().toString().toLowerCase() : "air");

        game.getConfig().set(path + "armor.leggings", inv.getLeggings() != null ? inv.getLeggings().getType().toString().toLowerCase() : "air");

        game.getConfig().set(path + "armor.boots", inv.getBoots() != null ? inv.getBoots().getType().toString().toLowerCase() : "air");
        game.saveConfig();
    }

    public void giveKit(Player player, String kitName) {
        FileConfiguration  config = game.getConfig();

        if (config.getConfigurationSection("kits." + kitName) == null) {
            return;
        }
        player.getInventory().clear();
        String path = "kits." + kitName + ".";

        ConfigurationSection section = config.getConfigurationSection(path + "items");

        for (String str : section.getKeys(false)) {
            int slot = Integer.parseInt(str);

            if (0 > slot && slot > 36)
                return;

            String string = path + "items." + slot + ".";
            String type = config.getString(string + "type");
            String name = config.getString(string + "name");
            List<String> lore = config.getStringList(string + "lore");
            List<String> enchants = config.getStringList(string + "enchants");
            int amount = config.getInt(string + "amount");

            ItemStack itemstack = new ItemStack(Material.matchMaterial(type.toUpperCase()), amount);
            ItemMeta itemmeta = itemstack.getItemMeta();

            if (itemmeta == null)
                continue;
            if (name != null)
                itemmeta.setDisplayName(Color.translate(name));
            if (lore != null)
                itemmeta.setLore(Arrays.asList(Color.translate(lore.toString())));
            if (enchants != null) {
                for (String s1 : enchants) {
                    String[] indiEnchants = s1.split(":");
                    itemmeta.addEnchant(Enchantment.getByName(indiEnchants[0].toUpperCase()), Integer.parseInt(indiEnchants[1]),true);
                }
            }
            itemstack.setItemMeta(itemmeta);
            player.getInventory().setItem(slot,itemstack);
        }
        String helmet = config.getString(path + "armor.helmet").toUpperCase();
        String chestplate = config.getString(path + "armor.chestplate").toUpperCase();
        String leggings = config.getString(path + "armor.leggings").toUpperCase();
        String boots = config.getString(path + "armor.boots").toUpperCase();

        player.getInventory().setHelmet(new ItemStack(helmet != null ? Material.matchMaterial(helmet) : Material.AIR));
        player.getInventory().setChestplate(new ItemStack(chestplate != null ? Material.matchMaterial(chestplate) : Material.AIR));
        player.getInventory().setLeggings(new ItemStack(leggings != null ? Material.matchMaterial(leggings) : Material.AIR));
        player.getInventory().setBoots(new ItemStack(boots != null ? Material.matchMaterial(boots) : Material.AIR));

        player.updateInventory();
    }


}
