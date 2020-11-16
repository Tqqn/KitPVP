package KitPvP.Tqqn.DB;

import KitPvP.Tqqn.Game;
import org.bukkit.entity.Player;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DBGetter {

    private Game game;

    public DBGetter(Game game) {
        this.game = game;
    }

    public void createTable() {
        PreparedStatement ps;
        try {
            ps = game.database.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS player_info "
            + "(UUID VARCHAR(100),LASTLOGIN DATE,KILLS INT(100),DEATHS INT(100),PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public void setDate(UUID uuid, Date date) {
        try {
            PreparedStatement ps = game.database.getConnection().prepareStatement("UPDATE player_info SET LASTLOGIN=? WHERE UUID=?");
            ps.setDate(1,date);
            ps.setString(2,uuid.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


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

}
