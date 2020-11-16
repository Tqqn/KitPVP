package KitPvP.Tqqn.Kits;

import KitPvP.Tqqn.Utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class KitsGUI {

    private Inventory inv;

    //creates the inventory and adds all the kit gui-display/material to the inventory.

  public KitsGUI() {
         inv = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Kit Selection");
          HashMap kits = Config.getKits();
      for (Object o : kits.values()) {
          Kits kit = (Kits) o;
          inv.addItem(kit.getItemStack());
      }
    }

    //gets the private inventory
    public Inventory getInv() {
        return inv;
    }
}
