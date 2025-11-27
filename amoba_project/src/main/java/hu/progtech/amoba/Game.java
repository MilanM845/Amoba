package hu.progtech.amoba;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Game {
    private final Board board;
    private final HighScoreRepository repo;
    private final Scanner scanner;
    private final Random random = new Random();

    public Game(int rows, int cols, HighScoreRepository repo, InputStream in) {
        this.board = new Board(rows, cols);
        this.repo = repo;
        this.scanner = new Scanner(in);
        int centerR = rows/2;
        int centerC = cols/2;
        board.place(centerR, centerC, 'X');
    }

    public void start() {
        System.out.println("Üdvözöllek az Amoba (NxM) játékba. Játékos=X, AI=O. Cél: 5 X egymás mellett.");
        System.out.print("Add meg a neved: "); String name = scanner.nextLine().trim();
        boolean humanTurn = true;
        board.printBoard();

        while (true) {
            if (humanTurn) {
                System.out.print("A te lépésed (pl f5), or 'save filename' / 'load filename' / 'quit': ");
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                if (line.startsWith("save ")) {
                    String fn = line.substring(5).trim();
                    saveBoard(fn);
                    continue;
                } else if (line.startsWith("load ")) {
                    String fn = line.substring(5).trim();
                    loadBoard(fn);
                    board.printBoard();
                    continue;
                } else if (line.equalsIgnoreCase("quit")) {
                    System.out.println("Szia!"); break;
                } else {
                    int[] rc = parseMove(line);
                    if (rc == null) { System.out.println("Nem megfelelő formátum."); continue; }
                    if (!board.isInside(rc[0], rc[1]) || !board.isEmpty(rc[0], rc[1])) { System.out.println("Nem megfelelő mező vagy a mező már foglalt."); continue; }
                    if (!isAdjacentToAny(rc[0], rc[1])) { System.out.println("Egy kitöltött mező mellé kell raknod."); continue; }
                    board.place(rc[0], rc[1], 'X');
                    board.printBoard();
                    if (board.checkWin('X')) {
                        System.out.println("Nyertél! Gratulálok.");
                        persistWin(name);
                        break;
                    }
                    humanTurn = false;
                }
            } else {

                List<int[]> options = board.getAdjacentEmpty();
                if (options.isEmpty()) options = board.getAllEmpty();
                if (options.isEmpty()) { System.out.println("Döntetlen."); break; }
                int[] choice = options.get(random.nextInt(options.size()));
                board.place(choice[0], choice[1], 'O');
                System.out.println("AI ide rakta " + (char)('a'+choice[1]) + (choice[0]+1));
                board.printBoard();
                if (board.checkWin('O')) {
                    System.out.println("AI Nyert."); persistWin("AI"); break;
                }
                humanTurn = true;
            }
        }


        System.out.println("High Scores:");
        try {
            repo.printHighScores();
        } catch (SQLException e) {
            System.err.println("Nem sikerült betölteni a pontszámokat: " + e.getMessage());
        }
    }

    private void persistWin(String winner) {
        try {
            repo.recordWin(winner);
        } catch (SQLException e) {
            System.err.println("Nem sikerült menteni a pontszámot " + e.getMessage());
        }
    }

    private boolean isAdjacentToAny(int r, int c) {
        for (int dr = -1; dr <= 1; dr++) for (int dc = -1; dc <=1; dc++) {
            if (dr==0 && dc==0) continue;
            int nr = r+dr, nc = c+dc;
            if (board.isInside(nr,nc) && !board.isEmpty(nr,nc)) return true;
        }
        return false;
    }

    private int[] parseMove(String s) {
        s = s.trim().toLowerCase();
        if (s.length() < 2) return null;
        char col = s.charAt(0);
        if (col < 'a' || col > 'z') return null;
        try {
            int row = Integer.parseInt(s.substring(1)) - 1;
            int c = col - 'a';
            return new int[]{row, c};
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private void saveBoard(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println(board.getRows() + " " + board.getCols());
            for (int r = 0; r < board.getRows(); r++) {
                for (int c = 0; c < board.getCols(); c++) {
                    pw.print(board.at(r,c));
                }
                pw.println();
            }
            System.out.println("Lementve ide " + filename);
        } catch (IOException e) {
            System.err.println("Nem sikerült menteni: " + e.getMessage());
        }
    }

    private void loadBoard(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String first = br.readLine();
            if (first == null) { System.out.println("Üres fájl."); return; }
            String[] parts = first.split("\s+"); int r = Integer.parseInt(parts[0]); int c = Integer.parseInt(parts[1]);
            for (int i = 0; i < r; i++) {
                String line = br.readLine();
                if (line == null) break;
                for (int j = 0; j < Math.min(line.length(), c); j++) {
                    char ch = line.charAt(j);
                    if (ch == 'X' || ch == 'O') {

                    }
                }
            }
            System.out.println("Betölteni fájlból.");
        } catch (IOException e) {
            System.err.println("Nem sikerült betölteni: " + e.getMessage());
        }
    }
}
