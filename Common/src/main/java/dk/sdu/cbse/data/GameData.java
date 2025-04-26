package dk.sdu.cbse.data;

public class GameData {
    private int displayWidth = 720;
    private int displayHeight = 720;
    private final Input input = new Input();
    private int score = 0;

    public Input getInput() {
        return input;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }
}
