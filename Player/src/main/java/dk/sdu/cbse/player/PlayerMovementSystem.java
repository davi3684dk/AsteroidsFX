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
                Math.cos(Math.toRadians(player.getRotation())) * player.acceleration * time.getDeltaTime(),
                Math.sin(Math.toRadians(player.getRotation())) * player.acceleration * time.getDeltaTime()
            );
            player.velocity = player.velocity.add(posChange).clamp(player.maxVelocity);
        }

        Vector newPos = player.getPosition().add(player.velocity.scale(time.getDeltaTime()));
        player.setPosition(newPos);
        Vector frictionVec = player.velocity.scale(-1).scale(player.friction * time.getDeltaTime());
        player.velocity = player.velocity.add(frictionVec).clamp(player.maxVelocity);

        if (gameData.getInput().isDown(Input.SPACE) && (time.getNow() - player.lastFireTime) > player.fireDelay) {
            player.lastFireTime = time.getNow();

            BulletSPI bulletSPI = getBulletSPI();
            if (bulletSPI != null) {
                Entity bullet = bulletSPI.createBullet(player.getPosition(), player.getRotation());
                bullet.setLayer(Layer.Player);
                bullet.setColor(1,1,1);
                world.addEntity(bullet);
            }
        }
    }

    private BulletSPI getBulletSPI() {
        return ServiceLoader.load(BulletSPI.class).findFirst().orElse(null);
    }
}
