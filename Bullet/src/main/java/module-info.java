import dk.sdu.cbse.bullet.BulletSystem;
import dk.sdu.cbse.bulletsystem.BulletSPI;
import dk.sdu.cbse.services.IEntityProcessing;

module Bullet {
    requires CommonBullet;
    requires Common;
    provides BulletSPI with BulletSystem;
    provides IEntityProcessing with BulletSystem;
}