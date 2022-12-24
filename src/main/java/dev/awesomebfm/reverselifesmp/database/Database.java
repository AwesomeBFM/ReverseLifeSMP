package dev.awesomebfm.reverselifesmp.database;

import dev.awesomebfm.reverselifesmp.models.DeadPlayer;
import dev.awesomebfm.reverselifesmp.models.PlayerData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Database {
    String url;
    String port;
    String username;
    String password;
    String database;

    private Connection connection;

    public Database(String url, String port, String username, String password, String database) {
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public void connect() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + url + ":" + port + "/" + database + "?autoReconnect=true&cmaxReconnects=5", username, password);
            } catch (SQLException e) {
                System.out.println("Kick and shout!");
                e.printStackTrace();
            }
        }
    }

    public void initialize() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS rlsmp_data (uuid VARCHAR(36) primary key, hearts int, deathbanned boolean)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS rlsmp_bans (uuid VARCHAR(36) primary key, ign VARCHAR(16))");
        statement.close();
    }

    public void purge() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM rlsmp_data");
        statement.executeUpdate("DELETE FROM rlsmp_bans");
        statement.close();
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public PlayerData getData(UUID uuid) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM rlsmp_data WHERE uuid = ?");
        ps.setString(1, uuid.toString());

        ResultSet rs = ps.executeQuery();

        PlayerData data;

        if (rs.next()) {
            data = new PlayerData(UUID.fromString(rs.getString("uuid")), rs.getInt("hearts"), rs.getBoolean("deathBanned"));
            ps.close();
            return data;
        }
        ps.close();
        return null;
    }

    public void createData(PlayerData data) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO rlsmp_data (uuid, hearts, deathBanned) VALUES (?, ?, ?)");
        ps.setString(1, data.getUuid().toString());
        ps.setInt(2, data.getHearts());
        ps.setBoolean(3, data.isDeathBanned());
        ps.executeUpdate();
        ps.close();
    }

    public void updateData(PlayerData data) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE rlsmp_data SET hearts = ?, deathBanned = ? WHERE uuid = ?");
        ps.setInt(1, data.getHearts());
        ps.setBoolean(2, data.isDeathBanned());
        ps.setString(3, data.getUuid().toString());
        ps.executeUpdate();
        ps.close();
    }

    public void deleteData(UUID uuid) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM rlsmp_data WHERE uuid = ?");
        ps.setString(1, uuid.toString());
        ps.executeUpdate();
        ps.close();
    }

    public void addDeathBan(UUID uuid, String ign) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO rlsmp_bans (uuid, ign) VALUES (?, ?)");
        ps.setString(1, uuid.toString());
        ps.setString(2, ign);
        ps.executeUpdate();
        ps.close();
    }

    public void removeDeathBan(UUID uuid) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM rlsmp_bans WHERE uuid = ?");
        ps.setString(1, uuid.toString());
        ps.executeUpdate();
        ps.close();
    }

    public ArrayList<DeadPlayer> getDeathBans() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM rlsmp_bans");
        ResultSet rs = ps.executeQuery();
        ArrayList<DeadPlayer> deadPlayers = new ArrayList<>();
        while (rs.next()) {
            deadPlayers.add(new DeadPlayer(UUID.fromString(rs.getString("uuid")), rs.getString("ign")));
        }
        ps.close();
        return deadPlayers;
    }
}
