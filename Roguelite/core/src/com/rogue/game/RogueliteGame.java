package com.rogue.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rogue.game.objects.items.*;

import java.util.ArrayList;

public class RogueliteGame extends Game {
    protected SpriteBatch batch;
    protected BitmapFont font;
    protected ArrayList<Item> itemArrayList;
    protected GameAnimations gameAnimations;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        gameAnimations = new GameAnimations();

        itemArrayList = new ArrayList<>();
        MaxHealthItem item1 = new MaxHealthItem("Juicy Ribs","Maximum health","ItemSprites/maxHp.png",-50,-50);
        DamageItem item2 = new DamageItem("GUN","Proyectile damage","ItemSprites/damage.png",-50,-50);
        SpeedItem item3 = new SpeedItem("Runner Flag","Movement speed","ItemSprites/speed.png",-50,-50);
        ArmorItem item4 = new ArmorItem("Shield Belt","Damage reduction","ItemSprites/armor.png",-50,-50);
        DodgeItem item5 = new DodgeItem("Slime Grease","Chance to avoid damage","ItemSprites/dodge.png",-50,-50);
        CritChanceItem item6 = new CritChanceItem("Marksman's Eye","Chance of extra damage","ItemSprites/critChance.png",-50,-50);
        CritDamageItem item7 = new CritDamageItem("Hunter Neckless","Damage multiplier","ItemSprites/critDamage.png",-50,-50);
        PSpeedItem item8 = new PSpeedItem("Jet Fuel","Attack speed","ItemSprites/pSpeed.png",-50,-50);
        HealthItem item9 = new HealthItem("HP Orb","Restore health","ItemSprites/health.png",-50,-50);

        itemArrayList.add(item1);
        itemArrayList.add(item2);
        itemArrayList.add(item3);
        itemArrayList.add(item4);
        itemArrayList.add(item5);
        itemArrayList.add(item6);
        itemArrayList.add(item7);
        itemArrayList.add(item8);
        itemArrayList.add(item9);

        this.setScreen(new MainMenuScreen(this));
    }
    public void render(){
        super.render();
    }

    public void dispose(){
        batch.dispose();
        font.dispose();
    }
}
