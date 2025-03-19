package dk.sdu.cbse.data;

public class Input {
    public static final int NUM_KEYS = 4;

    private static boolean[] keys = new boolean[NUM_KEYS];
    private static boolean[] pressedKeys = new boolean[NUM_KEYS];

    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int SPACE = 3;

    public void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            pressedKeys[i] = keys[i];
        }
    }

    public void setKey(int key, boolean pressed) {
        keys[key] = pressed;
    }

    public boolean isDown(int key) {
        return keys[key];
    }

    public boolean isPressed(int key) {
        return keys[key] && !pressedKeys[key];
    }
}
