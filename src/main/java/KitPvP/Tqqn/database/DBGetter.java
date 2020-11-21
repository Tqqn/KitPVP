package KitPvP.Tqqn.database;

import KitPvP.Tqqn.Game;
import KitPvP.Tqqn.utils.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DBGetter {

    private static DBGetter instance;

    public DBGetter(Game game) {
        instance = this;
    }


    //Creating a table if not exists with the UUID, KILLS and DEATHS

    public void createTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = Game.getInstance().getDataBase().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS player_info "
                    + "(UUID VARCHAR(100),KILLS INT(100),DEATHS INT(100),PRIMARY KEY (UUID))");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Adding kills to the DataBase to the desired Player
    public void addKill(UUID uuid) {
        try {
            PreparedStatement preparedStatement = Game.getInstance().getDataBase().getConnection().prepareStatement("UPDATE player_info SET KILLS=KILLS+1 WHERE UUID=?");
            //  preparedStatement.setInt(1,(getKills(uuid) + kills));
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Adding deaths to the DataBase to the desired Player
    public void addDeath(UUID uuid) {
        try {
            PreparedStatement preparedStatement = Game.getInstance().getDataBase().getConnection().prepareStatement("UPDATE player_info SET DEATHS=DEATHS+1 WHERE UUID=?");
            //  preparedStatement.setInt(1,(getKills(uuid) + deaths));
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet resultSet, PreparedStatement preparedStatement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


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
                    close(resultSet, preparedStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        });
    }

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
                    close(resultSet, preparedStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static DBGetter getInstance() {
        return instance;
    }

}
    /*

    Speler joint
    1. Haal data uit database
    2. Data opgehaald druk het in geheugen (cache)

    Speler maakt kill:
    1. Vind spelerdat uit geheugen
    2. Pas waarde aan (bijv 1 kill er bij etc)

    Server sluit af:
    1. Haal alles uit cache op
    2. Schrijf weg

    Elke 5 minuten:
    1. Haal alles uit cache op
    2. Schrijf weg

    Lijstje -> Key: value

    Lijte -> Keywaarde = value

    PlayerData
     - UUID
     - Kills
     - Deaths
     - Playtime

     Hashmap.get(UUID);
     PlayerData data = new PlayerData(H\);

     PlayerData data = Hashmap.get(UUID);
     data.addKill();
     HashMap.put(data);

     Weg schrijven
     HashMap hm = new HashMap;

     foreach()
     {
             PlayerData data = Hashmap.get(UUID);
             SQL Logica

     }

     */
