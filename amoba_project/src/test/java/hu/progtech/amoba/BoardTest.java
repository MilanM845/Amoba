package hu.progtech.amoba;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void testPlaceAndWinHorizontal() {
        Board b = new Board(10, 10);
        for (int i = 0; i < 5; i++) b.place(2, i, 'X');
        assertTrue(b.checkWin('X'));
    }

    @Test
    public void testNoWin() {
        Board b = new Board(10, 10);
        b.place(0,0,'X');
        b.place(1,1,'X');
        assertFalse(b.checkWin('X'));
    }
}
