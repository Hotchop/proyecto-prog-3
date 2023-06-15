package com.rogue.game.objects;

public class Player {
    private String name;
    private float health;
    private float maxHealth;
    private float speed;
    private float armor;
    private float dodgeChance;
    private int xp;
    private int level;
    private int score;
    private Weapon weapon;
    private final float MAX_SPEED = 300f;
    private final float MAX_ARMOR = 0.2f;
    private final float MAX_DODGE = 0.1f;
    private final int LEVEL_XP = 100;

    public Player(String name, Weapon weapon) {
        this.name = name;
        this.health = 100;
        this.maxHealth = 100;
        this.speed = 100;
        this.armor = 0;
        this.dodgeChance = 0;
        this.xp = 0;
        this.level = 1;
        this.score = 0;
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public float getDodgeChance() {
        return dodgeChance;
    }

    public void setDodgeChance(float dodgeChance) {
        this.dodgeChance = dodgeChance;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
