package hu.progtech.amoba;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {

    @Test
    public void testParseMoveValid() throws Exception {
        HighScoreRepository repo = mock(HighScoreRepository.class);
        Game g = new Game(10, 10, repo, new ByteArrayInputStream("P\n".getBytes()));

        var m = g.getClass().getDeclaredMethod("parseMove", String.class);
        m.setAccessible(true);
        int[] res = (int[]) m.invoke(g, "c5");
        assertEquals(4, res[0]);
        assertEquals(2, res[1]);
    }

    @Test
    public void testParseMoveInvalid() throws Exception {
        HighScoreRepository repo = mock(HighScoreRepository.class);
        Game g = new Game(10, 10, repo, new ByteArrayInputStream("P\n".getBytes()));

        var m = g.getClass().getDeclaredMethod("parseMove", String.class);
        m.setAccessible(true);

        assertNull(m.invoke(g, "55"));
        assertNull(m.invoke(g, "x"));
    }

    @Test
    public void testIsAdjacentToAny() throws Exception {
        HighScoreRepository repo = mock(HighScoreRepository.class);
        Game g = new Game(10, 10, repo, new ByteArrayInputStream("P\n".getBytes()));

        var adj = g.getClass().getDeclaredMethod("isAdjacentToAny", int.class, int.class);
        adj.setAccessible(true);

        assertTrue((boolean) adj.invoke(g, 4, 4));
        assertFalse((boolean) adj.invoke(g, 0, 0));
    }
}
