package dk.sdu.cbse.commoncollision;

import dk.sdu.cbse.data.Entity;
import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.Time;
import dk.sdu.cbse.data.World;

public interface IEntityCollisionProcessor {
    void onCollision(Time time, GameData gameData, World world, Entity entity1, Entity entity2);
}
