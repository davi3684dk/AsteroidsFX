package dk.sdu.cbse.player;

import dk.sdu.cbse.bulletsystem.BulletSPI;
import dk.sdu.cbse.data.*;
import dk.sdu.cbse.services.IEntityProcessing;

import java.util.ServiceLoader;

import static dk.sdu.cbse.player.Plugin.player;

public class PlayerMovementSystem implements IEntityProcessing {
    @Override
    public void process(Time time, GameData gameData, World world) {
        if (player.isDead())
            return;

        if (gameData.getInput().isDown(Input.RIGHT)) {
            player.setRotation(player.getRotation() + 360 * time.getDeltaTime());
        }
        if (gameData.getInput().isDown(Input.LEFT)) {
            player.setRotation(player.getRotation() - 360 * time.getDeltaTime());
        }

        if (gameData.getInput().isDown(Input.UP)) {
            Vector posChange = new Vector(
                Math.cos(Math.toRadians(player.getRotation())) * 100 * time.getDeltaTime(),
                Math.sin(Math.toRadians(player.getRotation())) * 100 * time.getDeltaTime()
            );
            Vector newPos = player.getPosition().add(posChange);
            player.setPosition(newPos);
        }

        if (gameData.getInput().isDown(Input.SPACE) && (time.getNow() - player.lastFireTime) > player.fireDelay) {
            player.lastFireTime = time.getNow();

            BulletSPI bulletSPI = GetBulletSPI();
            if (bulletSPI != null) {
                Entity bullet = bulletSPI.createBullet(player.getPosition(), player.getRotation());
                bullet.setLayer(Layer.Player);
                world.addEntity(bullet);
            }
        }
    }

    private BulletSPI GetBulletSPI() {
        return ServiceLoader.load(BulletSPI.class).findFirst().orElse(null);
    }
}
