import dk.sdu.cbse.collision.CollisionDetector;
import dk.sdu.cbse.services.IEntityPostProcessing;

module Collision {
    uses dk.sdu.cbse.commoncollision.IEntityCollisionProcessor;
    requires Common;
    requires CommonCollision;
    provides IEntityPostProcessing with CollisionDetector;
}