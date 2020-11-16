package KitPvP.Tqqn;

import KitPvP.Tqqn.Utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class KitsGUI {

    private Game game;
    private Inventory inv;

    public KitsGUI(Game game) {
        this.game = game;
    }
   /* public void createDisplay(ItemStack material, Inventory inv, int Slot, String name) {

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);

        inv.setItem(Slot, material);
    }
    public void openGUI(Player player) {
        for (String key : (Config.getSectionKits())) {

            Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Kit Selector");

                int slot = Config.getKitSlot(key);
                String nombre = Config.getKitName(key);

                ItemStack icon = new ItemStack(Material.matchMaterial(Config.getKitIcon(key)));


                createDisplay(icon,inv,slot,nombre);
                player.openInventory(inv);
        }
    }
*/

  public KitsGUI() {
         inv = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Kit Selection");
          HashMap kits = Config.getKits();
      for (Object o : kits.values()) {
          Kits kit = (Kits) o;
          inv.addItem(kit.getItemStack());
      }
    }

    public Inventory getInv() {
        return inv;
    }
}
