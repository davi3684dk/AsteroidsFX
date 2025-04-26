package dk.sdu.cbse.core;

import dk.sdu.cbse.data.*;
import dk.sdu.cbse.services.IEntityPostProcessing;
import dk.sdu.cbse.services.IEntityProcessing;
import dk.sdu.cbse.services.IPlugin;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

/**
 * Hello world!
 *
 */
public class Main extends Application
{
    private final Pane gameWindow = new Pane();
    private final GameData gameData = new GameData();
    private final World world = new World();
    private final HashMap<Entity, Polygon> entityPolygonHashMap = new HashMap<>();
    private final Text text = new Text(10, 20, "FPS: 10 \n Bullets: 0");

    private final Time time = new Time();

    private final int fps = 120;
    private double lastFrame;

    public static void main( String[] args )
    {
        launch(Main.class);
    }

    @Override
    public void start(Stage stage) throws Exception {

        text.setFill(Color.WHITE);

        gameWindow.setPrefSize(gameData.getDisplayWidth(),gameData.getDisplayHeight());
        gameWindow.getChildren().add(text);
        Scene scene = new Scene(gameWindow);

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.A)) {
                gameData.getInput().setKey(Input.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.D)) {
                gameData.getInput().setKey(Input.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.W)) {
                gameData.getInput().setKey(Input.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getInput().setKey(Input.SPACE, true);
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.A)) {
                gameData.getInput().setKey(Input.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.D)) {
                gameData.getInput().setKey(Input.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.W)) {
                gameData.getInput().setKey(Input.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getInput().setKey(Input.SPACE, false);
            }
        });


        //Run start method for all plugins
        getPluginServices().forEach(p -> p.start(gameData, world));

        initRenderer();

        stage.setScene(scene);
        stage.setTitle("ASTEROIDS");
        stage.show();
    }

    double lastUIRefresh;
    double avg;
    double n = 1;
    private void initRenderer() {
        gameWindow.setStyle("-fx-background-color: black;");
        double nt = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                double nowSeconds = (now - nt) / 1000000000d;

                if (nowSeconds - lastFrame < 1d/fps) {
                    return;
                }
                time.setNow(nowSeconds);
                time.setDeltaTime(time.getNow() - lastFrame);

                lastFrame = nowSeconds;

                GetEntityProcessingServices().forEach(e -> e.process(time, gameData, world));

                avg = avg * (n-1)/n + (1 / time.getDeltaTime()) / n;
                n++;

                if (time.getNow() - lastUIRefresh > 0.5) {
                    lastUIRefresh = time.getNow();
                    text.setText("FPS: " + (int)avg + " SCORE: " + gameData.getScore());
                    avg = 0;
                    n = 1;
                }
                draw();

                gameData.getInput().update();

                GetEntityPostProcessingServices().forEach(e -> e.postProcess(time, gameData, world));
            }
        }.start();
    }

    private void draw() {
        Iterator<Map.Entry<Entity, Polygon>> iterator = entityPolygonHashMap.entrySet().iterator();

        iterator.forEachRemaining(entry -> {
            if(!world.getEntities().contains(entry.getKey())){
                Polygon removedPolygon = entry.getValue();
                iterator.remove();
                gameWindow.getChildren().remove(removedPolygon);
            }
        });


        for (Entity entity : world.getEntities()) {
            Polygon polygon = entityPolygonHashMap.get(entity);

            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygon.setFill(Color.TRANSPARENT);
                polygon.setStroke(Color.color(entity.getColor()[0], entity.getColor()[1], entity.getColor()[2]));
                entityPolygonHashMap.put(entity,polygon);
                gameWindow.getChildren().add(polygon);
            }

            polygon.setTranslateX(entity.getPosition().getX());
            polygon.setTranslateY(entity.getPosition().getY());
            polygon.setRotate(entity.getRotation());
        }
    }

    private List<IPlugin> plugins;
    private List<IEntityProcessing> entityProcessings;
    private List<IEntityPostProcessing> entityPostProcessings;

    private List<IPlugin> getPluginServices() {
        if (plugins == null) {
            plugins = new ArrayList<>();
            ServiceLoader.load(IPlugin.class).forEach(plugins::add);
        }
        return plugins;
    }

    private List<IEntityProcessing> GetEntityProcessingServices() {
        if (entityProcessings == null) {
            entityProcessings = new ArrayList<>();
            ServiceLoader.load(IEntityProcessing.class).forEach(entityProcessings::add);
        }
        return entityProcessings;
    }

    private List<IEntityPostProcessing> GetEntityPostProcessingServices() {
        if (entityPostProcessings == null) {
            entityPostProcessings = new ArrayList<>();
            ServiceLoader.load(IEntityPostProcessing.class).forEach(entityPostProcessings::add);
        }
        return entityPostProcessings;
    }
}

