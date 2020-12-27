package me.tqqn.kit_pvp.listeners;

import me.tqqn.kit_pvp.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class OnRespawn implements Listener {

    //if a player dies for whatever reason the will respawn at the lobbyspawn stated as in the config.

    @EventHandler
    public void onPlayerDeath(PlayerRespawnEvent event) {
        event.setRespawnLocation(Config.getLobbySpawn());
    }
}
