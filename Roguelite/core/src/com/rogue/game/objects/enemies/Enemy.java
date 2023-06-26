package com.rogue.game.objects.enemies;
import com.badlogic.gdx.math.Rectangle;

public abstract class Enemy  {
    public float health;
    public float MAX_HEALTH;
    public float speed;
    public float damage;
    public float xp;
    public float score;
    public static float difficulty;
    public Rectangle hitBox;
    public Rectangle hitBoxAtaque;
    public int direction;
    public int posModifier;
    public boolean status;


    public Enemy() {
    }

    public Rectangle getHitBoxAtaque() {
        return hitBoxAtaque;
    }

    public void setHitBoxAtaque(Rectangle hitBoxAtaque) {
        this.hitBoxAtaque = hitBoxAtaque;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    public void setMAX_HEALTH(float MAX_HEALTH) {
        this.MAX_HEALTH = MAX_HEALTH;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getXp() {
        return xp;
    }

    public void setXp(float xp) {
        this.xp = xp;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public static float getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(float difficulty) {
        Enemy.difficulty = difficulty;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getPosModifier() {
        return posModifier;
    }

    public void setPosModifier(int posModifier) {
        this.posModifier = posModifier;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
        if(!this.status){
            this.hitBox.x+=1000;
            this.hitBoxAtaque.x=10000;
        }
    }
}

