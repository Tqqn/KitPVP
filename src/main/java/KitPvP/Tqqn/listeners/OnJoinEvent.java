package KitPvP.Tqqn.listeners;

import KitPvP.Tqqn.Game;
import KitPvP.Tqqn.database.DBGetter;
import KitPvP.Tqqn.utils.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoinEvent implements Listener {

    //if Player joins the server this will run

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //sets the scoreboard sync for the joining player, getting the data async

        DBGetter.getInstance().getPlayerDataForJoinScoreboard(player);

        //teleports the player to the lobby spawn on join.
        player.teleport(Config.getLobbySpawn());
    }
   @EventHandler
    public void onJoinDB(PlayerJoinEvent event) {
       Player player = event.getPlayer();

       //creates a new Player in the Database on join.

       Game.getInstance().getData().createPlayer(player);
   }
}
