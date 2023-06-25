package com.rogue.game.objects.mapstuff.traps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {
    Rectangle hitbox;
    Texture textura;
    float originX;

    public Bullet(float x, float y) {
        textura=new Texture("bullet.png");
        hitbox=new Rectangle(x,y+20,6,6);
        originX=x+10;
    }
    public void reinit(float y){
        this.hitbox.x=originX;
        this.hitbox.y=y;
    }

    public float getOriginX() {
        return originX;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
        hitbox.y+=8;
    }

    public Texture getTextura() {
        return textura;
    }


    public void setTextura(Texture textura) {
        this.textura = textura;
    }
}
