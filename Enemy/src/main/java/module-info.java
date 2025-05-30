import dk.sdu.cbse.bulletsystem.BulletSPI;
import dk.sdu.cbse.commoncollision.IEntityCollisionProcessor;
import dk.sdu.cbse.enemy.EnemyMovementSystem;
import dk.sdu.cbse.enemy.Plugin;
import dk.sdu.cbse.services.IEntityProcessing;
import dk.sdu.cbse.services.IPlugin;

module Enemy {
    requires CommonBullet;
    requires CommonCollision;
    requires Common;
    requires CommonScore;
    uses BulletSPI;
    uses dk.sdu.cbse.commonscore.ScoreSPI;
    provides IPlugin with Plugin;
    provides IEntityProcessing with EnemyMovementSystem;
    provides IEntityCollisionProcessor with EnemyMovementSystem;
}