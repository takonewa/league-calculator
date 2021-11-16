package za.co.deposita;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.deposita.service.FileService;
import za.co.deposita.service.exceptions.GameInvalidException;
import za.co.deposita.service.exceptions.LeagueFileException;
import za.co.deposita.service.util.TeamParser;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LeagueCalculatorApplicationTests {

    private FileService fileService = new FileService();

    @Test
    public void invalidFile() {
        assertThrows(LeagueFileException.class, () -> {
            fileService.processFile("fake.csv");
        });
    }

    @Test
    public void invalidGame() {
        assertThrows(GameInvalidException.class, () -> {
            TeamParser.parseGame(null);
        });
    }

    @Test
    public void gameLost() {
        assertTrue(TeamParser.parseGame("0 - 2").isLost());
    }

    @Test
    public void gameWon() {
        assertTrue(TeamParser.parseGame("1 - 0").isWon());
    }

    @Test
    public void gameDrawn() {
        assertTrue(TeamParser.parseGame("1 - 1").isDrawn());
    }
}
