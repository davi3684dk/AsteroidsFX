import dk.sdu.cbse.bulletsystem.BulletSPI;
import dk.sdu.cbse.enemy.EnemyMovementSystem;
import dk.sdu.cbse.enemy.Plugin;
import dk.sdu.cbse.services.IEntityProcessing;
import dk.sdu.cbse.services.IPlugin;

module Enemy {
    requires Common;
    requires CommonBullet;
    uses BulletSPI;
    provides IPlugin with Plugin;
    provides IEntityProcessing with EnemyMovementSystem;
}