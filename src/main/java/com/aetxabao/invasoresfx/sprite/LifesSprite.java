package com.aetxabao.invasoresfx.sprite;

import com.aetxabao.invasoresfx.game.AppStatus;
import com.aetxabao.invasoresfx.util.Rect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static com.aetxabao.invasoresfx.game.AppConsts.*;

public class LifesSprite extends ASprite{

    Rect gameRect;
    AppStatus appStatus;

    public LifesSprite(Rect gameRect, Image img, AppStatus appStatus) {
        super(img, LIFES_ROWS, LIFES_COLS);
        this.gameRect = gameRect;
        this.appStatus = appStatus;
        x = gameRect.width()-width;
        y = 0;
        update();
    }

    @Override
    public void update() {
        updateFrame();
    }

    public void updateFrame(){
        currentFrame = 4 - appStatus.getLifes();
        if (currentFrame<0) currentFrame = 0;
    }

    @Override
    public void draw(GraphicsContext gc) {
        int srcX = 0;
        int srcY = currentFrame * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, (int)(x+ LIFES_ALFA * width), (int)(y+ LIFES_ALFA * height));
        gc.drawImage(img, src.left, src.top, src.width(), src.height(),
                          dst.left, dst.top, dst.width(), dst.height());
    }

}
