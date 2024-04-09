package com.aetxabao.invasoresfx.sprite;

import com.aetxabao.invasoresfx.util.Rect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class ASprite {

    // region attributes
    protected int rows;
    protected int cols;

    protected int x;
    protected int y;
    protected int xSpeed;
    protected int ySpeed;
    protected Image img;

    protected int currentFrame;
    protected int width;
    protected int height;
    protected int side;
    // endregion

    public ASprite(Image img, int rows, int cols){
        this.img = img;
        this.rows = rows;
        this.cols = cols;
        this.side = 0;
        if (img!=null) {
            this.width = (int) (img.getWidth() / cols);
            this.height = (int) (img.getHeight() / rows);
        }
    }

	public void setPos(int x, int y){
		this.x = x;
		this.y = y;
	}

    public int getXSpeed(){
        return xSpeed;
    }

    public void setXSpeed(int xSpeed){
        this.xSpeed = xSpeed;
    }

    public int getYSpeed(){
        return ySpeed;
    }

    public void setYSpeed(int ySpeed){
        this.ySpeed = ySpeed;
    }

    public Rect getRect(){
        return new Rect(x,y,x+width,y+height);
    }

    public Rect[] getRects(){
        Rect[] rects = new Rect[1];
        rects[0] = getRect();
        return rects;
    }

    public boolean collides(ASprite sprite){
        for(Rect rA : this.getRects()){
            for(Rect rB : sprite.getRects()){
                if (Rect.intersects(rA,rB) || rB.contains(rA) || rA.contains(rB)) return true;
            }
        }
        return false;
    }

    public void draw(GraphicsContext gc) {
        int srcX = currentFrame * width;
        int srcY = side*height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x+width, y+height);
        gc.drawImage(img, src.left, src.top, src.width(), src.height(),
                dst.left, dst.top, dst.width(), dst.height());
    }

    public abstract void update();

}
