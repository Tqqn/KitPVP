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
            game.data.addKills(killer.getUniqueId(), 1);
            game.data.addDeaths(player.getUniqueId(),1);
            event.setDeathMessage(Color.translate(ChatColor.RED + player.getDisplayName() + " &2has been killed by " + ChatColor.RED + killer.getDisplayName()));
            onJoinEvent.updateScoreboardDeaths(player);
            onJoinEvent.updateScoreboardKills(killer);
        } else {
            event.setDeathMessage(null);
        }

    }
    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event) {
        event.getDrops().clear();
    }

}
