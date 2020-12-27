package me.tqqn.kit_pvp.database;

import me.tqqn.kit_pvp.utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

    //gets the login info from the config.

    private String host = Config.getHost();
    private String port = Config.getPort();
    private String database = Config.getDataBaseName();
    private String username = Config.getUsername();
    private String password = Config.getPassword();

    private Connection connection;

    //if DataBase is connected return true else false.
    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    //Standard code to connect DataBase
    public void connect() throws ClassNotFoundException, SQLException  {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
        }

    }
    //Standard code to disconnect DataBase
    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
    //Standard code to get the connection
    public Connection getConnection() {
        return connection;
    }

}
