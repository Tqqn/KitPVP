package me.tqqn.kit_pvp.kits;

import me.tqqn.kit_pvp.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public class KitsGUI {

    private Inventory inventory;

    //creates the inventory and adds all the kit gui-display/material to the inventory.

  public KitsGUI() {
      inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Kit Selection");
      Map<String, Kits> kitslist = Config.getKits();
      for (Kits kits : kitslist.values()) {
          inventory.addItem(kits.getItemStack());
      }
  }

    //gets the private inventory
    public Inventory getInv() {
        return inventory;
    }
}
