package dk.sdu.cbse.commonasteroid;

import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.Vector;

public interface AsteroidSPI {
    Entity createAsteroid(Vector position, double rotation);
}
