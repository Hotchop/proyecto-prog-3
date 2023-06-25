package com.rogue.game;

import com.badlogic.gdx.Gdx;
<<<<<<< Updated upstream
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
=======
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
>>>>>>> Stashed changes
import com.badlogic.gdx.utils.ScreenUtils;
import com.rogue.game.objects.Player;

public class LoadingScreen implements Screen {
    private final RogueliteGame game;
    private final Player player;
    private OrthographicCamera camera;
<<<<<<< Updated upstream
    private Texture door;
    private boolean animationComplete;
    private int animationCoordinate;
    private float elapsedTime;
    private Music loadingOST;
=======
>>>>>>> Stashed changes

    public LoadingScreen(RogueliteGame game, Player player) {
        this.game = game;
        this.player = player;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
<<<<<<< Updated upstream
        loadingOST = Gdx.audio.newMusic(Gdx.files.internal("BattleWon.mp3"));
        loadingOST.setVolume(0.1f);
        loadingOST.setLooping(true);
        loadingOST.play();
        game.font.getData().setScale(0.75f);
        animationComplete = false;
        animationCoordinate = -100;
        door = new Texture("Door Frame.png");

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
=======
>>>>>>> Stashed changes

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
<<<<<<< Updated upstream
        game.font.draw(game.batch, "Score: " + player.getScore(), 350, 350,100, Align.center,false);
        game.font.draw(game.batch, "\nEntering Floor " + (GameScreen.floorNumber + 1), 350, 325,100, Align.center,false);
        game.font.draw(game.batch, "\nPress SPACE to continue", 350, 250,100, Align.center,false);
        game.batch.draw(door,282,400,240,192);
        if(!animationComplete){
            game.batch.draw((TextureRegion) game.gameAnimations.playerRun.getKeyFrame(elapsedTime,true),animationCoordinate,400,128,128);
        }else{
            if(!game.gameAnimations.playerExit.isAnimationFinished(elapsedTime+(1f/0.9f))){
                game.batch.draw((TextureRegion) game.gameAnimations.playerExit.getKeyFrame(elapsedTime,false),336,400,128,128);
            }
        }
        game.batch.end();

        animationCoordinate += 200 * Gdx.graphics.getDeltaTime();
        if(animationCoordinate >= 338){
            animationComplete = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {  ///Cambio de escena al juego
            loadingOST.stop();
=======
        game.font.draw(game.batch, "Su puntuacion es de: " + player.getScore(), 100, 100);
        game.font.draw(game.batch, "\nEntrando al piso " + (GameScreen.floorNumber + 1), 100, 80);
        game.font.draw(game.batch, "\nTap anywhere to continue", 100, 50);
        game.batch.end();

        if (Gdx.input.isTouched()) {  ///Cambio de escena al juego
>>>>>>> Stashed changes
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