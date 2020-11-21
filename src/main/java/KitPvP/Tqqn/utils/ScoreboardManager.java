package KitPvP.Tqqn.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardManager {


    public ScoreboardManager() {
    }

    public static void setScoreboard(int kill, int death, Player player) {
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
        kdr.setSuffix(Color.translate("&f" + getRatioSum(kill, death)));
        obj.getScore(ChatColor.BLUE.toString()).setScore(3);

        Team deaths = board.registerNewTeam("deaths");
        deaths.addEntry(ChatColor.BLACK.toString());
        deaths.setPrefix(Color.translate("&bDeaths: "));
        deaths.setSuffix(Color.translate("&f" + death));
        obj.getScore(ChatColor.BLACK.toString()).setScore(4);

        Team kills = board.registerNewTeam("kills");
        kills.addEntry(ChatColor.AQUA.toString());
        kills.setPrefix(Color.translate("&bKills: "));
        kills.setSuffix(Color.translate("&f" + kill));
        obj.getScore(ChatColor.AQUA.toString()).setScore(5);

        blank1.setScore(6);
        stats.setScore(7);

        player.setScoreboard(obj.getScoreboard());
    }

    public static void updateScoreboard(int killamount, int deathamount, Player player) {
        Team kills = player.getScoreboard().getTeam("kills");
        Team kill = kills != null ? kills : player.getScoreboard().registerNewTeam("kills");
        kill.setSuffix(ChatColor.WHITE + Integer.toString(killamount));

        Team deaths = player.getScoreboard().getTeam("deaths");
        Team death = deaths != null ? deaths : player.getScoreboard().registerNewTeam("deaths");
        death.setSuffix(ChatColor.WHITE + Integer.toString(deathamount));

        Team ratio = player.getScoreboard().getTeam("kdr");
        Team rati = ratio != null ? ratio : player.getScoreboard().registerNewTeam("kdr");
        rati.setSuffix(ChatColor.WHITE + getRatioSum(killamount, deathamount));

    }

    public static String getRatioSum(int kill, int death) {
        if (kill == 0 || death == 0) {
            return "0.0";
        }
        return String.format("%.1f", (double) kill / death);
    }
}
