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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.rogue.game.enums.PlayerAnimationStatus;
import com.rogue.game.objects.Player;
import com.rogue.game.objects.items.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.rogue.game.objects.Proyectil;
import java.util.ArrayList;
import java.util.List;


import java.text.DecimalFormat;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameScreen implements Screen {
    private final RogueliteGame game;
    private Music battleOST;
    private OrthographicCamera camera;
    Texture walls;
    Texture floor;
    Texture door;
    Texture gate;
    Texture fireball;
    Texture enemyBullet;
    float elapsedTime;
    Player player;
    Item item1,item2,item3;
    private final DecimalFormat decimalFormat;
    static int floorNumber = 0;
    Rectangle exit;
    boolean itemsAreSpawned;
    boolean levelComplete;
    private Texture proyectilTexture;
    private List<Proyectil> proyectiles;
    private float attackSpeed = 0.5f;
    private float lastShotTime = 0.0f;
    private Sound soundProyectil;

    public GameScreen(final RogueliteGame game, Player player){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);
        decimalFormat = new DecimalFormat("0.0");

        this.player = player;
        this.player.setAnimationStatus(PlayerAnimationStatus.IDLE);

        walls = new Texture("Walls.png");
        floor = new Texture("Floor.png");
        door = new Texture("Door Frame.png");
        gate = new Texture("Door Gate.png");

        floorNumber++;
        exit = new Rectangle(384,640,32,32);

        battleOST = Gdx.audio.newMusic(Gdx.files.internal("Battle1.mp3"));
        soundProyectil = Gdx.audio.newSound(Gdx.files.internal("ProyectilSoundP.wav"));
        battleOST.setVolume(0.1f);
        battleOST.setLooping(true);
        battleOST.play();
        proyectilTexture = new Texture("Fireball.png");
        proyectiles = new ArrayList<>();

        itemsAreSpawned = false;
        levelComplete = false;

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        elapsedTime += Gdx.graphics.getDeltaTime();	//Tiempo de juego
        ScreenUtils.clear(0.145f, 0.075f, 0.102f, 1);
		
        camera.update();
		
        game.batch.begin();
        game.batch.draw(floor,160,160);
        game.batch.draw(walls,128,160);
        game.batch.draw(door,370,640);
        if(!levelComplete) game.batch.draw(gate,384,640);
        game.batch.draw((TextureRegion) runAnimation.getKeyFrame(elapsedTime,true),player.getHitBox().x + player.getPosModifier(),player.getHitBox().y,32*player.getDirection(),32);
        recorridoProyectil(elapsedTime);
        for (Proyectil proyectil : proyectiles) {
            game.batch.draw(proyectilTexture, proyectil.getPosicion().x, proyectil.getPosicion().y);
        }
        game.batch.end();

        showPlayerStats();

        if(!itemsAreSpawned){   //Spawneo de items random y sus imagenes
            itemsAreSpawned = true;
            try {
                spawnItems();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }else{
            game.batch.begin();
            game.batch.draw(item1.getSprite(),item1.getItemHitbox().x,item1.getItemHitbox().y);
            game.batch.draw(item2.getSprite(),item2.getItemHitbox().x,item2.getItemHitbox().y);
            game.batch.draw(item3.getSprite(),item3.getItemHitbox().x,item3.getItemHitbox().y);
            game.batch.end();
        }

        //Animacion del personaje
        game.batch.begin();
        if(player.getAnimationStatus() == PlayerAnimationStatus.IDLE){
            game.batch.draw((TextureRegion) game.gameAnimations.playerIdle.getKeyFrame(elapsedTime,true),player.getHitBox().x + player.getPosModifier(),player.getHitBox().y,32*player.getDirection(),32);
        }
        if(player.getAnimationStatus() == PlayerAnimationStatus.RUN){
            game.batch.draw((TextureRegion) game.gameAnimations.playerRun.getKeyFrame(elapsedTime,true),player.getHitBox().x + player.getPosModifier(),player.getHitBox().y,32*player.getDirection(),32);
        }
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D)){
            player.setAnimationStatus(PlayerAnimationStatus.RUN);
        }else{
            player.setAnimationStatus(PlayerAnimationStatus.IDLE);
        }

        //Logica de movimento del Personaje
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

        //Logica de hitbox del personaje

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

        //Logia de pick Up de items
        itemInteraction(item1);
        itemInteraction(item2);
        itemInteraction(item3);

        //Exit Level
        if(player.getHitBox().overlaps(exit) && Gdx.input.isKeyJustPressed(Input.Keys.E) && levelComplete){
            player.getHitBox().x = 384;
            player.getHitBox().y = 250;
            game.setScreen(new LoadingScreen(game,player));
            dispose();
        }

        //Muerte del Jugador
        if(player.getHealth() <= 0){
            player.getHitBox().x = 384;
            player.getHitBox().y = 250;
            game.setScreen(new EndScreen(game,player));
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


    public void spawnItems() throws InstantiationException, IllegalAccessException {
        Random random = new Random();   //Spawnea 3 copias de items random y los posiciona en el mapa
        Item orginal1 = game.itemArrayList.get(random(0,game.itemArrayList.size()-1));
        Item orginal2 = game.itemArrayList.get(random(0,game.itemArrayList.size()-1));
        Item orginal3 = game.itemArrayList.get(random(0,game.itemArrayList.size()-1));

        item1 = orginal1.getClass().newInstance();
        item2 = orginal2.getClass().newInstance();
        item3 = orginal3.getClass().newInstance();

        item1.copy(orginal1);
        item1.getItemHitbox().x = 302;
        item1.getItemHitbox().y = 450;

        item2.copy(orginal2);
        item2.getItemHitbox().x = 384;
        item2.getItemHitbox().y = 450;

        item3.copy(orginal3);
        item3.getItemHitbox().x = 466;
        item3.getItemHitbox().y = 450;
    }

    public void showPlayerStats(){
        game.batch.begin();
        //Player Hp
        game.batch.draw(new Texture("ItemIcons/player_HP_Icon.png"),86,687);
        game.font.draw(game.batch,"Health",86,677,32,Align.right,false);
        game.font.draw(game.batch,(int)player.getHealth() +" / "+(int)player.getMaxHealth(),86,657,32,Align.right,false);

        //Player Xp
        game.batch.draw(new Texture("ItemIcons/player_Xp_Icon.png"),86,597);
        game.font.draw(game.batch,"XP",86,587,32,Align.right,false);
        game.font.draw(game.batch,player.getXp() +" / "+player.getLEVEL_XP(),86,567,32,Align.right,false);

        //Player Level
        game.batch.draw(new Texture("ItemIcons/player_Level_Icon.png"),86,507);
        game.font.draw(game.batch,"Level",86,497,32,Align.right,false);
        game.font.draw(game.batch,""+player.getLevel(),86,477,32,Align.right,false);

        //Floor and Score
        game.batch.draw(new Texture("ItemIcons/floor_Icon.png"),712,687,-32,32);
        game.font.draw(game.batch,"Floor",680,677,32,Align.left,false);
        game.font.draw(game.batch,""+floorNumber,680,657,32,Align.left,false);
        game.font.draw(game.batch,"Score",680,637,32,Align.left,false);
        game.font.draw(game.batch,""+player.getScore(),680,617,32,Align.left,false);

        //Damage
        game.batch.draw(new Texture("ItemIcons/damage_Icon.png"),150,118);
        game.font.draw(game.batch,"Damage",150,108,32,Align.center,false);
        game.font.draw(game.batch,""+(int)player.getWeapon().getDamage(),150,88,32,Align.center,false);

        //CritChance
        game.batch.draw(new Texture("ItemIcons/critChance_Icon.png"),230,118);
        game.font.draw(game.batch,"Crit.Chance",230,108,32,Align.center,false);
        game.font.draw(game.batch,""+decimalFormat.format(player.getWeapon().getCritChance()*100)+"%",230,88,32,Align.center,false);
        if(!CritChanceItem.isSpawneable()) game.font.draw(game.batch,"MAX",230,68,32,Align.center,false);

        //CritDamage
        game.batch.draw(new Texture("ItemIcons/critDamage_Icon.png"),320,118);
        game.font.draw(game.batch,"Crit.Damage",320,108,32,Align.center,false);
        game.font.draw(game.batch,"x"+decimalFormat.format(player.getWeapon().getCritDamage()),320,88,32,Align.center,false);
        if(!CritDamageItem.isSpawneable()) game.font.draw(game.batch,"MAX",320,68,32,Align.center,false);

        //Projectile Speed
        game.batch.draw(new Texture("ItemIcons/pSpeed_Icon.png"),415,118);
        game.font.draw(game.batch,"Attack Speed",415,108,32,Align.center,false);
        game.font.draw(game.batch,""+(int)player.getWeapon().getpSpeed(),415,88,32,Align.center,false);
        if(!PSpeedItem.isSpawneable()) game.font.draw(game.batch,"MAX",415,68,32,Align.center,false);

        //Armor
        game.batch.draw(new Texture("ItemIcons/armor_Icon.png"),490,118);
        game.font.draw(game.batch,"Armor",490,108,32,Align.center,false);
        game.font.draw(game.batch,""+decimalFormat.format(player.getArmor()*100)+"%",490,88,32,Align.center,false);
        if(!ArmorItem.isSpawneable()) game.font.draw(game.batch,"MAX",490,68,32,Align.center,false);

        //Dodge
        game.batch.draw(new Texture("ItemIcons/dodge_Icon.png"),550,118);
        game.font.draw(game.batch,"Dodge",550,108,32,Align.center,false);
        game.font.draw(game.batch,""+decimalFormat.format(player.getDodgeChance()*100)+"%",550,88,32,Align.center,false);
        if(!DodgeItem.isSpawneable()) game.font.draw(game.batch,"MAX",550,68,32,Align.center,false);

        //Speed
        game.batch.draw(new Texture("ItemIcons/speed_Icon.png"),610,118);
        game.font.draw(game.batch,"Speed",610,108,32,Align.center,false);
        game.font.draw(game.batch,""+(int)player.getSpeed(),610,88,32,Align.center,false);
        if(!SpeedItem.isSpawneable()) game.font.draw(game.batch,"MAX",610,68,32,Align.center,false);

        game.batch.end();
    }

    public void itemInteraction(Item item){
        if(player.getHitBox().overlaps(item.getItemHitbox())){
            displayDescription(item);
            if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
                item.pickUp(player);
                clearItems();
            }
        }
    }

    public void displayDescription(Item item){
        game.batch.begin();
        game.font.draw(game.batch,item.getName(),item.getItemHitbox().x-135,item.getItemHitbox().y-20,300f, Align.center,false);
        game.font.draw(game.batch,item.getDecription(),item.getItemHitbox().x-135,item.getItemHitbox().y-40,300f,Align.center,false);
        game.batch.end();
    }

    public void clearItems(){   //Mueve los objetos fuera de la pantalla y abre la puerta
        item1.getItemHitbox().y = -100;
        item2.getItemHitbox().y = -100;
        item3.getItemHitbox().y = -100;

        levelComplete = true;
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
