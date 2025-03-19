package dk.sdu.cbse.bulletsystem;

import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.Vector;

public interface BulletSPI {
    Entity createBullet(Vector position, double rotation);
}
