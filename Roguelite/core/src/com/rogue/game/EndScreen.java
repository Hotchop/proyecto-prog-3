package com.rogue.game;

import com.badlogic.gdx.Gdx;
<<<<<<< Updated upstream
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.*;
=======
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
>>>>>>> Stashed changes
import com.rogue.game.objects.Player;

public class EndScreen implements Screen {
    private final RogueliteGame game;
    private final Player player;
<<<<<<< Updated upstream
    boolean gameSaved;
    private OrthographicCamera camera;
    private float elapsedTime;
    private Music Gameover;
=======

    boolean gameSaved;
    private OrthographicCamera camera;

>>>>>>> Stashed changes
    public EndScreen(RogueliteGame game, Player player) {
        this.game = game;
        this.player = player;
        this.gameSaved = false;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
<<<<<<< Updated upstream
        Gameover = Gdx.audio.newMusic(Gdx.files.internal("GameOver.mp3"));
        Gameover.setVolume(0.3f);
        Gameover.setLooping(true);
        Gameover.play();
=======
>>>>>>> Stashed changes
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
<<<<<<< Updated upstream
        elapsedTime += Gdx.graphics.getDeltaTime();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.font.getData().setScale(3);
        game.batch.begin();
        GlyphLayout menuLayout = new GlyphLayout(game.font, "Has muerto");
        game.font.draw(game.batch, menuLayout, (Gdx.graphics.getWidth() - menuLayout.width) / 2, (Gdx.graphics.getHeight() - menuLayout.height) / 2 + 350);
        game.font.getData().setScale(1);
        game.batch.draw((TextureRegion) game.gameAnimations.playerDie.getKeyFrame(elapsedTime,false),336,400,128,128);
        GlyphLayout lvlReached = new GlyphLayout(game.font, "Nivel MAX: " + player.getLevel());
        game.font.draw(game.batch, lvlReached, (Gdx.graphics.getWidth() - menuLayout.width) / 2 - 100, (Gdx.graphics.getHeight() - menuLayout.height) / 2- 60);

        GlyphLayout scoreReached = new GlyphLayout(game.font, "Score: " + player.getScore());
        game.font.draw(game.batch, scoreReached, (Gdx.graphics.getWidth() - menuLayout.width) / 2 - 100, (Gdx.graphics.getHeight() - menuLayout.height) / 2 - 120);
        game.font.draw(game.batch, "Presiona Enter para volver al menu", 400, 50,0,Align.center,false);
        if(!gameSaved){
            System.out.println("Guardado con exito");
            Datos save = new Datos(player.getName(), player.getLevel(), player.getScore());
            Json json = new Json();
            json.toJson(save, FileHandle.tempFile("Roguelite/assets/Archivos"));
            gameSaved = true;

        }
        game.batch.end();


        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {  ///Cambio de escena al juego
            Gameover.stop();
=======

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
>>>>>>> Stashed changes
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