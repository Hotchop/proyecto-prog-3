package com.rogue.game.objects.mapstuff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.rogue.game.interfaces.IRandomXY;

import java.util.ArrayList;

public class Objecto implements IRandomXY {
    private Texture texture;
    private Rectangle hitbox;
    private boolean spawned;
    private static ArrayList<Texture> texturas=new ArrayList<>();
    {
        texturas.add(new Texture("Rock1_grass_shadow4.png"));
        texturas.add(new Texture("Rock4_3_no_shadow.png"));
        texturas.add(new Texture("Rock8_ground_shadow_dark3.png"));
    }
    public Objecto() {
        this.texture = texturas.get((int) (Math.random()*(3)));
        this.hitbox = new Rectangle(generacionAleatoriaX(),generacionAleatoriaY(),24,24);
        spawned=false;
    }


    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean isSpawned() {
        return spawned;
    }
    public int generacionAleatoriaX(){
        int mapa=(int)(Math.random()*2);
        if(mapa==0){
            return (int)(Math.random()*(350-230)+230);
        }else{
            return (int)(Math.random()*(530-420)+420);
        }
    }
    public int generacionAleatoriaY(){
        int mapa=(int)(Math.random()*2);
        if(mapa==0){
            return (int)((Math.random()*(420-240))+240);
        }else{
            return (int)((Math.random()*(600-490))+490);
        }
    }
    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

}
