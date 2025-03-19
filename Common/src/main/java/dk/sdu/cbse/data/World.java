package dk.sdu.cbse.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class World {
    private final List<Entity> entities = new ArrayList<>();

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Entity> getEntities(Layer layer) {
        return entities.stream().filter(e -> e.getLayer() == layer).toList();
    }

    public <E extends Entity> List<E> getEntities(Class<E>... types) {
        List<E> r = new ArrayList<>();
        List<Class<E>> t = Arrays.stream(types).toList();

        for (Entity entity : entities) {
            if (t.contains(entity.getClass())) {
                r.add((E)entity);
            }
        }
        return r;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
}
