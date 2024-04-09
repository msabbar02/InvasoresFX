package com.aetxabao.invasoresfx.sprite;

import com.aetxabao.invasoresfx.util.Rect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static com.aetxabao.invasoresfx.game.AppConsts.*;


/**
 * Enemigos normales que se desplazan de lado a lado y en los extremos descienden.
 */
public class EnemyShip extends AEnemy {

    int N;//ticks para cambio de frame
    int n;
    Rect gameRect;

    public EnemyShip(Rect gameRect, Image img, int N) {
        super(img, ENEMYSHIP_ROWS, ENEMYSHIP_COLS);
        this.gameRect = gameRect;
        xSpeed = ENEMYSHIP_MAX_SPEED;
        x = gameRect.left+width/2;
        this.N = N;
        this.n = 0;
    }

    public void updateFrame(){
        if (++n==N) {
            n = 0;
            currentFrame = ++currentFrame % cols;
        }
    }

    @Override
    public Rect getRect(){
        return new Rect(x, y, (int)(x + ENEMYSHIP_ALFA * width),(int)(y + ENEMYSHIP_ALFA * height));
    }

    @Override
    public void update() {
        if (x > gameRect.right - width - xSpeed || x + xSpeed < gameRect.left) {
            xSpeed = -xSpeed;
            y = y + height;
        }
        x = x + xSpeed;
        updateFrame();
    }

    @Override
    public void draw(GraphicsContext gc) {
        int srcX = currentFrame * width;
        int srcY = 0;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, (int)(x + ENEMYSHIP_ALFA * width), (int)(y + ENEMYSHIP_ALFA * height));
        gc.drawImage(img, src.left, src.top, src.width(), src.height(),
                dst.left, dst.top, dst.width(), dst.height());
    }

}
