package dk.sdu.cbse.services;

import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;

public interface IPlugin {
    void start(GameData gameData, World world);
    void stop(GameData gameData, World world);
}
