package dk.sdu.cbse.player;

import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.Layer;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IPlugin;

public class Plugin implements IPlugin {
    protected static Player player;

    @Override
    public void start(GameData gameData, World world) {
        player = new Player();
        player.getPosition().setX(gameData.getDisplayWidth() / 2d);
        player.getPosition().setY(gameData.getDisplayHeight() / 2d);
        player.setColor(1, 1, 1);
        player.setPolygonCoordinates(-10,-10,20,0,-10,10,-6,0);
        player.setRadius(8);
        player.setLayer(Layer.Player);

        world.addEntity(player);

        System.out.println("Added PLAYER");
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }
}
