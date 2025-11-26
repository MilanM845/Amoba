package hu.progtech.amoba;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class Board {
    private final int rows;
    private final int cols;
    private final char[][] grid; // ' ' empty, 'X', 'O'

    public Board(int rows, int cols) {
        if (cols < 4 || rows < cols || rows > 25 || cols > 25) {
            throw new IllegalArgumentException("Invalid sizes: require 4 <= M <= N <=25");
        }
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
        for (int r = 0; r < rows; r++) for (int c = 0; c < cols; c++) grid[r][c] = ' ';
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public char at(int r, int c) { return grid[r][c]; }

    public boolean isEmpty(int r, int c) { return grid[r][c] == ' '; }

    public boolean place(int r, int c, char p) {
        if (!isInside(r, c) || !isEmpty(r, c)) return false;
        grid[r][c] = p;
        return true;
    }

    public boolean isInside(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    public List<int[]> getAllEmpty() {
        List<int[]> res = new ArrayList<>();
        for (int r = 0; r < rows; r++) for (int c = 0; c < cols; c++) if (isEmpty(r,c)) res.add(new int[]{r,c});
        return res;
    }

    public List<int[]> getAdjacentEmpty() {
        boolean[][] ok = new boolean[rows][cols];
        for (int r = 0; r < rows; r++) for (int c = 0; c < cols; c++) if (!isEmpty(r,c)) {
            for (int dr = -1; dr <= 1; dr++) for (int dc = -1; dc <= 1; dc++) {
                int nr = r + dr, nc = c + dc;
                if (isInside(nr, nc) && isEmpty(nr, nc)) ok[nr][nc] = true;
            }
        }
        List<int[]> res = new ArrayList<>();
        for (int r = 0; r < rows; r++) for (int c = 0; c < cols; c++) if (ok[r][c]) res.add(new int[]{r,c});
        return res;
    }

    public boolean checkWin(char player) {

        for (int r = 0; r < rows; r++) for (int c = 0; c < cols; c++) {
            if (grid[r][c] != player) continue;
            if (countDirection(r,c,1,0,player) >= 5) return true;
            if (countDirection(r,c,0,1,player) >= 5) return true;
            if (countDirection(r,c,1,1,player) >= 5) return true;
            if (countDirection(r,c,1,-1,player) >= 5) return true;
        }
        return false;
    }

    private int countDirection(int r, int c, int dr, int dc, char player) {
        int cnt = 0;
        int rr = r, cc = c;
        while (isInside(rr, cc) && grid[rr][cc] == player) {
            cnt++; rr += dr; cc += dc;
        }
        return cnt;
    }

    public void printBoard() {

        System.out.print("   ");
        for (int c = 0; c < cols; c++) System.out.print((char)('a'+c)+" ");
        System.out.println();
        for (int r = 0; r < rows; r++) {
            System.out.printf("%2d ", r+1);
            for (int c = 0; c < cols; c++) System.out.print(grid[r][c] == ' ' ? ". " : grid[r][c] + " ");
            System.out.println();
        }
    }
}
