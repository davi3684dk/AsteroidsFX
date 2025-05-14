package dk.sdu.cbse.data;

public class GameData {
    private int displayWidth = 720;
    private int displayHeight = 720;
    private final Input input = new Input();

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
}
