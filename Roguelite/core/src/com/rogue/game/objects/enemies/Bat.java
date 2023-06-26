package com.rogue.game.objects.enemies;

import com.badlogic.gdx.math.Rectangle;
import com.rogue.game.interfaces.IRandomXY;

public class Bat extends Enemy implements IRandomXY {

    public Bat(int posx, int posy) {
        this.health = 50 * Enemy.difficulty;
        this.speed = 80;
        this.damage = 10 * Enemy.difficulty;
        this.xp = 25;
        this.score = 75 * Enemy.difficulty;
        this.hitBox = new Rectangle(generacionAleatoriaX(),generacionAleatoriaY(),40,40);
        this.hitBoxAtaque=new Rectangle(hitBox.x,hitBox.y,20,20);
        this.direction =1;
        this.posModifier = posModifier;
        this.status =true;
    }


    @Override
    public int generacionAleatoriaX(){
        return (int)((Math.random()*(570-190))+190);
    }
    public int generacionAleatoriaY(){
        return (int)((Math.random()*(620-280))+280);
    }
}