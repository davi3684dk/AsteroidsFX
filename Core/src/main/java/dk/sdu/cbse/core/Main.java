package dk.sdu.cbse.core;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application
{
    public static void main( String[] args )
    {
        launch(Main.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CoreConfig.class);

        Game game = context.getBean(Game.class);
        game.start(stage);
    }
}

