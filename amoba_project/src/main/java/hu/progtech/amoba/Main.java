package hu.progtech.amoba;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        int rows = 10, cols = 10;
        HighScoreRepository repo = new HighScoreRepository("amoba.db");
        Game game = new Game(rows, cols, repo, System.in);
        game.start();
    }
}
