import dk.sdu.asteroid.AsteroidPlugin;
import dk.sdu.asteroid.AsteroidSystem;
import dk.sdu.cbse.commoncollision.IEntityCollisionProcessor;
import dk.sdu.cbse.services.IEntityProcessing;
import dk.sdu.cbse.services.IPlugin;

module Asteroid {
    uses dk.sdu.cbse.commonscore.ScoreSPI;
    requires CommonAsteroid;
    requires CommonCollision;
    requires Common;
    requires java.desktop;
    requires CommonScore;
    provides IPlugin with AsteroidPlugin;
    provides IEntityProcessing with AsteroidSystem;
    provides IEntityCollisionProcessor with AsteroidSystem;
}