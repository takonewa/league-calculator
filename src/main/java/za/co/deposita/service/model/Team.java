package za.co.deposita.service.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;

import java.util.List;

@Log
@Data
@Builder
public class Team implements Comparable<Team> {
    private String name;
    private List<Game> games;

    public Integer getTotalPoints() {
        return (games.stream()
                .mapToInt(game -> game.getPoints())
                .sum());
    }

    public Integer getTotalGoalForward() {
        return (games.stream()
                .mapToInt(game -> game.getGoalsForward())
                .sum());
    }

    public Integer getTotalGoalAgainst() {
        return (games.stream()
                .mapToInt(game -> game.getGoalAgainst())
                .sum());
    }

    public Integer getTotalGoalDifference() {
        return (getTotalGoalForward() - getTotalGoalAgainst());
    }

    @Override
    public int compareTo(Team other) {
        if (getTotalPoints().equals(other.getTotalPoints())) {
            if (getTotalGoalDifference().equals(other.getTotalGoalDifference())) {
                return (getTotalGoalForward().compareTo(other.getTotalGoalForward()));
            } else {
                return (getTotalGoalDifference().compareTo(other.getTotalGoalDifference()));
            }
        } else {
            return (getTotalPoints().compareTo(other.getTotalPoints()));
        }
    }

    public Integer getGamesPlayed() {
        return (this.games.size());
    }

    public long getGamesWon() {
        return (this.games.stream()
                .filter(game -> game.isWon())
                .count());
    }

    public long getGamesLost() {
        return (this.games.stream()
                .filter(game -> game.isLost())
                .count());
    }

    public long getGamesDrawn() {
        return (this.games.stream()
                .filter(game -> game.isDrawn())
                .count());
    }
}
