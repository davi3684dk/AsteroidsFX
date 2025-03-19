import dk.sdu.cbse.bulletsystem.BulletSPI;
import dk.sdu.cbse.player.PlayerMovementSystem;
import dk.sdu.cbse.player.Plugin;
import dk.sdu.cbse.services.IEntityProcessing;
import dk.sdu.cbse.services.IPlugin;

module Player {
    requires Common;
    requires CommonBullet;
    uses BulletSPI;
    provides IPlugin with Plugin;
    provides IEntityProcessing with PlayerMovementSystem;
}