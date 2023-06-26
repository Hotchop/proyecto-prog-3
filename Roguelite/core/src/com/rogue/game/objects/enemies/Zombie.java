package com.rogue.game.objects.enemies;

import com.badlogic.gdx.math.Rectangle;
import com.rogue.game.interfaces.IRandomXY;

public class Zombie extends Enemy implements IRandomXY {
    public Zombie() {
        this.health = 50*Enemy.difficulty;
        this.MAX_HEALTH = 120;
        this.speed = 40;
        this.damage = 15*Enemy.difficulty;
        this.xp = 25;
        this.score = 100*Enemy.difficulty;
        this.hitBox = new Rectangle(generacionAleatoriaX(),generacionAleatoriaY(),45,45);
        this.direction =1;
        this.hitBoxAtaque=new Rectangle(hitBox.x+16,hitBox.y+10,12,24);
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
