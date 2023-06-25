package com.rogue.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighscoreScreen implements Screen {
    private static List<Datos> listaData;
    private final RogueliteGame game;
    private OrthographicCamera camera;

    public static List<Datos> getListaData() {
        return listaData;
    }

    public static void setListaData(List<Datos> listaData) {
        HighscoreScreen.listaData = listaData;
    }

    public RogueliteGame getGame() {
        return game;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public Integer getDist() {
        return dist;
    }

    public void setDist(Integer dist) {
        this.dist = dist;
    }

    private Integer dist;
    public HighscoreScreen(RogueliteGame game) {
        this.game = game;
        listaData = new ArrayList<>();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        ///Lectura json
        try {
            FileHandle file = Gdx.files.local("datos.json");
            String jsonString = file.readString();

            // Crear un objeto JsonReader para analizar el JSON
            JsonReader jsonReader = new JsonReader();
            JsonValue jsonValue = jsonReader.parse(jsonString);

            // Crear un objeto Json para deserializar el JSON en un ArrayList de tipo Datos
            Json json = new Json();
            listaData = json.fromJson(ArrayList.class, Datos.class, jsonValue.toString());
        }catch(GdxRuntimeException ex){};

    }
    public static void agregarDato(Datos nuevoDato) {
        ///Agregar dato al json
        if (listaData.size() == 10) {
            Datos ultimoDato = listaData.get(9);
            System.out.println(nuevoDato.compareTo(ultimoDato));
            if (nuevoDato.compareTo(ultimoDato) < 0) {
                System.out.println("entra");
                listaData.remove(9); // Eliminar el último elemento
                listaData.add(nuevoDato); // Agregar el nuevo elemento
                Collections.sort(listaData); // Ordenar la lista
                System.out.println(listaData);
            }
        } else {
            listaData.add(nuevoDato);
            Collections.sort(listaData);
        }
    }
    ///Usado en endscreen
    public void actualizarLista() {
        Collections.sort(listaData);
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        dist = 60;
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
