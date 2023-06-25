package com.rogue.game.objects.enemies;

import com.badlogic.gdx.math.Rectangle;
import com.rogue.game.interfaces.IRandomXY;

public class Slime extends Enemy implements IRandomXY {
    public Slime() {
        this.health = 200;
        this.MAX_HEALTH = 300;
        this.speed = 30;
        this.damage = 15;
        this.xp = 25;
        this.score = 100;
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
        return (int)((Math.random()*(620-300))+300);
    }
}
