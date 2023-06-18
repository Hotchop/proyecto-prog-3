package com.rogue.game.objects.items;

import com.badlogic.gdx.graphics.Texture;

public abstract class Item {
    private String name;
    private String decription;
    private Texture sprite;

    public Item(String name, String decription, Texture sprite) {
        this.name = name;
        this.decription = decription;
        this.sprite = sprite;
    }

    public void pickUp(){

    }
}
