package dk.sdu.cbse.services;

import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.Time;
import dk.sdu.cbse.data.World;

public interface IEntityProcessing {
    void process(Time time, GameData gameData, World world);
}
