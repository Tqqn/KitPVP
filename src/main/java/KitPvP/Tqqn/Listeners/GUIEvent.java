package KitPvP.Tqqn.Listeners;

import KitPvP.Tqqn.Utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIEvent implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();


        if (inv.getName().equals(ChatColor.BLUE + "Kit Selection")) {
            event.setCancelled(true);
            ItemStack itemStack = event.getCurrentItem();
            String is = itemStack.getItemMeta().getDisplayName().toLowerCase();

            player.getInventory().clear();
            Config.giveKit(player, is);
            Config.giveKitArmor(player, is);
            player.teleport(Config.getArenaSpawn());
        } else {
            event.setCancelled(false);
        }
    }
}
