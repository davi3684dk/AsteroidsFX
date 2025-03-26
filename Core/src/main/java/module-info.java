import dk.sdu.cbse.services.IEntityPostProcessing;
import dk.sdu.cbse.services.IEntityProcessing;
import dk.sdu.cbse.services.IPlugin;

module Core {
    requires javafx.graphics;
    requires Common;
    opens dk.sdu.cbse.core to javafx.graphics;
    uses IEntityProcessing;
    uses IEntityPostProcessing;
    uses IPlugin;
}