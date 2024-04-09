package com.aetxabao.invasoresfx.game;

import com.aetxabao.invasoresfx.util.Exec;
import com.aetxabao.invasoresfx.util.Rect;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import static com.aetxabao.invasoresfx.game.AppConsts.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class AppInvasoresFx extends Application {

    //region atributtes
    private static GraphicsContext gc;
    private static GameManager gameManager;
    private static Rect gameRect;
    private long deltaTime;

    private static Renderer renderer;

    private static BooleanProperty wPressed = new SimpleBooleanProperty();
    private static BooleanProperty aPressed = new SimpleBooleanProperty();
    private static BooleanProperty sPressed = new SimpleBooleanProperty();
    private static BooleanProperty dPressed = new SimpleBooleanProperty();
    private static BooleanProperty shotReleased = new SimpleBooleanProperty();

    private static AppStatus appStatus = new AppStatus();

    private static Logger log = Logger.getLogger(AppInvasoresFx.class);

    // endregion

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        System.setProperty(SYSTEM_PROPERTY, Exec.getGitUserName());
        PropertyConfigurator.configure(LOG4J_PROPERTIES);
        log.info("-".repeat(80));

        setup(stage);

        setEventActions(stage.getScene());

        new AnimationTimer() {
            public void handle(long currentNanoTime) {

                if ( (currentNanoTime - deltaTime) / 1000000000.0 > (1.0/GAME_SPEED) ) {
                    deltaTime = currentNanoTime;
                    switch (appStatus.getValue()) {
                        case E_APP_START:
                            gameStart();
                            break;
                        case E_APP_PLAYING:
                            gamePlaying();
                            break;
                        case E_APP_NEWLEVEL:
                            gameNewLevel();
                            break;
                        case E_APP_ONELESSLIFE:
                            gameOneLessLife();
                            break;
                        case E_APP_WON:
                        case E_APP_LOST:
                            gameFinish();
                            break;
                        case E_APP_QUIT:
                            log.info("ESC");
                            stage.close();
                            break;
                    }
                }

            }
        }.start();

        stage.show();
    }

    private void setup(Stage stage){
        stage.setTitle( APP_TITLE );
        stage.setResizable(false);

        Group root = new Group();
        Scene scene = new Scene( root );
        stage.setScene( scene );

        stage.getIcons().addAll(ICON_16, ICON_32, ICON_64, ICON_128);

        Canvas canvas = new Canvas( BOARD_WIDTH, BOARD_HEIGHT);
        root.getChildren().add( canvas );

        gc = canvas.getGraphicsContext2D();

        gameRect = new Rect(0, BOARD_MARGIN_TOP, BOARD_WIDTH, BOARD_HEIGHT);

        gameManager = new GameManager(gameRect, appStatus);

        renderer = new Renderer(gc, gameManager);

        final long startNanoTime = System.nanoTime();

        deltaTime = startNanoTime;
    }

    private static void setEventActions(Scene scene){
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()){
                case W -> wPressed.set(true);
                case A -> aPressed.set(true);
                case S -> sPressed.set(true);
                case D -> dPressed.set(true);
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()){
                case W -> wPressed.set(false);
                case A -> aPressed.set(false);
                case S -> sPressed.set(false);
                case D -> dPressed.set(false);
                case ENTER, SPACE -> enterOrSpaceAction();
                case ESCAPE -> appStatus.quit();
            }
        });
    }

    private static void enterOrSpaceAction() {
        switch (appStatus.getValue()){
            case E_APP_START -> gameManager.start();
            case E_APP_PLAYING -> shotReleased.set(true);
            case E_APP_NEWLEVEL -> gameManager.nextLevel();
            case E_APP_ONELESSLIFE -> gameManager.sameLevel();
            case E_APP_WON, E_APP_LOST -> gameManager.finish();
        }
    }

    private static void gameStart() {
        renderer.drawStart();
    }

    private static void gamePlaying() {
        // render game
        renderer.drawBoard();
        // user input
        processInput();
        // update game
        gameManager.updateGame();
    }

    private static void gameNewLevel() {
        renderer.drawNewLevel(appStatus.getLevel()+1);
    }

    private static void gameOneLessLife() {
        renderer.drawOneLessLife();
    }

    private static void gameFinish() {
        switch (appStatus.getValue()){
            case E_APP_WON:
                renderer.drawYouWon();
                break;
            case E_APP_LOST:
                renderer.drawYouLost();
                break;
        }
    }

    private static void processInput() {
        gameManager.getShip().waiting();
        if (wPressed.getValue()) gameManager.getShip().moveUp();
        if (sPressed.getValue()) gameManager.getShip().moveDown();
        if (aPressed.getValue()) gameManager.getShip().moveLeft();
        if (dPressed.getValue()) gameManager.getShip().moveRight();
        if (shotReleased.getValue()){
            gameManager.shot();
            shotReleased.set(false);
        }
    }

}
