package dk.sdu.cbse.collision;

import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.Vector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CollisionDetectorTest {
    CollisionDetector collisionDetector = new CollisionDetector();

    private Entity entity1;
    private Entity entity2;

    @Before
    public void setUp() {
        entity1 = new Entity();
        //Box with size 5
        entity1.setPolygonCoordinates(0,0,5,0,5,5,0,5);
        entity2 = new Entity();
        //Box with size 10
        entity2.setPolygonCoordinates(0,0,10,0,10,10,0,10);
    }

    @Test
    public void testPolygonEdgeCollision() {
        entity2.setPosition(new Vector(2.5,2.5));
        boolean collision = collisionDetector.polygonCollision(entity1, entity2);
        Assert.assertTrue(collision);
    }

    @Test
    public void testPolygonInsideCollision() {
        entity1.setPosition(new Vector(2.5,2.5));
        boolean collision = collisionDetector.polygonCollision(entity1, entity2);
        Assert.assertFalse(collision);
    }

    @Test
    public void testPolygonCornerCollision() {
        entity1.setPosition(new Vector(-5,5));
        boolean collision = collisionDetector.polygonCollision(entity1, entity2);
        Assert.assertTrue(collision);
    }

    @Test
    public void testPolygonOutsideCollision() {
        entity1.setPosition(new Vector(-5.1,-5.1));
        boolean collision = collisionDetector.polygonCollision(entity1, entity2);
        Assert.assertFalse(collision);
    }
}