package KitPvP.Tqqn.Listeners;

import KitPvP.Tqqn.Game;
import KitPvP.Tqqn.Utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GUIListener implements Listener {

    private static Game game;

    public GUIListener(Game game) {
        GUIListener.game = game;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();


        //checks if inventory is equal to the kit selection menu
        if (inv.getName().equals(ChatColor.BLUE + "Kit Selection")) {
            //if it is cancel event
            event.setCancelled(true);

            //null check
            if (event.getCurrentItem().getItemMeta() != null) {

                String is = event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase();

                //clears the inventory of the player on kit choose
                player.getInventory().clear();
                //gives the kit/items to the player from the config.
                Config.giveKit(player, is);
                Config.giveKitArmor(player, is);
                //teleport the player to the arena
                player.teleport(Config.getArenaSpawn());

                //checks if player is in arena, if not adds player to arena-list
                if (!game.isArena(player)) {
                    game.addArena(player);
                }
            }
        } else {
            event.setCancelled(false);
        }
    }
}
