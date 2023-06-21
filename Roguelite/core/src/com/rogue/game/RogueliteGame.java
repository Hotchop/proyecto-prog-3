package com.rogue.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RogueliteGame extends Game {
    protected SpriteBatch batch;
    protected BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("Minecraft.fnt"), Gdx.files.internal("Minecraft.png"), false);
        font.setColor(Color.WHITE);
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
