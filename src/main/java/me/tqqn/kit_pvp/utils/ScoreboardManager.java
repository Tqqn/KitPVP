package me.tqqn.kit_pvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardManager {


    public ScoreboardManager() {
    }

    //Sets the scoreboard sync, getting the data async

    public static void setScoreboard(int kill, int death, Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.getObjective("kitpvp") != null ? scoreboard.getObjective("kitpvp") : scoreboard.registerNewObjective("aaa", "dummy");
        objective.setDisplayName(Color.translate("&c&lKIT-PVP"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        //static scores
        Score blank = objective.getScore(" ");
        Score blank1 = objective.getScore("  ");

        Score stats = objective.getScore(Color.translate("&e&lStats"));


        Score website = objective.getScore(Color.translate("&cplayer.dusdavidgames.nl"));
        website.setScore(1);
        blank.setScore(2);


        //dynamic scores

        Team killdeathratio = scoreboard.registerNewTeam("kdr");
        killdeathratio.addEntry(ChatColor.BLUE.toString());
        killdeathratio.setPrefix(Color.translate("&bK/D Ratio: "));
        killdeathratio.setSuffix(Color.translate("&f" + getRatioSum(kill, death)));
        objective.getScore(ChatColor.BLUE.toString()).setScore(3);

        Team deaths = scoreboard.registerNewTeam("deaths");
        deaths.addEntry(ChatColor.BLACK.toString());
        deaths.setPrefix(Color.translate("&bDeaths: "));
        deaths.setSuffix(Color.translate("&f" + death));
        objective.getScore(ChatColor.BLACK.toString()).setScore(4);

        Team kills = scoreboard.registerNewTeam("kills");
        kills.addEntry(ChatColor.AQUA.toString());
        kills.setPrefix(Color.translate("&bKills: "));
        kills.setSuffix(Color.translate("&f" + kill));
        objective.getScore(ChatColor.AQUA.toString()).setScore(5);

        blank1.setScore(6);
        stats.setScore(7);

        player.setScoreboard(objective.getScoreboard());
    }

    // Updating scoreboard sync, getting the data async

    public static void updateScoreboard(int killamount, int deathamount, Player player) {
        Team kills = player.getScoreboard().getTeam("kills");
        Team newkills = kills != null ? kills : player.getScoreboard().registerNewTeam("kills");
        newkills.setSuffix(ChatColor.WHITE + Integer.toString(killamount));

        Team deaths = player.getScoreboard().getTeam("deaths");
        Team newdeaths = deaths != null ? deaths : player.getScoreboard().registerNewTeam("deaths");
        newdeaths.setSuffix(ChatColor.WHITE + Integer.toString(deathamount));

        Team killdeathratio = player.getScoreboard().getTeam("kdr");
        Team newkilldeathratio = killdeathratio != null ? killdeathratio : player.getScoreboard().registerNewTeam("kdr");
        newkilldeathratio.setSuffix(ChatColor.WHITE + getRatioSum(killamount, deathamount));

    }

    // Maths the k/d-ratio sum
    public static String getRatioSum(int kill, int death) {
        if (kill == 0 || death == 0) {
            return "0.0";
        }
        return String.format("%.1f", (double) kill / death);
    }
}
