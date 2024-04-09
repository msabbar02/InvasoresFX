package com.aetxabao.invasoresfx.game;

import com.aetxabao.invasoresfx.sprite.weaponry.AShot;
import com.aetxabao.invasoresfx.sprite.ASprite;
import com.aetxabao.invasoresfx.sprite.SpriteTemp;
import com.aetxabao.invasoresfx.util.Rect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static com.aetxabao.invasoresfx.game.AppConsts.*;

public class Renderer {

    GraphicsContext gc;
    GameManager gameManager;

    public Renderer(GraphicsContext gc, GameManager gameManager) {
        this.gc = gc;
        this.gameManager = gameManager;
    }

    public void drawStart() {
//        clearScreen(Color.BLACK);

        Image img = COVER_IMAGE;
        Rect src = new Rect(0, 0, (int) (img.getWidth()), (int) (img.getHeight()));
        Rect dst = new Rect(0, 0, gameManager.getWidth(), (int) (BOARD_MARGIN_TOP + gameManager.getHeight()));
        gc.drawImage(img, src.left, src.top, src.width(), src.height(),
                dst.left, dst.top, dst.width(), dst.height());

        double x = gameManager.getWidth() / 6;
        double y = 3.5 * gameManager.getHeight() / 5;
        double ay = gameManager.getHeight() / 10;
        write(x,y,ay, TXT_PRESS_ENTER, TXT_TO_START);

    }

    private void write(double x, double y, double ay, String txt1, String txt2){
        gc.setFill( Color.PURPLE );
        gc.setFont( FONT_HELVETICA_52 );
        if (txt1!=null) gc.fillText( txt1, x, y  );
        if (txt2!=null) gc.fillText( txt2, x, y + ay);
        x -= 5;
        y -= 5;
        gc.setFill( Color.LIMEGREEN );
        if (txt1!=null) gc.fillText( txt1, x, y );
        if (txt2!=null) gc.fillText( txt2, x, y + ay);
    }

    private void clearScreen(Color color) {
        double x,y,w,h, r;
        gc.setFill(color);
        gc.setStroke(Color.BLACK);
        x = 0;
        y = 0;
        w = gameManager.getWidth();
        h = gameManager.getHeight() + BOARD_MARGIN_TOP;
        r = 0;
        gc.fillRect(x, y, w ,h);
        gc.strokeRoundRect(x, y, w ,h, r, r);
    }

    public void drawBoard() {
        clearScreen(Color.BLACK);
        if (gameManager.getShip().isAlive()) {
            gameManager.getShip().draw(gc);
        }
        for (ASprite enemy : gameManager.enemies) {
            enemy.draw(gc);
        }
        for (AShot AShot : gameManager.shotsDown) {
            AShot.draw(gc);
        }
        for (AShot AShot : gameManager.shotsUp) {
            AShot.draw(gc);
        }
        for (SpriteTemp temp : gameManager.temps) {
            temp.draw(gc);
        }
        // Vidas
        gameManager.lifesSprite.draw(gc);
        double x,y;
        // Puntos
        x = 2  * gameManager.getWidth() / 5;
        y = MARGIN_TOP;
        gc.setFill( Color.LIMEGREEN );
        gc.setFont( FONT_HELVETICA_16 );
        gc.fillText( TXT_POINTS + " : " + gameManager.score , x, y );
        // Nivel
        x = MARGIN_LEFT;
        y = MARGIN_TOP;
        gc.setFill( Color.LIMEGREEN );
        gc.setFont( FONT_HELVETICA_16 );
        String txt = TXT_LEVEL.substring(0,1).toUpperCase() + TXT_LEVEL.substring(1).toLowerCase();
        gc.fillText( txt + " : " + gameManager.getLevel(), x, y );

    }

    public void drawYouWon() {
        drawFinish(TXT_YOU_WON);
    }

    public void drawYouLost() {
        drawFinish(TXT_YOU_LOST);
    }

    private void drawFinish(String txt) {
        double x,y,ay;

        x = 2.0 * gameManager.getWidth() / 8;
        y = gameManager.getHeight() / 3;
        ay = 0;
        write(x,y,ay,txt,null);

        pressEnterToContinue();
    }

    public void drawNewLevel(int level) {
        double x,y,ay;

        x = 2.5 * gameManager.getWidth() / 8;
        y = gameManager.getHeight() / 3;
        ay = 0;
        write(x,y,ay, TXT_LEVEL + " " + level,null);

        pressEnterToContinue();
    }

    public void drawOneLessLife() {
        double x,y,ay;

        x = 2.0 * gameManager.getWidth() / 8;
        y = gameManager.getHeight() / 3;
        ay = 0;
        write(x,y,ay, TXT_YOU_CAN,null);

        pressEnterToContinue();
    }

    private void pressEnterToContinue() {
        double x,y,ay;
        x = gameManager.getWidth() / 6;
        y = 3.5 * gameManager.getHeight() / 5;
        ay = gameManager.getHeight() / 10;
        write(x,y,ay, TXT_PRESS_ENTER, TXT_TO_CONTINUE);
    }

}
