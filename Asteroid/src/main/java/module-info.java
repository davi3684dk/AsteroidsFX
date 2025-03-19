import dk.sdu.cbse.asteroid.AsteroidPlugin;
import dk.sdu.cbse.asteroid.AsteroidSystem;
import dk.sdu.cbse.commoncollision.IEntityCollisionProcessor;
import dk.sdu.cbse.services.IEntityProcessing;
import dk.sdu.cbse.services.IPlugin;

module Asteroid {
    requires CommonAsteroid;
    requires java.desktop;
    requires CommonCollision;
    requires Common;
    provides IPlugin with AsteroidPlugin;
    provides IEntityProcessing with AsteroidSystem;
    provides IEntityCollisionProcessor with AsteroidSystem;
}