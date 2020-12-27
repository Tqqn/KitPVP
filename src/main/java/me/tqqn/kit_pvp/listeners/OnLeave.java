package me.tqqn.kit_pvp.listeners;

import me.tqqn.kit_pvp.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnLeave implements Listener {

    //if player leaves and if player is in the arena-list - remove him
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Game.getInstance().getPlayerInArenaMap().contains(player.getUniqueId())) {
            Game.getInstance().getPlayerInArenaMap().remove(player.getUniqueId());
        }
    }
}
