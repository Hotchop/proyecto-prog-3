package com.rogue.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.rogue.game.objects.Player;

public class EndScreen implements Screen {
    private final RogueliteGame game;
    private final Player player;

    boolean gameSaved;
    private OrthographicCamera camera;

    public EndScreen(RogueliteGame game, Player player) {
        this.game = game;
        this.player = player;
        this.gameSaved = false;
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
        game.font.draw(game.batch, "YOU DIE", 400, 400,0, Align.center,false);
        game.font.draw(game.batch, "Su puntuacion llego a:" + player.getScore(), 400, 300,0, Align.center,false);
        game.font.draw(game.batch, "Llego hasta nivel " + player.getLevel(), 400, 200,0, Align.center,false);
        game.font.draw(game.batch, "Tap anywhere to restart", 400, 50,0,Align.center,false);
        game.batch.end();

        if(!gameSaved){
            Games save = new Games(player.getName(), player.getLevel(), player.getScore(), GameScreen.floorNumber);
            Json json = new Json();
            json.toJson(save, FileHandle.tempFile("Roguelite/assets/Archivos"));
            gameSaved = false;
        }

        if (Gdx.input.isTouched()) {  ///Cambio de escena al juego
            game.setScreen(new MainMenuScreen(game));
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