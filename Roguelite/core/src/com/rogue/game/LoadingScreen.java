package com.rogue.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.rogue.game.objects.Player;

public class LoadingScreen implements Screen {
    private final RogueliteGame game;
    private final Player player;
    private OrthographicCamera camera;

    public LoadingScreen(RogueliteGame game, Player player) {
        this.game = game;
        this.player = player;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Su puntuacion es de: " + player.getScore(), 100, 100);
        game.font.draw(game.batch, "\nEntrando al piso " + (GameScreen.floorNumber + 1), 100, 80);
        game.font.draw(game.batch, "\nTap anywhere to continue", 100, 50);
        game.batch.end();

        if (Gdx.input.isTouched()) {  ///Cambio de escena al juego
            game.setScreen(new GameScreen(game, player));
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