package dk.sdu.cbse.player;

import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.Vector;

public class Player extends Entity {
    double lastFireTime;
    double fireDelay = 0.25;

    Vector velocity = new Vector(0,0);
    float maxVelocity = 150;
    double friction = 0.8;
    double acceleration = 400;
}
