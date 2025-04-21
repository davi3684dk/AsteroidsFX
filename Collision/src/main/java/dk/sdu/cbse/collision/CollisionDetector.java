package dk.sdu.cbse.collision;

import dk.sdu.cbse.commoncollision.IEntityCollisionProcessor;
import dk.sdu.cbse.data.*;
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
                if (this.collides(entity1, entity2)) {
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

    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getPosition().getX() - (float) entity2.getPosition().getX();
        float dy = (float) entity1.getPosition().getY() - (float) entity2.getPosition().getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
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
