package com.rogue.game.objects;

import java.util.Random;

public class Weapon {
    private float damage;
    private float critChance;
    private float critDamage;
    private float pSpeed;
    private final float MAX_CRITCHANCE = 0.5f;
    private final float MAX_CRITDAMAGE = 3f;
    private final float MAX_PSPEED = 0.2f;

    public Weapon() {
        this.damage = 50f;
        this.critChance = 0.05f;
        this.critDamage = 1.1f;
        this.pSpeed = 0.5f;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getCritChance() {
        return critChance;
    }

    public void setCritChance(float critChance) {
        this.critChance = critChance;
    }

    public float getCritDamage() {
        return critDamage;
    }

    public void setCritDamage(float critDamage) {
        this.critDamage = critDamage;
    }

    public float getpSpeed() {
        return pSpeed;
    }

    public void setpSpeed(float pSpeed) {
        this.pSpeed = pSpeed;
    }

    public float getMAX_CRITCHANCE() {
        return MAX_CRITCHANCE;
    }

    public float getMAX_CRITDAMAGE() {
        return MAX_CRITDAMAGE;
    }

    public float getMAX_PSPEED() {
        return MAX_PSPEED;
    }

    public float attack(){
        Random random = new Random();
        float chance = random.nextFloat(0f,1f);
        if(chance > 0f && chance <= getCritChance()){
            return damage * critDamage;
        }else{
            return damage;
        }
    }
}
