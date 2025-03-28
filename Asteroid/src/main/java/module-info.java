import dk.sdu.asteroid.AsteroidPlugin;
import dk.sdu.asteroid.AsteroidSystem;
import dk.sdu.cbse.commoncollision.IEntityCollisionProcessor;
import dk.sdu.cbse.services.IEntityProcessing;
import dk.sdu.cbse.services.IPlugin;

module Asteroid {
    requires CommonAsteroid;
    requires CommonCollision;
    requires Common;
    requires java.desktop;
    provides IPlugin with AsteroidPlugin;
    provides IEntityProcessing with AsteroidSystem;
    provides IEntityCollisionProcessor with AsteroidSystem;
}