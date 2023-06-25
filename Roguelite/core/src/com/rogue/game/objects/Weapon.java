package com.rogue.game.objects;

public class Weapon {
    private float damage;
    private float critChance;
    private float critDamage;
    private float pSpeed;
    private final float MAX_CRITCHANCE = 0.5f;
    private final float MAX_CRITDAMAGE = 3f;
    private final float MAX_PSPEED = 500f;

    public Weapon() {
        this.damage = 50;
        this.critChance = 0;
        this.critDamage = 1;
        this.pSpeed = 200;
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
}
