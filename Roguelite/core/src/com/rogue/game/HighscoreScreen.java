package com.rogue.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

public class HighscoreScreen implements Screen {
    private List<Datos> listaData;
    private final RogueliteGame game;
    private float elapsedTime;
    private OrthographicCamera camera;
    private Integer dist;
    public HighscoreScreen(RogueliteGame game) {
        this.game = game;
        listaData = new ArrayList<>();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        Datos a = new Datos("Juanceto01",1,1000);
        Datos b = new Datos("Juanceto02",3,3000);
        Datos c = new Datos("Juanceto03",7,7000);
        Datos d = new Datos("Juanceto04",10,10000);
        Datos e = new Datos("Juanceto05",12,12000);
        Datos f = new Datos("Juanceto06",13,13000);
        Datos g = new Datos("Juanceto07",2,2000);
        Datos h = new Datos("Juanceto08",15,15000);
        Datos i = new Datos("Juanceto09",19,19000);
        Datos j = new Datos("Juanceto10",1,1000);
        listaData.add(a);
        listaData.add(b);
        listaData.add(c);
        listaData.add(d);
        listaData.add(e);
        listaData.add(f);
        listaData.add(g);
        listaData.add(h);
        listaData.add(i);
        listaData.add(j);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        dist = 60;
        elapsedTime += Gdx.graphics.getDeltaTime();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.getData().setScale(2.5F);
        game.font.draw(game.batch, "HIGHSCORE", 400, 750,0, Align.center,false);
        game.font.getData().setScale(0.7F);
        for(Datos muestra: listaData)
        {
            game.font.draw(game.batch,muestra.toString(),30,700-dist);
            dist+=60;
        }
        game.batch.end();
        if ((Gdx.input.isKeyJustPressed(Input.Keys.ENTER))|| (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) || (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE))) {  ///Cambio de escena al juego
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
