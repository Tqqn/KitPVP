package KitPvP.Tqqn.Listeners;

import KitPvP.Tqqn.Game;
import KitPvP.Tqqn.Utils.Color;
import KitPvP.Tqqn.Utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

import java.sql.Date;

public class onJoinEvent implements Listener {

    private static Game game;

    public onJoinEvent(Game game) {
        onJoinEvent.game = game;


    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setScoreboard(setScoreBoard(player));

        player.teleport(Config.getLobbySpawn());
    }
   @EventHandler
    public void onJoinDB(PlayerJoinEvent event) {
        Player player = event.getPlayer();

       Date date = new Date(System.currentTimeMillis());

        game.data.createPlayer(player);
        game.data.setDate(player.getUniqueId(),date);

    }
    public Scoreboard setScoreBoard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.getObjective("kitpvp") != null ? board.getObjective("kitpvp") : board.registerNewObjective("aaa", "dummy");
        obj.setDisplayName(Color.translate("&c&lKIT-PVP"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        //static scores
        Score blank = obj.getScore(" ");
        Score blank1 = obj.getScore("  ");

        Score stats = obj.getScore(Color.translate("&e&lStats"));


        Score website = obj.getScore(Color.translate("&cplayer.dusdavidgames.nl"));
        website.setScore(1);
        blank.setScore(2);
        //dynamic scores

        Team deaths = board.registerNewTeam("deaths");
        deaths.addEntry(ChatColor.BLACK.toString());
        deaths.setPrefix(Color.translate("&bDeaths: "));
        deaths.setSuffix(Color.translate("&f" + game.data.getDeaths(player.getUniqueId())));
        obj.getScore(ChatColor.BLACK.toString()).setScore(3);

        Team kills = board.registerNewTeam("kills");
        kills.addEntry(ChatColor.AQUA.toString());
        kills.setPrefix(Color.translate("&bKills: "));
        kills.setSuffix(Color.translate("&f" + game.data.getKills(player.getUniqueId())));
        obj.getScore(ChatColor.AQUA.toString()).setScore(4);
        blank1.setScore(5);

        stats.setScore(6);

        return board;
    }

    public static void updateScoreboardKills(Player player) {

        Team kills = player.getScoreboard().getTeam("kills");
        Team kill = kills != null ? kills : player.getScoreboard().registerNewTeam("kills");
        kill.setSuffix(ChatColor.WHITE + Integer.toString(game.data.getKills(player.getUniqueId())));
    }
    public static void updateScoreboardDeaths(Player player) {
        Team deaths = player.getScoreboard().getTeam("deaths");
        Team death = deaths != null ? deaths : player.getScoreboard().registerNewTeam("deaths");
        death.setSuffix(ChatColor.WHITE + Integer.toString(game.data.getDeaths(player.getUniqueId())));

    }


}
