package me.tqqn.kit_pvp.listeners;

import me.tqqn.kit_pvp.Game;
import me.tqqn.kit_pvp.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GuiListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();


        //checks if inventory is equal to the kit selection menu
        if (inventory.getName().equals(ChatColor.BLUE + "Kit Selection")) {
            //if it is cancel event
            event.setCancelled(true);

            //null check
            if (event.getCurrentItem().getItemMeta() != null) {

                String itemName = event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase();

                //clears the inventory of the player on kit choose
                player.getInventory().clear();
                //gives the kit/items to the player from the config.
                Config.giveKit(player, itemName);
                Config.giveKitArmor(player, itemName);
                //teleport the player to the arena
                player.teleport(Config.getArenaSpawn());

                //checks if player is in arena, if not adds player to arena-list
                if (!Game.getInstance().playerIsArena((player))) {
                    Game.getInstance().addPlayerToArena(player);
                }
            }
        } else {
            event.setCancelled(false);
        }
    }
}
