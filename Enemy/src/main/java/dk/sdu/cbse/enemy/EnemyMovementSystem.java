package dk.sdu.cbse.enemy;

import dk.sdu.cbse.bulletsystem.BulletSPI;
import dk.sdu.cbse.data.*;
import dk.sdu.cbse.services.IEntityProcessing;

import java.util.List;
import java.util.Random;
import java.util.ServiceLoader;

public class EnemyMovementSystem implements IEntityProcessing {
    private Random rand = new Random();

    @Override
    public void process(Time time, GameData gameData, World world) {
        List<Enemy> enemies = world.getEntities(Enemy.class);
        for (Enemy enemy : enemies) {
            Vector posChange = new Vector(
                    Math.cos(Math.toRadians(enemy.getRotation())) * enemy.getSpeed() * time.getDeltaTime(),
                    Math.sin(Math.toRadians(enemy.getRotation())) * enemy.getSpeed() * time.getDeltaTime()
            );
            Vector newPos = enemy.getPosition().add(posChange);

            if (newPos.getX() < 0)
                newPos.setX(gameData.getDisplayWidth() + newPos.getX());
            else if (newPos.getY() < 0)
                newPos.setY(gameData.getDisplayHeight() + newPos.getY());
            else if (newPos.getX() > gameData.getDisplayWidth())
                newPos.setX(gameData.getDisplayWidth() - newPos.getX());
            else if (newPos.getY() > gameData.getDisplayHeight())
                newPos.setY(gameData.getDisplayHeight() - newPos.getY());

            enemy.setPosition(newPos);

            if (time.getNow() - enemy.lastDirectionChange > enemy.directionChangeFrequency) {
                enemy.lastDirectionChange = time.getNow();
                enemy.setRotation(enemy.getRotation() + Math.random() * 45);
            }

            if (time.getNow() - enemy.lastFireTime > enemy.fireDelay) {
                enemy.lastFireTime = time.getNow();
                if (rand.nextDouble() < 0.3) {
                    Entity bullet = getBulletSPI().createBullet(enemy.getPosition(), enemy.getRotation());
                    bullet.setLayer(Layer.Enemy);
                    world.addEntity(bullet);
                }
            }
        }

        int maxEnemies = (int) (Math.pow(time.getNow() / 60, 0.4) * 2);
        if (enemies.size() < maxEnemies) {

            Vector position;
            if (rand.nextBoolean()) {
                position = new Vector(0, rand.nextDouble(gameData.getDisplayHeight()));
            }
            else {
                position = new Vector(rand.nextDouble(gameData.getDisplayWidth()), 0);
            }
            world.addEntity(createEnemy(position));
        }
    }

    private Enemy createEnemy(Vector position) {
        Enemy enemy = new Enemy(60, 2);
        enemy.setPosition(position);
        enemy.setColor(1, 0, 0);
        enemy.setPolygonCoordinates(-15,-10,15,0,-15,10,-11,0);
        enemy.setRadius(15);
        enemy.setLayer(Layer.Enemy);
        return enemy;
    }

    private BulletSPI getBulletSPI() {
        return ServiceLoader.load(BulletSPI.class).findFirst().orElse(null);
    }
}
