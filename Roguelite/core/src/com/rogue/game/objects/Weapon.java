package com.rogue.game.objects;

public class Weapon {
    private float damage;
    private float critChance;
    private float critDamage;
    private float pSpeed;
    private float pSize;

    private final float MAX_CRITCHANCE = 0.5f;
    private final float MAX_CRITDAMAGE = 3f;
    private final float MAX_PSPEED = 500f;
    private final float MAX_PSIZE = 64f;

    public Weapon() {
        this.damage = 50;
        this.critChance = 0;
        this.critDamage = 0;
        this.pSpeed = 200;
        this.pSize = 32f;
    }
}
