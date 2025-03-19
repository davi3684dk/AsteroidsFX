package dk.sdu.cbse.bulletsystem;

import dk.sdu.cbse.data.Entity;

public class Bullet extends Entity {
    private double speed;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
