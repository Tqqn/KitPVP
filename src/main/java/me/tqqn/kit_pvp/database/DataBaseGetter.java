package me.tqqn.kit_pvp.database;

import me.tqqn.kit_pvp.Game;
import me.tqqn.kit_pvp.utils.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DataBaseGetter {

    //Creating a table if not exists with the UUID, KILLS and DEATHS

    public void createTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = Game.getInstance().getDataBase().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS player_info "
                    + "(UUID VARCHAR(100),KILLS INT(100),DEATHS INT(100),PRIMARY KEY (UUID))");
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    //Creating a new player if it not exist

    public void createPlayer(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            if (!exists(uuid)) {
                PreparedStatement preparedStatement = Game.getInstance().getDataBase().getConnection().prepareStatement("INSERT IGNORE INTO player_info" +
                        " (UUID) VALUES (?)");
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.executeUpdate();
                preparedStatement.close();

                //sets the users kills/deaths on 0, not NULL.
                PreparedStatement setDeathsKillsTo0 = Game.getInstance().getDataBase().getConnection().prepareStatement("UPDATE player_info SET KILLS=0, DEATHS=0 WHERE UUID=?");
                setDeathsKillsTo0.setString(1, uuid.toString());
                setDeathsKillsTo0.executeUpdate();
                setDeathsKillsTo0.close();
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    //boolean to check is the uuid exists
    public boolean exists(UUID uuid) {
        try {
            PreparedStatement preparedStatement = Game.getInstance().getDataBase().getConnection().prepareStatement("SELECT * FROM player_info WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) {
                // player is found
                return true;
            }
            close(results,preparedStatement);
            return false;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    //Adding 1 kill to the DataBase to the desired Player
    public void addKill(UUID uuid) {
        try {
            PreparedStatement preparedStatement = Game.getInstance().getDataBase().getConnection().prepareStatement("UPDATE player_info SET KILLS=KILLS+1 WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    //Adding 1 death to the DataBase to the desired Player
    public void addDeath(UUID uuid) {
        try {
            PreparedStatement preparedStatement = Game.getInstance().getDataBase().getConnection().prepareStatement("UPDATE player_info SET DEATHS=DEATHS+1 WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    //closes the resultset/preparedstatement

    public void close(ResultSet resultSet, PreparedStatement preparedStatement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    //getting the playerdata(stats) ASYNC for the updating scoreboard and giving the data to the updatescoreboard method

    public void getStatsData(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Game.getInstance(), new Runnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement preparedStatement = Game.getInstance().getDataBase().getConnection().prepareStatement("SELECT KILLS, DEATHS FROM player_info WHERE UUID=?");
                    preparedStatement.setString(1, player.getUniqueId().toString());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        final int kills = resultSet.getInt("KILLS");
                        final int deaths = resultSet.getInt("DEATHS");
                        Bukkit.getScheduler().runTask(Game.getInstance(), () -> ScoreboardManager.updateScoreboard(kills, deaths, player));
                    }
                    //closes the resultset/preparedstatement
                    close(resultSet, preparedStatement);

                } catch (SQLException exception) {
                    exception.printStackTrace();
                }


            }
        });
    }

    //getting the playerdata(stats) ASYNC for the join-scoreboard and giving the data to the setscoreboard method after 1 second it's completed

    public void getPlayerDataForJoinScoreboard(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Game.getInstance(), new Runnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement preparedStatement = Game.getInstance().getDataBase().getConnection().prepareStatement("SELECT KILLS, DEATHS FROM player_info WHERE UUID=?");
                    preparedStatement.setString(1, player.getUniqueId().toString());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        final int kills = resultSet.getInt("KILLS");
                        final int deaths = resultSet.getInt("DEATHS");
                        Bukkit.getScheduler().runTaskLater(Game.getInstance(), () -> ScoreboardManager.setScoreboard(kills, deaths, player), 20L);
                    }
                    //closes the resultset/preparedstatement
                    close(resultSet, preparedStatement);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}
