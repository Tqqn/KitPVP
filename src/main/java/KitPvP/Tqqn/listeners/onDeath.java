package KitPvP.Tqqn.listeners;

import KitPvP.Tqqn.Game;
import KitPvP.Tqqn.database.DBGetter;
import KitPvP.Tqqn.utils.Color;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onDeath implements Listener {

    @EventHandler
    public void onPlayerDeathByPlayer(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.getKiller() instanceof Player) {
            Player killer = player.getKiller();

            //updates the stats (DB) for the player and killer

            DBGetter.getInstance().getStatsData(player);
            DBGetter.getInstance().getStatsData(killer);

            //adding death/kill (DB) for the player and killer
            DBGetter.getInstance().addDeath(player.getUniqueId());
            DBGetter.getInstance().addKill(killer.getUniqueId());

            //remove player when he died from the playerinarena list

            if (Game.getInstance().playerInArena.contains(player.getUniqueId())) {
                Game.getInstance().playerInArena.remove(player.getUniqueId());
            }

            //sets a custom deathmessage
            event.setDeathMessage(Color.translate(ChatColor.RED + player.getDisplayName() + " &2has been killed by " + ChatColor.RED + killer.getDisplayName()));

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
            if (Game.getInstance().playerInArena.contains(player.getUniqueId())) {
                Game.getInstance().playerInArena.remove(player.getUniqueId());
            }

            //if player dies the stuff he drops will despawn/get removed.
            event.getDrops().clear();
        }
    }

}
