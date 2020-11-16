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

public class onJoinEvent implements Listener {

    private static Game game;

    public onJoinEvent(Game game) {
        onJoinEvent.game = game;
    }

    //if Player joins the server this will run

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //sets the scoreboard for the player that joins.
        player.setScoreboard(setScoreBoard(player));

        //teleports the player to the lobby spawn on join.
        player.teleport(Config.getLobbySpawn());
    }
   @EventHandler
    public void onJoinDB(PlayerJoinEvent event) {
        Player player = event.getPlayer();

       //creates a new Player in the Database on join.

        game.data.createPlayer(player);

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

        Team kdr = board.registerNewTeam("kdr");
        kdr.addEntry(ChatColor.BLUE.toString());
        kdr.setPrefix(Color.translate("&bK/D Ratio: "));
        kdr.setSuffix(Color.translate("&f" + game.data.getRatio(player.getUniqueId())));
        obj.getScore(ChatColor.BLUE.toString()).setScore(3);

        Team deaths = board.registerNewTeam("deaths");
        deaths.addEntry(ChatColor.BLACK.toString());
        deaths.setPrefix(Color.translate("&bDeaths: "));
        deaths.setSuffix(Color.translate("&f" + game.data.getDeaths(player.getUniqueId())));
        obj.getScore(ChatColor.BLACK.toString()).setScore(4);

        Team kills = board.registerNewTeam("kills");
        kills.addEntry(ChatColor.AQUA.toString());
        kills.setPrefix(Color.translate("&bKills: "));
        kills.setSuffix(Color.translate("&f" + game.data.getKills(player.getUniqueId())));
        obj.getScore(ChatColor.AQUA.toString()).setScore(5);



        blank1.setScore(6);
        stats.setScore(7);

        return board;
    }

    //Updates the scoreboard kills
    public static void updateScoreboardKills(Player player) {

        Team kills = player.getScoreboard().getTeam("kills");
        Team kill = kills != null ? kills : player.getScoreboard().registerNewTeam("kills");
        kill.setSuffix(ChatColor.WHITE + Integer.toString(game.data.getKills(player.getUniqueId())));
    }
    //Updates the scoreboard deaths
    public static void updateScoreboardDeaths(Player player) {
        Team deaths = player.getScoreboard().getTeam("deaths");
        Team death = deaths != null ? deaths : player.getScoreboard().registerNewTeam("deaths");
        death.setSuffix(ChatColor.WHITE + Integer.toString(game.data.getDeaths(player.getUniqueId())));

    }
    //Updates the scoreboard ratio
    public static void updateScoreboardRatio(Player player) {
        Team ratio = player.getScoreboard().getTeam("kdr");
        Team rati = ratio != null ? ratio : player.getScoreboard().registerNewTeam("kdr");
        rati.setSuffix(ChatColor.WHITE + game.data.getRatio(player.getUniqueId()));
    }
}
