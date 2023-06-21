package com.rogue.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.rogue.game.objects.Player;
import com.rogue.game.objects.Proyectil;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private final RogueliteGame game;
    private Music battleOST;
    private OrthographicCamera camera;
    Texture playerSpriteSheet;
    Texture walls;
    Texture floor;
    Texture door;
    Texture gate;
    Texture fireball;
    Texture enemyBullet;
    TextureRegion[] animationFrames;
    Animation runAnimation;
    Animation idleAnimation;
    float elapsedTime;
    Player player;
    private Texture proyectilTexture;
    private List<Proyectil> proyectiles;
    private float attackSpeed = 0.5f;
    private float lastShotTime = 0.0f;
    private Sound soundProyectil;
    public GameScreen(final RogueliteGame game, Player player){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,1920,1080);

        this.player = player;

        playerSpriteSheet = new Texture("AnimationSheet_Character.png");
        walls = new Texture("Walls.png");
        floor = new Texture("Floor.png");
        door = new Texture("Door Frame.png");
        gate = new Texture("Door Gate.png");
        battleOST = Gdx.audio.newMusic(Gdx.files.internal("Battle1.mp3"));
        soundProyectil = Gdx.audio.newSound(Gdx.files.internal("ProyectilSoundP.wav"));
        battleOST.setVolume(0.1f);
        battleOST.setLooping(true);
        battleOST.play();
        proyectilTexture = new Texture("Fireball.png");
        proyectiles = new ArrayList<>();
        ///Divide Spritesheet en partes iguales en una matriz
        TextureRegion[][] tempFrames = TextureRegion.split(playerSpriteSheet,32,32);

        //Crea un animation con un array de texturas de 4 imagenes
        animationFrames = new TextureRegion[8];

        //Guarda la animacion de correr
        int index = 0;
        for(int j = 0; j < 8; j++){
            animationFrames[index++] = tempFrames[3][j];
        }
        runAnimation = new Animation(1f/16f,animationFrames);

        //Idle Animation
        animationFrames = new TextureRegion[2];
        index = 0;
        for(int j = 0; j < 2; j++){
            animationFrames[index++] = tempFrames[0][j];
        }
        idleAnimation = new Animation<>(1f/8f,animationFrames);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        elapsedTime += Gdx.graphics.getDeltaTime();	//Tiempo de juego
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.begin();
        game.batch.draw(floor,160,160);
        game.batch.draw(walls,128,160);
        game.batch.draw(door,370,640);
        game.batch.draw(gate,384,640);
        game.batch.draw((TextureRegion) runAnimation.getKeyFrame(elapsedTime,true),player.getHitBox().x + player.getPosModifier(),player.getHitBox().y,32*player.getDirection(),32);
        recorridoProyectil(elapsedTime);
        for (Proyectil proyectil : proyectiles) {
            game.batch.draw(proyectilTexture, proyectil.getPosicion().x, proyectil.getPosicion().y);
        }
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.W)) player.getHitBox().y += player.getSpeed() * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            player.getHitBox().x -= player.getSpeed() * Gdx.graphics.getDeltaTime();
            player.setDirection(-1);
            player.setPosModifier(32);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) player.getHitBox().y -= player.getSpeed() * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            player.getHitBox().x += player.getSpeed() * Gdx.graphics.getDeltaTime();
            player.setDirection(1);
            player.setPosModifier(0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dispararProyectil(0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dispararProyectil(0, -1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dispararProyectil(-1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dispararProyectil(1, 0);
        }

        //Area de juego: x - 160 a 608, y - 240 a 640
        if(player.getHitBox().x < 160) player.getHitBox().x = 160;
        if(player.getHitBox().x > 608) player.getHitBox().x = 608;
        if(player.getHitBox().y < 240) player.getHitBox().y = 240;
        if(player.getHitBox().y > 640) player.getHitBox().y = 640;
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
    private void dispararProyectil(int direccionX, int direccionY) {
        if (direccionX != 0 || direccionY != 0) {
            float currentTime = TimeUtils.nanoTime() / 1000000000.0f; // Obtiene el tiempo actual en segundos
            float elapsedTime = currentTime - lastShotTime; // Calcula el tiempo transcurrido desde el último disparo

            if (elapsedTime >= attackSpeed) {
                lastShotTime = currentTime; // Actualiza el momento del último disparo

                float posX = player.getHitBox().x;
                float posY = player.getHitBox().y;
                Vector2 direccionDisparo = new Vector2(direccionX, direccionY).nor();
                Proyectil proyectil = new Proyectil(new Vector2(posX, posY), direccionDisparo, 15.0f);
                soundProyectil.play();
                proyectiles.add(proyectil);
            }
        }
    }
    private void recorridoProyectil(float deltaTime) {
        for (int i = proyectiles.size() - 1; i >= 0; i--) {
            Proyectil proyectil = proyectiles.get(i);
            proyectil.update();
            if (proyectil.getPosicion().x > Gdx.graphics.getWidth() || proyectil.getPosicion().y > Gdx.graphics.getHeight()) {
                proyectiles.remove(i);
            }
        }
    }
    @Override
    public void dispose() {
        playerSpriteSheet.dispose();
        walls.dispose();
        floor.dispose();
        door.dispose();
        gate.dispose();
        fireball.dispose();
        enemyBullet.dispose();
    }
}
