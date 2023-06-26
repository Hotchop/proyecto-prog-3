package com.rogue.game.objects;

import com.badlogic.gdx.math.Rectangle;
import com.rogue.game.enums.PlayerAnimationStatus;

import java.util.Random;

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
    private Rectangle hitBox;
    private int direction;
    private int posModifier;
    private PlayerAnimationStatus animationStatus;
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
        this.hitBox = new Rectangle(384,250,16,20);
        this.direction = 1;
        this.posModifier = 0;
        this.animationStatus = PlayerAnimationStatus.IDLE;
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

    public PlayerAnimationStatus getAnimationStatus() {
        return animationStatus;
    }

    public void setAnimationStatus(PlayerAnimationStatus animationStatus) {
        this.animationStatus = animationStatus;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public float getMAX_SPEED() {
        return MAX_SPEED;
    }

    public float getMAX_ARMOR() {
        return MAX_ARMOR;
    }

    public float getMAX_DODGE() {
        return MAX_DODGE;
    }

    public int getLEVEL_XP() {
        return LEVEL_XP;
    }

    public float attacked(float damage){
        Random random = new Random();
        float chance = random.nextFloat(0f,1f);
        if(getDodgeChance() > 0f && chance <= getDodgeChance()){
            return 0f;
        }else{
            return damage * (1f - getArmor());
        }
    }

    public Rectangle isOverlaping(Rectangle r){
        if(hitBox.overlaps(r)){
            return r;
        }
        return new Rectangle(0,0,0,0);
    }
    public void gettingDamage(int damage){
        if(health>=damage) this.health-=damage;
        if(health<damage) this.health=0;
    }
}
