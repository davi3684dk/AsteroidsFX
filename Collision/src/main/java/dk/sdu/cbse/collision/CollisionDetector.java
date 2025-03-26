package dk.sdu.cbse.collision;

import dk.sdu.cbse.commoncollision.IEntityCollisionProcessor;
import dk.sdu.cbse.data.*;
import dk.sdu.cbse.services.IEntityPostProcessing;

import java.util.Iterator;
import java.util.ServiceLoader;

public class CollisionDetector implements IEntityPostProcessing {

    public CollisionDetector() {
    }

    @Override
    public void postProcess(Time time, GameData gameData, World world) {
        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities().stream().toList()) {
            for (Entity entity2 : world.getEntities().stream().toList()) {

                if (entity1.getLayer() == entity2.getLayer())
                    continue;

                // CollisionDetection
                if (this.collides(entity1, entity2)) {
                    getCollisionProcessors().forEachRemaining(p -> p.onCollision(time, gameData, world, entity1, entity2));
                    world.removeEntity(entity1);
                    world.removeEntity(entity2);
                    entity1.setDead(true);
                    entity2.setDead(true);
                }
            }
        }
    }

    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getPosition().getX() - (float) entity2.getPosition().getX();
        float dy = (float) entity1.getPosition().getY() - (float) entity2.getPosition().getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    public Iterator<IEntityCollisionProcessor> getCollisionProcessors() {
        return ServiceLoader.load(PluginsLayer.getLayer(), IEntityCollisionProcessor.class).iterator();
    }
}
