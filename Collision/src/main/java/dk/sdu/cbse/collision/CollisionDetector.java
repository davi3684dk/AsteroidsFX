package dk.sdu.cbse.collision;

import dk.sdu.cbse.commoncollision.IEntityCollisionProcessor;
import dk.sdu.cbse.data.*;
import dk.sdu.cbse.data.Vector;
import dk.sdu.cbse.services.IEntityPostProcessing;

import java.util.*;

public class CollisionDetector implements IEntityPostProcessing {

    public CollisionDetector() {
    }

    @Override
    public void postProcess(Time time, GameData gameData, World world) {
        // two for loops for all entities in the world
        List<Entity> entities = new ArrayList<>(world.getEntities());
        Set<Entity> toRemove = new HashSet<>();

        for (int i = 0; i < entities.size(); i++) {
            Entity entity1 = entities.get(i);
            if (toRemove.contains(entity1)) continue;

            for (int j = i + 1; j < entities.size(); j++) {
                Entity entity2 = entities.get(j);
                if (toRemove.contains(entity2)) continue;

                if (entity1.getLayer() == entity2.getLayer())
                    continue;

                // CollisionDetection
                if (this.polygonCollision(entity1, entity2)) {
                    getCollisionProcessors().forEach(p -> p.onCollision(time, gameData, world, entity1, entity2));
                    entity1.setDead(true);
                    entity2.setDead(true);
                    toRemove.add(entity1);
                    toRemove.add(entity2);
                }
            }
        }

        toRemove.forEach(world::removeEntity);
    }

    private Vector[] getWorldCoordinates(Entity entity) {
        Vector[] coordinates = new Vector[entity.getPolygonCoordinates().length / 2];

        for (int i = 0; i < entity.getPolygonCoordinates().length; i += 2) {
            double x = entity.getPolygonCoordinates()[i];
            double y = entity.getPolygonCoordinates()[i + 1];

            double rotation = Math.toRadians(entity.getRotation());
            Vector position = entity.getPosition();

            double rotatedX = x * Math.cos(rotation) - y * Math.sin(rotation);
            double rotatedY = x * Math.sin(rotation) + y * Math.cos(rotation);

            coordinates[i/2] = new Vector(rotatedX + position.getX(), rotatedY + position.getY());
        }
        return coordinates;
    }

    public boolean polygonCollision(Entity entity1, Entity entity2) {
        Vector[] coordinates1 = getWorldCoordinates(entity1);
        Vector[] coordinates2 = getWorldCoordinates(entity2);

        int next = 0;
        for (int current = 0; current < coordinates1.length; current++) {

            next = current + 1;
            if (next == coordinates1.length)
                next = 0;

            boolean collision = polygonLineCollision(coordinates2, coordinates1[current], coordinates1[next]);
            if (collision)
                return true;
        }

        return false;
    }

    public boolean polygonLineCollision(Vector[] coordinates, Vector point1, Vector point2) {
        int next = 0;
        for (int current = 0; current < coordinates.length; current++) {
            next = current + 1;
            if (next == coordinates.length)
                next = 0;

            if (lineLineCollision(point1, point2, coordinates[current], coordinates[next]))
                return true;
        }

        return false;
    }

    public boolean lineLineCollision(Vector line1Point1, Vector line1Point2, Vector line2Point1, Vector line2Point2) {
        double x1 = line1Point1.getX();
        double y1 = line1Point1.getY();
        double x2 = line1Point2.getX();
        double y2 = line1Point2.getY();

        double x3 = line2Point1.getX();
        double y3 = line2Point1.getY();
        double x4 = line2Point2.getX();
        double y4 = line2Point2.getY();

        double denominator = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);

        //Parallel lines
        if (denominator == 0)
            return false;

        double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denominator;
        double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denominator;

        return (ua >= 0 && ua <= 1) && (ub >= 0 && ub <= 1);
    }

    List<IEntityCollisionProcessor> collisionProcessors;
    public List<IEntityCollisionProcessor> getCollisionProcessors() {
        if (collisionProcessors == null) {
            collisionProcessors = new ArrayList<>();
            ServiceLoader.load(IEntityCollisionProcessor.class).forEach(collisionProcessors::add);
        }
        return collisionProcessors;
    }
}
