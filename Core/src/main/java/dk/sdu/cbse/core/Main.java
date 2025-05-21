package dk.sdu.cbse.core;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    public static void main( String[] args )
    {
        launch(Main.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Game game = new Game();
        game.start(stage);
    }
}

