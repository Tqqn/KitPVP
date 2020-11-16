package KitPvP.Tqqn.DB;

import KitPvP.Tqqn.Game;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DBGetter {

    private Game game;

    public DBGetter(Game game) {
        this.game = game;
    }

    //Creating a table if not exists with the UUID, KILLS and DEATHS

    public void createTable() {
        PreparedStatement ps;
        try {
            ps = game.database.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS player_info "
            + "(UUID VARCHAR(100),KILLS INT(100),DEATHS INT(100),PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Creating a new player if it not exist

    public void createPlayer(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            if (!exists(uuid)) {
                PreparedStatement ps = game.database.getConnection().prepareStatement("INSERT IGNORE INTO player_info" +
                        " (UUID) VALUES (?)");
                ps.setString(1, uuid.toString());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //boolean to check is the uuid exists
    public boolean exists(UUID uuid) {
        try {
            PreparedStatement ps = game.database.getConnection().prepareStatement("SELECT * FROM player_info WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet results = ps.executeQuery();
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
    public void addKills(UUID uuid, int kills) {
        try {
            PreparedStatement ps = game.database.getConnection().prepareStatement("UPDATE player_info SET KILLS=? WHERE UUID=?");
            ps.setInt(1,(getKills(uuid) + kills));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Adding deaths to the DataBase to the desired Player
    public void addDeaths(UUID uuid, int deaths) {
        try {
            PreparedStatement ps = game.database.getConnection().prepareStatement("UPDATE player_info SET DEATHS=? WHERE UUID=?");
            ps.setInt(1, (getDeaths(uuid) + deaths));
            ps.setString(2,uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Getting the kills from the DataBase from the desired Player
    public int getKills(UUID uuid) {
        try {
            PreparedStatement ps = game.database.getConnection().prepareStatement("SELECT KILLS FROM player_info WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int kills = 0;
            if (rs.next()) {
                kills = rs.getInt("KILLS");
                return kills;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Getting the deaths from the DataBase from the desired Player
    public int getDeaths(UUID uuid) {
        try {
            PreparedStatement ps = game.database.getConnection().prepareStatement("SELECT DEATHS FROM player_info WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int deaths = 0;
            if (rs.next()) {
                deaths = rs.getInt("DEATHS");
                return deaths;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //getting the ratio from the kills/deaths returning string with %.1f format
    public String getRatio(UUID uuid) {
        if (game.data.getKills(uuid) == 0 || game.data.getDeaths(uuid) == 0) {
            return "0.0";
        }
        return String.format("%.1f", (double) game.data.getKills(uuid)  / game.data.getDeaths(uuid));
    }

}
