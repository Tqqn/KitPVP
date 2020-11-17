package KitPvP.Tqqn.Listeners;

import KitPvP.Tqqn.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onLeave implements Listener {

    private static Game game;

    public onLeave(Game game) {
        onLeave.game = game;
    }

    //if player leaves and if player is in the arena-list - remove him
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (game.inarena.contains(player.getUniqueId())) {
            game.inarena.remove(player.getUniqueId());
        }
    }
}
