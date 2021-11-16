package za.co.deposita.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Game {
    private int number;
    private int goalAgainst;
    private int goalsForward;

    public int getPoints() {
        return this.goalAgainst == this.goalsForward ? 1 :
                this.goalAgainst > this.goalsForward ? 0 : 3;
    }

    public boolean isWon() {
        return getPoints() == 3;
    }

    public boolean isLost() {
        return getPoints() == 0;
    }

    public boolean isDrawn() {
        return getPoints() == 1;
    }
}
