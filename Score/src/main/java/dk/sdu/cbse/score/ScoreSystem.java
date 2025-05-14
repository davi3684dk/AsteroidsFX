package dk.sdu.cbse.score;

import dk.sdu.cbse.commonscore.ScoreSPI;

public class ScoreSystem implements ScoreSPI {
    private static int totalScore = 0;

    @Override
    public void addScore(int score) {
        totalScore += score;
    }

    @Override
    public int getScore() {
        return totalScore;
    }
}
