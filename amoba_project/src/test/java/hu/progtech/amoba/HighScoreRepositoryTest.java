package hu.progtech.amoba;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HighScoreRepositoryTest {

    @Test
    public void testRecordWin() throws Exception {
        HighScoreRepository repo = new HighScoreRepository(":memory:");
        repo.recordWin("Test");
        repo.recordWin("Test");
        var scores = repo.getHighScores();
        assertEquals(1, scores.size());
        assertTrue(scores.get(0).contains("2"));
    }

    @Test
    public void testGetHighScores() throws Exception {
        HighScoreRepository repo = new HighScoreRepository(":memory:");
        repo.recordWin("A");
        repo.recordWin("B");
        repo.recordWin("B");
        var list = repo.getHighScores();
        assertEquals(2, list.size());
        assertTrue(list.get(0).startsWith("B"));
    }
}
