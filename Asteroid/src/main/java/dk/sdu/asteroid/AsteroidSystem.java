package dk.sdu.asteroid;

import dk.sdu.cbse.commoncollision.IEntityCollisionProcessor;
import dk.sdu.cbse.commonasteroid.Asteroid;
import dk.sdu.cbse.commonasteroid.AsteroidSPI;
import dk.sdu.cbse.data.*;
import dk.sdu.cbse.services.IEntityProcessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AsteroidSystem implements IEntityProcessing, AsteroidSPI, IEntityCollisionProcessor {
    Random rand = new Random();

    @Override
    public void process(Time time, GameData gameData, World world) {
        List<SplitterAsteroid> asteroidList = world.getEntities(SplitterAsteroid.class);

        for (Asteroid asteroid : asteroidList) {
            Vector posChange = new Vector(
                    Math.cos(Math.toRadians(asteroid.getRotation())) * asteroid.getSpeed() * time.getDeltaTime(),
                    Math.sin(Math.toRadians(asteroid.getRotation())) * asteroid.getSpeed() * time.getDeltaTime()
            );
            Vector newPos = asteroid.getPosition().add(posChange);

            if (newPos.getX() < 0)
                newPos.setX(gameData.getDisplayWidth() + newPos.getX());
            if (newPos.getY() < 0)
                newPos.setY(gameData.getDisplayHeight() + newPos.getY());
            if (newPos.getX() > gameData.getDisplayWidth())
                newPos.setX(gameData.getDisplayWidth() - newPos.getX());
            if (newPos.getY() > gameData.getDisplayHeight())
                newPos.setY(gameData.getDisplayHeight() - newPos.getY());

            asteroid.setPosition(newPos);
        }

        if (asteroidList.size() < 20) {

            Vector position;

            if (rand.nextBoolean()) {
                position = new Vector(0, rand.nextDouble(gameData.getDisplayHeight()));
            }
            else {
                position = new Vector(rand.nextDouble(gameData.getDisplayWidth()), 0);
            }

            world.addEntity(createAsteroid(position, rand.nextDouble(360)));
        }
    }

    private double[] proceduralAsteroid(int hp) {
        double[] points = new double[rand.nextInt(6, 8) * 2];

        double rad = 2 * Math.PI / points.length;

        for (int i = 0; i < points.length; i+=2) {
            points[i] = Math.cos(rad * i) * rand.nextDouble(5,10) * Math.sqrt(hp);
            points[i+1] = Math.sin(rad * i) * rand.nextDouble(5,10) * Math.sqrt(hp);
        }

        return points;
    }

    private SplitterAsteroid createAsteroid(Vector position, double rotation, int hp) {
        SplitterAsteroid asteroid = new SplitterAsteroid();

        double[] points = proceduralAsteroid(hp);
        asteroid.setPolygonCoordinates(points);

        java.awt.Color color = java.awt.Color.getHSBColor(hp / 3f, 1, 1);

        asteroid.setColor(color.getRed()/255d, color.getGreen()/255d, color.getBlue()/255d);
        asteroid.setPosition(position);
        asteroid.setRotation(rotation);
        asteroid.setSpeed(rand.nextDouble(50, 100));
        asteroid.setLayer(Layer.Enemy);
        asteroid.setHp(hp);

        double radius = Arrays.stream(points).map(Math::abs).max().getAsDouble();
        asteroid.setRadius(radius);

        return asteroid;
    }

    @Override
    public Entity createAsteroid(Vector position, double rotation) {
        return createAsteroid(position, rotation, 3);
    }

    @Override
    public void onCollision(Time time, GameData gameData, World world, Entity entity1, Entity entity2) {
        if (entity1 instanceof SplitterAsteroid)  {
            for (Entity ent : splitAsteroid((SplitterAsteroid) entity1, entity2.getRotation())) {
                world.addEntity(ent);
            }
        }
        else if (entity2 instanceof SplitterAsteroid)  {
            for (Entity ent : splitAsteroid((SplitterAsteroid) entity2, entity1.getRotation())) {
                world.addEntity(ent);
            }
        }
    }

    private List<SplitterAsteroid> splitAsteroid(SplitterAsteroid asteroid, double rotation) {
        if (asteroid.getHp() <= 0)
            return new ArrayList<>();

        double maxPoint = Arrays.stream(asteroid.getPolygonCoordinates()).map(Math::abs).max().getAsDouble();
        double scale = (maxPoint / 10);

        ArrayList<SplitterAsteroid> asteroids = new ArrayList<>();

        asteroids.add(
                createAsteroid(asteroid.getPosition(), rotation + 25, asteroid.getHp() - 1)
        );
        asteroids.add(
                createAsteroid(asteroid.getPosition(), rotation - 25, asteroid.getHp() - 1)
        );

        return asteroids;
    }
}
