package KitPvP.Tqqn.listeners;

import KitPvP.Tqqn.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onLeave implements Listener {

    //if player leaves and if player is in the arena-list - remove him
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Game.getInstance().inarena.contains(player.getUniqueId())) {
            Game.getInstance().inarena.remove(player.getUniqueId());
        }
    }
}
