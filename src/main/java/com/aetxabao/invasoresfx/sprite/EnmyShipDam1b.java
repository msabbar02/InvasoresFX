package com.aetxabao.invasoresfx.sprite;
import com.aetxabao.invasoresfx.util.Rect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static com.aetxabao.invasoresfx.game.AppConsts.*;

    /**
     * Enemigos normales con un comportamiento diferente.
     */
    public class EnmyShipDam1b extends AEnemy {

        Rect gameRect;
        int direction;
        int verticalSpeed;
        int oscillationAmplitude;
        double oscillationFrequency;
        double time;

        public EnmyShipDam1b(Rect gameRect, Image img) {
            super(img, ENEMYSHIP_ROWS, ENEMYSHIP_COLS);
            this.gameRect = gameRect;
            xSpeed = ENEMYSHIP_MAX_SPEED;
            ySpeed = 0;
            direction = 1;
            int ENEMYSHIP_VERTICAL_SPEED = 0;
            verticalSpeed = ENEMYSHIP_VERTICAL_SPEED;
            time = 0;
        }

        public EnmyShipDam1b(Image img, int rows, int cols) {
            super(img, rows, cols);
        }

        @Override
        public Rect getRect() {
            return new Rect(x, y, (int) (x + ENEMYSHIP_ALFA * width), (int) (y + ENEMYSHIP_ALFA * height));
        }

        @Override
        public void update() {
            // Update horizontal position
            x += direction * xSpeed;

            // Update vertical position using oscillatory motion
            y = (int) (gameRect.centerY() + oscillationAmplitude * Math.sin(oscillationFrequency * time));
            time += 0.1; // Increment time for oscillation

            // Check if enemy reaches the edges of the game area
            if (x <= gameRect.left || x + width >= gameRect.right) {
                direction *= -1; // Change direction when reaching the edge
            }
        }

        @Override
        public void draw(GraphicsContext gc) {
            int srcX = currentFrame * width;
            int srcY = 0;
            Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
            Rect dst = new Rect(x, y, (int) (x + ENEMYSHIP_ALFA * width), (int) (y + ENEMYSHIP_ALFA * height));
            gc.drawImage(img, src.left, src.top, src.width(), src.height(),
                    dst.left, dst.top, dst.width(), dst.height());
        }


    }

