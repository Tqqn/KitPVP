package KitPvP.Tqqn.kits;

import KitPvP.Tqqn.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class KitsGUI {

    private Inventory inv;

    //creates the inventory and adds all the kit gui-display/material to the inventory.

  public KitsGUI() {
      inv = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Kit Selection");
      HashMap<String, Kits> kitslist = Config.getKits();
      for (Kits kits : kitslist.values()) {
          inv.addItem(kits.getItemStack());
      }
  }

    //gets the private inventory
    public Inventory getInv() {
        return inv;
    }
}
