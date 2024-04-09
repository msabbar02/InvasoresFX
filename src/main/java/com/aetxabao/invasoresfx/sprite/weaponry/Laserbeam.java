package com.aetxabao.invasoresfx.sprite.weaponry;

import javafx.scene.image.Image;

/**
 * Disparo ascendente sin animación
 */
public class Laserbeam extends AShot {

    // region attributes
    static int ROWS = 1;
    static int COLS = 1;
    static final int MAX_SPEED = 10;
    //endregion

    public Laserbeam(Image img) {
        super(img,ROWS,COLS);
        xSpeed = 0;
        ySpeed = MAX_SPEED;
    }

    public void update() {
        x = x + xSpeed;
        y = y - ySpeed;
    }

}
