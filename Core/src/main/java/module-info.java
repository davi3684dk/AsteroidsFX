import dk.sdu.cbse.services.IEntityPostProcessing;
import dk.sdu.cbse.services.IEntityProcessing;
import dk.sdu.cbse.services.IPlugin;

module Core {
    requires javafx.graphics;
    requires Common;
    requires CommonScore;
    opens dk.sdu.cbse.core to javafx.graphics;
    exports dk.sdu.cbse.core;
    uses IEntityProcessing;
    uses IEntityPostProcessing;
    uses IPlugin;
    uses dk.sdu.cbse.commonscore.ScoreSPI;
}