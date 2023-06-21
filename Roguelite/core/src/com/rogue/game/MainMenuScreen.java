package com.rogue.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.rogue.game.objects.Player;
import com.rogue.game.objects.Weapon;

import javax.swing.*;

public class MainMenuScreen implements Screen {
    private final RogueliteGame game;
    private OrthographicCamera camera;
    protected Player player;
    protected Weapon playerWeapon;
    private Texture backgroundTexture;
    private Music menuOst;
    private BitmapFont fontMenu;
    private static final Color DEFAULT_COLOR = Color.YELLOW;
    private static final Color HIGHLIGHT_COLOR = Color.WHITE;
    private int selectedOption = 0;
    public MainMenuScreen(final RogueliteGame game){
        this.game = game;
        backgroundTexture = new Texture("FondoMenu2.jpg");
        camera = new OrthographicCamera();
        fontMenu = new BitmapFont(Gdx.files.internal("FontTitulo.fnt"), Gdx.files.internal("FontTitulo.png"), false);
        game.font.getData().setScale(1);
        camera.setToOrtho(false,800,800);
        menuOst = Gdx.audio.newMusic(Gdx.files.internal("MainMenuOST.mp3"));
        menuOst.setVolume(0.3f);
        menuOst.setLooping(true);
        menuOst.play();
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
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        GlyphLayout menuLayout = new GlyphLayout(fontMenu, "UTN Roguelite");
        fontMenu.draw(game.batch, menuLayout, (Gdx.graphics.getWidth() - menuLayout.width) / 2, Gdx.graphics.getHeight() - menuLayout.height - 50);

        GlyphLayout buttonLayout = new GlyphLayout(game.font, "Nuevo juego");
        Color buttonColor = (selectedOption == 0) ? HIGHLIGHT_COLOR : DEFAULT_COLOR;
        game.font.setColor(buttonColor);
        game.font.draw(game.batch, buttonLayout, (Gdx.graphics.getWidth() - buttonLayout.width) / 2, (Gdx.graphics.getHeight() - buttonLayout.height) / 2);
        
        GlyphLayout buttonLayout2 = new GlyphLayout(game.font, "PlaceHolder");
        Color buttonColor2 = (selectedOption == 1) ? HIGHLIGHT_COLOR : DEFAULT_COLOR;
        game.font.setColor(buttonColor2);
        game.font.draw(game.batch, buttonLayout2, (Gdx.graphics.getWidth() - buttonLayout2.width) / 2, ((Gdx.graphics.getHeight() - buttonLayout.height) / 2) - 60);

        game.batch.end();



        ///Cambiar opcion del menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {

            selectedOption = Math.max(0, selectedOption - 1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {

            selectedOption = Math.min(1, selectedOption + 1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (selectedOption) {
                case 0:
                    menuOst.stop();
                    playerWeapon = new Weapon();
                    String name = JOptionPane.showInputDialog("Nombre de jugador");
                    player = new Player(name, playerWeapon);
                    game.setScreen(new GameScreen(game, player));
                    dispose();
                    break;
                case 1:
                    ///Logica de la 2da opcion, en caso de querer agregar mas opciones se agregan + cases
                    break;
            }
        }
    }
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
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
