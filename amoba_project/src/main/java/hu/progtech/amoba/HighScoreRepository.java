package hu.progtech.amoba;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class HighScoreRepository {
    private final String url;

    public HighScoreRepository(String dbFile) throws SQLException {
        this.url = "jdbc:sqlite:" + dbFile;
        try (Connection c = DriverManager.getConnection(url)) {
            try (Statement s = c.createStatement()) {
                s.executeUpdate("CREATE TABLE IF NOT EXISTS scores (name TEXT PRIMARY KEY, wins INTEGER)"); 
            }
        }
    }

    public void recordWin(String name) throws SQLException {
        try (Connection c = DriverManager.getConnection(url)) {
            try (PreparedStatement p = c.prepareStatement("INSERT INTO scores(name,wins) VALUES(?,1) ON CONFLICT(name) DO UPDATE SET wins = wins + 1")) {
                p.setString(1, name);
                p.executeUpdate();
            }
        }
    }

    public List<String> getHighScores() throws SQLException {
        List<String> out = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(url)) {
            try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT name,wins FROM scores ORDER BY wins DESC")) {
                while (rs.next()) {
                    out.add(rs.getString("name") + ": " + rs.getInt("wins"));
                }
            }
        }
        return out;
    }

    public void printHighScores() throws SQLException {
        List<String> hs = getHighScores();
        for (String line : hs) System.out.println(line);
    }
}
