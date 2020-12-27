package me.tqqn.kit_pvp.listeners;

import me.tqqn.kit_pvp.Game;
import me.tqqn.kit_pvp.utils.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    //if Player joins the server this will run

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //sets the scoreboard sync for the joining player, getting the data async

        Game.getInstance().getDBGetter().getPlayerDataForJoinScoreboard(player);

        //teleports the player to the lobby spawn on join.
        player.teleport(Config.getLobbySpawn());
    }
   @EventHandler
    public void onJoinDB(PlayerJoinEvent event) {
       Player player = event.getPlayer();

       //creates a new Player in the Database on join.

       Game.getInstance().getDBGetter().createPlayer(player);
   }
}
