package com.rogue.game.objects.mapstuff.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.rogue.game.interfaces.IRandomXY;

public class Trap implements IRandomXY {
    Rectangle hitbox;
    Texture textura;
    boolean atacando;
    Bullet bala;
    boolean direc;
    boolean spawned;
    int damage;

    public Trap() {
        hitbox=new Rectangle(generacionAleatoriaX(),generacionAleatoriaY(),16,24);
        textura=new Texture("disparada.png");
        atacando=true;
        bala=new Bullet(hitbox.x,hitbox.y);
        damage=10;
    }

    public int generacionAleatoriaX(){
        int mapa=(int)(Math.random()*2);
        if(mapa==0){
            direc=true;
            return (int)((Math.random()*(180-160))+160);
        }else{
            direc=false;
            return (int)((Math.random()*(605-589))+589);
        }
    }
    public int generacionAleatoriaY(){
        return (int)((Math.random()*(625+1-260))+260);
    }
    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public Texture getTextura() {
        return textura;
    }

    public void setTextura(Texture textura) {
        this.textura = textura;
    }

    public boolean isAtacando() {
        return atacando;
    }

    public void setAtacando(boolean atacando) {
        this.atacando = atacando;
    }

    public Bullet getBala() {
        return bala;
    }

    public void setBala(Bullet bala) {
        this.bala = bala;
    }
    public int direccion(){
        if(direc==true){
            return 1;
        }else{
            return -1;
        }
    }

    public boolean isDirec() {
        return direc;
    }

    public void setDirec(boolean direc) {
        this.direc = direc;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


    public boolean isSpawned() {
        return spawned;
    }


    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }
}
