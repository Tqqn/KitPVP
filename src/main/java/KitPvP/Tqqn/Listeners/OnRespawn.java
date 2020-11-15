package KitPvP.Tqqn.Listeners;

import KitPvP.Tqqn.Utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnRespawn implements Listener {

    @EventHandler
    public void onPlayerDeath(org.bukkit.event.player.PlayerRespawnEvent event) {
        event.setRespawnLocation(Config.getLobbySpawn());
    }
}
