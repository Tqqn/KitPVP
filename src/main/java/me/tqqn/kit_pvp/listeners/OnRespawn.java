package KitPvP.Tqqn.listeners;

import KitPvP.Tqqn.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnRespawn implements Listener {

    //if a player dies for whatever reason the will respawn at the lobbyspawn stated as in the config.

    @EventHandler
    public void onPlayerDeath(org.bukkit.event.player.PlayerRespawnEvent event) {
        event.setRespawnLocation(Config.getLobbySpawn());
    }
}
