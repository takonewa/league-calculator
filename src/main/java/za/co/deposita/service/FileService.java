package za.co.deposita.service;

import lombok.extern.java.Log;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import za.co.deposita.service.exceptions.GameInvalidException;
import za.co.deposita.service.exceptions.LeagueFileException;
import za.co.deposita.service.exceptions.RecordInvalidException;
import za.co.deposita.service.model.Team;
import za.co.deposita.service.util.PrintHelper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Collections.reverseOrder;
import static za.co.deposita.service.util.TeamParser.parse;

@Log
@Component
public class FileService {

    public void processFile(String fileResourcePath) throws GameInvalidException, RecordInvalidException, LeagueFileException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classloader.getResourceAsStream(fileResourcePath)) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            List<Team> teams = StreamSupport
                    .stream(records.spliterator(), false)
                    .skip(1)
                    .map(record -> parse(record))
                    .sorted(reverseOrder())
                    .collect(Collectors.toList());
            printData(teams);
        } catch (NullPointerException | IOException exception) {
            throw new LeagueFileException("Failed to process league file input: " + exception.getMessage());
        }
    }

    private void printData(List<Team> teams) {
        log.log(Level.INFO, PrintHelper.LINE);
        log.log(Level.INFO, PrintHelper.HEADERS);
        log.log(Level.INFO, PrintHelper.LINE);
        int position = 1;
        for (Team team : teams) {
            log.log(Level.INFO, PrintHelper.ENTRY,
                    new Object[]{
                            position++,
                            team.getName(),
                            team.getGamesPlayed(),
                            team.getGamesWon(),
                            team.getGamesDrawn(),
                            team.getGamesLost(),
                            team.getTotalGoalForward(),
                            team.getTotalGoalAgainst(),
                            team.getTotalGoalDifference(),
                            team.getTotalPoints(),
                    });
            if (position == 18) {
                log.log(Level.INFO, PrintHelper.LINE);
            }
        }
        log.log(Level.INFO, PrintHelper.LINE);
    }
}
