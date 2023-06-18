package com.rogue.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.rogue.game.objects.Player;
import com.rogue.game.objects.Weapon;

import javax.swing.*;

public class MainMenuScreen implements Screen {
    private final RogueliteGame game;
    private OrthographicCamera camera;
    protected Player player;
    protected Weapon playerWeapon;

    public MainMenuScreen(final RogueliteGame game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Menu Place Holder", 100, 100);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 50);
        game.batch.end();

        if(Gdx.input.isTouched()){  ///Cambio de escena al juego
            playerWeapon = new Weapon();
            String name = JOptionPane.showInputDialog("Nombre de jugador");
            player = new Player(name,playerWeapon);
            game.setScreen(new GameScreen(game,player));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
