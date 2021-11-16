package za.co.deposita.service.util;

import lombok.extern.java.Log;
import org.apache.commons.csv.CSVRecord;
import org.springframework.util.StringUtils;
import za.co.deposita.service.exceptions.GameInvalidException;
import za.co.deposita.service.exceptions.RecordInvalidException;
import za.co.deposita.service.model.Game;
import za.co.deposita.service.model.Team;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Log
public final class TeamParser {

    public static Team parse(CSVRecord record) throws RecordInvalidException {
        if (!StringUtils.hasText(record.get(0))) {
            throw new RecordInvalidException("Team record is invalid");
        }
        return Team.builder()
                .name(record.get(0))
                .games(parseGames(record))
                .build();
    }

    public static List<Game> parseGames(CSVRecord record) throws GameInvalidException {
        List<Game> games = new ArrayList<>();
        for (int index = 1; index <= 38; index++) {
            String scoreline = record.get(index);
            if (!StringUtils.hasText(scoreline)) {
                throw new RecordInvalidException("Game entry is not valid: " + scoreline);
            }
            Game game = parseGame(scoreline);
            if (game.getGoalAgainst() < 0 || game.getGoalsForward() < 0) {
                throw new RecordInvalidException("Scoreline cannot be negative: " + game);
            }
            games.add(game);
        }
        return (games);
    }

    public static Game parseGame(String record) throws GameInvalidException {
        try {
            String[] scores = record.split("-");
            return Game.builder()
                    .goalsForward(parseInt(scores[0].trim()))
                    .goalAgainst(parseInt(scores[1].trim()))
                    .build();
        } catch (NumberFormatException | NullPointerException exception) {
            throw new GameInvalidException("Failed to pass game score: " + record);
        }
    }
}
