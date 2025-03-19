package dk.sdu.cbse.bullet;

import dk.sdu.cbse.bulletsystem.Bullet;
import dk.sdu.cbse.bulletsystem.BulletSPI;
import dk.sdu.cbse.data.*;
import dk.sdu.cbse.services.IEntityProcessing;

public class BulletSystem implements IEntityProcessing, BulletSPI {
    @Override
    public void process(Time time, GameData gameData, World world) {
        for (Bullet bullet : world.getEntities(Bullet.class)) {
            Vector posChange = new Vector(
                    Math.cos(Math.toRadians(bullet.getRotation())) * bullet.getSpeed() * time.getDeltaTime(),
                    Math.sin(Math.toRadians(bullet.getRotation())) * bullet.getSpeed() * time.getDeltaTime()
            );
            Vector newPos = bullet.getPosition().add((posChange));

            if (newPos.getX() > gameData.getDisplayWidth() || newPos.getX() < 0 ||
                    newPos.getY() > gameData.getDisplayHeight() || newPos.getY() < 0)
                world.removeEntity(bullet);
            else
                bullet.setPosition(newPos);
        }
    }

    @Override
    public Entity createBullet(Vector position, double rotation) {
        Bullet bullet = new Bullet();
        bullet.setPolygonCoordinates(0,2,1.41,1.41,2,0,1.41,-1.41,0,-2,-1.41,-1.41,-2,0,-1.41,1.41);
        bullet.setColor(1,0,0);
        bullet.setPosition(position);
        bullet.setRotation(rotation);
        bullet.setSpeed(200);

        return bullet;
    }
}
