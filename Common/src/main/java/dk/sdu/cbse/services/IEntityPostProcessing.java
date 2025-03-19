package dk.sdu.cbse.services;

import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.Time;
import dk.sdu.cbse.data.World;

public interface IEntityPostProcessing {

    void postProcess(Time time, GameData gameData, World world);
}
