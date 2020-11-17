package KitPvP.Tqqn.Listeners;

import KitPvP.Tqqn.Game;
import KitPvP.Tqqn.Utils.Color;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onDeath implements Listener {

    private static Game game;

    public onDeath(Game game) {
        onDeath.game = game;
    }



    @EventHandler
    public void onPlayerDeathByPlayer(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.getKiller() instanceof Player) {
            Player killer = player.getKiller();

            //add a kill to the DB for the killer
            game.data.addKills(killer.getUniqueId(), 1);
            //add a death to the DB for the killer
            game.data.addDeaths(player.getUniqueId(), 1);

            if (game.inarena.contains(player.getUniqueId())) {
                game.inarena.remove(player.getUniqueId());
            }


            //sets a custom deathmessage
            event.setDeathMessage(Color.translate(ChatColor.RED + player.getDisplayName() + " &2has been killed by " + ChatColor.RED + killer.getDisplayName()));

            //updates the scoreboard for both players.
            onJoinEvent.updateScoreboardDeaths(player);
            onJoinEvent.updateScoreboardRatio(player);

            onJoinEvent.updateScoreboardKills(killer);
            onJoinEvent.updateScoreboardRatio(killer);
        } else {
            //Removes vanilla deathmessages from the server.
            event.setDeathMessage(null);
        }

    }
    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            //if the player that died is in the arena, remove him from arenalist
            if (game.inarena.contains(player.getUniqueId())) {
                game.inarena.remove(player.getUniqueId());
            }

            //if player dies the stuff he drops will despawn/get removed.
            event.getDrops().clear();
        }
    }

}
