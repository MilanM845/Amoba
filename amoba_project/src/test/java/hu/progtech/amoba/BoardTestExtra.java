package hu.progtech.amoba;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTestExtra {

    @Test
    public void testAdjacentEmpty() {
        Board b = new Board(5,5);
        b.place(2,2,'X');
        var adj = b.getAdjacentEmpty();
        assertEquals(8, adj.size());
    }

    @Test
    public void testInside() {
        Board b = new Board(5,5);
        assertTrue(b.isInside(0,0));
        assertFalse(b.isInside(5,5));
    }
}
