package KitPvP.Tqqn.listeners;

import KitPvP.Tqqn.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnLeave implements Listener {

    //if player leaves and if player is in the arena-list - remove him
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Game.getInstance().playerInArena.contains(player.getUniqueId())) {
            Game.getInstance().playerInArena.remove(player.getUniqueId());
        }
    }
}
