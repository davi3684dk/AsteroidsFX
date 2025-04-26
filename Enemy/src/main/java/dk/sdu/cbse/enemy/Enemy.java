package dk.sdu.cbse.enemy;

import dk.sdu.cbse.data.Entity;

public class Enemy extends Entity {
    double lastFireTime;
    double fireDelay = 0.5;
    private double speed;
    double directionChangeFrequency;
    double lastDirectionChange;

    public Enemy(double speed, double directionChangeFrequency) {
        this.speed = speed;
        this.directionChangeFrequency = directionChangeFrequency;
    }


    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
