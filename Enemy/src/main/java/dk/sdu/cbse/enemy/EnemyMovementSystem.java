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
            if (newPos.getY() < 0)
                newPos.setY(gameData.getDisplayHeight() + newPos.getY());
            if (newPos.getX() > gameData.getDisplayWidth())
                newPos.setX(gameData.getDisplayWidth() - newPos.getX());
            if (newPos.getY() > gameData.getDisplayHeight())
                newPos.setY(gameData.getDisplayHeight() - newPos.getY());

            enemy.setPosition(newPos);

            if (time.getNow() - enemy.lastDirectionChange > enemy.directionChangeFrequency) {
                enemy.lastDirectionChange = time.getNow();
                enemy.setRotation(Math.random() * 360);
            }

            int maxEnemies = (int)(Math.pow(time.getNow(), 0.7) * 3 - 1);
            if (enemies.size() < maxEnemies) {

                Vector position;
                if (rand.nextBoolean()) {
                    position = new Vector(0, rand.nextDouble(gameData.getDisplayHeight()));
                }
                else {
                    position = new Vector(rand.nextDouble(gameData.getDisplayWidth()), 0);
                }

            }
        }
    }

    private Enemy createEnemy(Vector position) {
        Enemy enemy = new Enemy();
        enemy.setPosition(position);
        enemy.setColor(1, 1, 1);
        enemy.setPolygonCoordinates(-10,-10,20,0,-10,10,-6,0);
        enemy.setRadius(8);
        enemy.setLayer(Layer.Player);

        world.addEntity(player);

        System.out.println("Added PLAYER");
    }

    private BulletSPI GetBulletSPI() {
        return ServiceLoader.load(BulletSPI.class).findFirst().orElse(null);
    }
}
