package com.rogue.game.objects.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.rogue.game.interfaces.ItemLogic;
import com.rogue.game.objects.Player;

import java.awt.*;

public class Item implements ItemLogic {
    private String name;
    private String decription;
    private Texture sprite;
    private com.badlogic.gdx.math.Rectangle itemHitbox;

    public Item() {
    }

    public Item(String name, String decription, String imagePath, int posX, int posY) {
        this.name = name;
        this.decription = decription;
        this.sprite = new Texture(imagePath);
        this.itemHitbox = new com.badlogic.gdx.math.Rectangle(posX,posY,32,32);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public Texture getSprite() {
        return sprite;
    }

    public void setSprite(Texture sprite) {
        this.sprite = sprite;
    }

    public com.badlogic.gdx.math.Rectangle getItemHitbox() {
        return itemHitbox;
    }

    public void setItemHitbox(com.badlogic.gdx.math.Rectangle itemHitbox) {
        this.itemHitbox = itemHitbox;
    }

    @Override
    public void pickUp(Player player) {

    }

    @Override
    public void copy(Item item) {
        this.name = item.getName();
        this.decription = item.getDecription();
        this.sprite = new Texture(item.getSprite().getTextureData());
        this.itemHitbox = new Rectangle(-50,-50,32,32);
    }
}
