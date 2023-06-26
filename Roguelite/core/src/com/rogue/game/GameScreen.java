package com.rogue.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.rogue.game.enums.PlayerAnimationStatus;
import com.rogue.game.objects.Player;
import com.rogue.game.objects.enemies.*;
import com.rogue.game.objects.items.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.rogue.game.objects.Proyectil;
import com.rogue.game.objects.mapstuff.Objecto;
import com.rogue.game.objects.mapstuff.Objetos;
import com.rogue.game.objects.mapstuff.traps.Trampas;
import com.rogue.game.objects.mapstuff.traps.Trap;
import java.text.DecimalFormat;
import java.util.Random;
import static com.badlogic.gdx.math.MathUtils.random;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GameScreen implements Screen {
    private final RogueliteGame game;
    private static ArrayList<Music> canciones=new ArrayList<>();
    {
        canciones.add(Gdx.audio.newMusic(Gdx.files.internal("Battle1.mp3")));
        canciones.add(Gdx.audio.newMusic(Gdx.files.internal("Battle2.mp3")));
        canciones.add(Gdx.audio.newMusic(Gdx.files.internal("Battle3.mp3")));
        canciones.add(Gdx.audio.newMusic(Gdx.files.internal("Battle4.mp3")));
        canciones.add(Gdx.audio.newMusic(Gdx.files.internal("Battle5.mp3")));
    }
    private Music battleOST;
    private OrthographicCamera camera;
    Texture walls;
    Texture floor;
    Texture door;
    Texture gate;
    float elapsedTime;
    Player player;
    boolean playerGetsDamage;
    Item item1,item2,item3;
    private final DecimalFormat decimalFormat;
    public static int floorNumber;
    Rectangle exit;
    boolean itemsAreSpawned;
    boolean levelComplete;
    private Texture proyectilTexture;
    private List<Proyectil> proyectiles;
    private float attackSpeed = 0.5f;
    private float attackSpeedTrap = 5.0f;
    private float lastShotTime = 0.0f;
    private float lastShotTimeTrap = 0.0f;
    private  float lastAttackTime=0.0f;
    private Sound soundProyectil;
    Sound soundDamagePlayer;
    Objetos objetos;
    Trampas traps;
    Rectangle objCol;
    Rectangle objCol2;
    Rectangle enemiesCol;
    ArrayList<Objecto>hitboxes;
    ArrayList<Trap>hitboxes2;
    Boolean objSpawned;
    Boolean trapsSpawned;
    private List<Enemy>z;
    ArrayList<Rectangle>hitboxesAtaque;
    HashSet<Enemy> deadEnemies;
    boolean enemyGetsShot;
    Item itemR;
    boolean hpItemSpawned;
    ShapeRenderer shapeRenderer;
    public GameScreen(final RogueliteGame game, Player player){
        this.game = game;

        shapeRenderer=new ShapeRenderer();
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

        game.font.getData().setScale(0.5f);

        this.battleOST = canciones.get((int) (Math.random()*(5)));
        soundProyectil = Gdx.audio.newSound(Gdx.files.internal("ProyectilSoundP.wav"));
        soundDamagePlayer = Gdx.audio.newSound(Gdx.files.internal("damagedSound.mp3"));
        battleOST.setVolume(0.1f);
        battleOST.setLooping(true);
        battleOST.play();
        proyectilTexture = new Texture("Fireball.png");
        proyectiles = new ArrayList<>();

        objCol=new Rectangle();
        objCol2=new Rectangle();
        objSpawned=false;
        trapsSpawned=false;

        z=new ArrayList<>((List<Enemy>) generadorEnemigos());

        enemiesCol=new Rectangle();
        enemyGetsShot=false;
        hitboxesAtaque=new ArrayList<>();
        for(Enemy e: z){
            hitboxesAtaque.add(e.getHitBoxAtaque());
        }
        itemR=new HealthItem("HP Orb","Restore health","ItemSprites/health.png", -100, -100);
        hpItemSpawned=false;

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
        game.batch.end();

        showPlayerStats();  //UI de Stats del Jugador

        spawnObstacles(); //Spawneo de objetos y trampas

        //Logica trampas
        checkBulletsCollisions();
        trapsState();

        if(!noEnemies()) {
            itemInteraction(item1);
            itemInteraction(item2);
            itemInteraction(item3);
        }

        //AI del enemigo y render de sprites
        enemyLogic();

        //Logica de spawneo de items
        itemLogic();

        //Render de proyectiles
        proyectileRender();

        //Movimiento, ataque y animaciones del jugador
        playerLogic();

        //Muerte del Jugador
        if(player.getHealth() <= 0){
            battleOST.stop();
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

    public void playerPosition(){
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            if(!player.getHitBox().overlaps(objCol)){
                player.getHitBox().y += player.getSpeed() * Gdx.graphics.getDeltaTime();
            }
            if(player.getHitBox().overlaps(objCol)){
                player.getHitBox().y -= player.getSpeed() * Gdx.graphics.getDeltaTime();
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            if(!player.getHitBox().overlaps(objCol)){
                player.getHitBox().x -= player.getSpeed() * Gdx.graphics.getDeltaTime();
                player.setDirection(-1);
                player.setPosModifier(32);
            }
            if(player.getHitBox().overlaps(objCol)){
                player.getHitBox().x += player.getSpeed() * Gdx.graphics.getDeltaTime();
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            if(!player.getHitBox().overlaps(objCol)){
                player.getHitBox().y -= player.getSpeed() * Gdx.graphics.getDeltaTime();
            }
            if(player.getHitBox().overlaps(objCol)){
                player.getHitBox().y += (player.getSpeed() * Gdx.graphics.getDeltaTime());
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            if(!player.getHitBox().overlaps(objCol) ){
                player.getHitBox().x += player.getSpeed() * Gdx.graphics.getDeltaTime();
                player.setDirection(1);
                player.setPosModifier(0);
            }
            if(player.getHitBox().overlaps(objCol) ){
                player.getHitBox().x-=player.getSpeed()*Gdx.graphics.getDeltaTime();
            }
        }
    }
    public void playerLogic(){
        //Animacion del personaje
        game.batch.begin();
        if(player.getAnimationStatus() == PlayerAnimationStatus.IDLE){
            game.batch.draw((TextureRegion) game.gameAnimations.playerIdle.getKeyFrame(elapsedTime,true),player.getHitBox().x + player.getPosModifier()-8,player.getHitBox().y,32*player.getDirection(),32);
            if(playerGetsDamage){
                soundDamagePlayer.play(0.2f);
                game.batch.draw((TextureRegion) game.gameAnimations.playerIdleDamage.getKeyFrame(elapsedTime,true),player.getHitBox().x + player.getPosModifier()-8,player.getHitBox().y-16,32*player.getDirection(),32);
                playerGetsDamage=false;
            }
        }
        if(player.getAnimationStatus() == PlayerAnimationStatus.RUN){
            game.batch.draw((TextureRegion) game.gameAnimations.playerRun.getKeyFrame(elapsedTime,true),player.getHitBox().x + player.getPosModifier()-8,player.getHitBox().y,32*player.getDirection(),32);
            if(playerGetsDamage){
                soundDamagePlayer.play(0.2f);
                game.batch.draw((TextureRegion) game.gameAnimations.playerRunDamage.getKeyFrame(elapsedTime,true),player.getHitBox().x + player.getPosModifier()-8,player.getHitBox().y,32*player.getDirection(),32);
                playerGetsDamage=false;
            }
        }
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D)){
            player.setAnimationStatus(PlayerAnimationStatus.RUN);
        }else{
            player.setAnimationStatus(PlayerAnimationStatus.IDLE);
        }

        //Logica de movimento del Personaje y sus colisiones
        checkPlayerCollisions();
        playerPosition();
        //Exit Level
        if(player.getHitBox().overlaps(exit) && levelComplete){
            game.batch.begin();
            game.font.draw(game.batch,"[E]",400-16,620,32,Align.center,false);
            game.batch.end();
            if(Gdx.input.isKeyJustPressed(Input.Keys.E) ){
                battleOST.stop();
                player.getHitBox().x = 384;
                player.getHitBox().y = 250;
                game.setScreen(new LoadingScreen(game,player));
                dispose();
            }
        }

        //Area de juego: x - 160 a 608, y - 240 a 640
        if(player.getHitBox().x < 160) player.getHitBox().x = 160;
        if(player.getHitBox().x > 608) player.getHitBox().x = 608;
        if(player.getHitBox().y < 240) player.getHitBox().y = 240;
        if(player.getHitBox().y > 640) player.getHitBox().y = 640;

        //Logica de ataque del personaje

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


    }
    public void checkPlayerCollisions(){
        for(Objecto r: hitboxes) {
            if(player.getHitBox().overlaps(r.getHitbox())) {
                objCol = player.isOverlaping(r.getHitbox());
                break;
            }
        }
        for(Trap t: hitboxes2){
            if(player.getHitBox().overlaps(t.getHitbox())){
                objCol=player.isOverlaping(t.getHitbox());
                break;
            }
        }
    }
    public void checkEnemiesCollisions(){
        for(Enemy e: z){
            for(Trap t: hitboxes2){
                if(e.getHitBox().overlaps(t.getHitbox())){
                    enemiesCol=t.getHitbox();
                    break;
                }
            }
            for(Objecto o: hitboxes){
                if(e.getHitBox().overlaps(o.getHitbox())){
                    enemiesCol=o.getHitbox();
                    break;
                }
            }
        }
    }
    public void checkBulletsCollisions(){
        for(Objecto r: hitboxes) {
            for(Trap t: hitboxes2){
                if(t.getBala().getHitbox().overlaps(r.getHitbox())) {
                    objCol2 = r.getHitbox();
                    break;

                }
            }
        }
    }
    private void trapsState() {
        float currentTime = TimeUtils.nanoTime() / 1000000000.0f; // Obtiene el tiempo actual en segundos
        float elapsedTime = currentTime - lastShotTimeTrap; // Calcula el tiempo transcurrido desde el último disparo
        for(Trap t: hitboxes2) {
            if(noEnemies()){

                if (elapsedTime >= attackSpeedTrap) {
                    lastShotTimeTrap = currentTime; // Actualiza el momento del último disparo
                    if (!t.isAtacando()) {
                        t.setAtacando(true);
                    }
                }
                if(t.getBala().getHitbox().overlaps(player.getHitBox())){
                    player.gettingDamage(t.getDamage());
                    playerGetsDamage=true;
                    t.getBala().reinit(t.getBala().getHitbox().y);
                    t.setAtacando(false);
                }
                if (t.getBala().getHitbox().overlaps(objCol2) || t.getBala().getHitbox().overlaps(new Rectangle(620, t.getBala().getHitbox().y, 16, 100)) || t.getBala().getHitbox().overlaps(new Rectangle(140, t.getHitbox().y, 16, 100))) {
                    t.getBala().reinit(t.getBala().getHitbox().y);
                    t.setAtacando(false);
                }

                if (t.isAtacando()) {
                    t.getBala().getHitbox().x += 90 * Gdx.graphics.getDeltaTime() * (t.direccion());
                }
            }else{
                t.getBala().reinit(t.getBala().getHitbox().y);
            }
        }
    }
    public void itemLogic(){
        //Logica de pick Up de items

        if(hpItemSpawned) itemInteractionR(itemR);



        ///Spawn items random
        if(!noEnemies()) {
            if (!itemsAreSpawned) {   //Spawneo de items random y sus imagenes
                itemsAreSpawned = true;
                try {
                    spawnItems();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                game.batch.begin();
                game.batch.draw(item1.getSprite(), item1.getItemHitbox().x, item1.getItemHitbox().y);
                game.batch.draw(item2.getSprite(), item2.getItemHitbox().x, item2.getItemHitbox().y);
                game.batch.draw(item3.getSprite(), item3.getItemHitbox().x, item3.getItemHitbox().y);
                game.batch.end();

            }
        }

        //Spawn hp item
        if(hpItemSpawned){
            game.batch.begin();
            game.batch.draw(itemR.getSprite(),itemR.getItemHitbox().x,itemR.getItemHitbox().y);
            game.batch.end();
        }
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
        game.batch.draw(new Texture("ItemIcons/damage_Icon.png"),128,118);
        game.font.draw(game.batch,"Damage",128,108,32,Align.center,false);
        game.font.draw(game.batch,""+(int)player.getWeapon().getDamage(),128,88,32,Align.center,false);

        //CritChance
        game.batch.draw(new Texture("ItemIcons/critChance_Icon.png"),215,118);
        game.font.draw(game.batch,"Crit.Chance",215,108,32,Align.center,false);
        game.font.draw(game.batch,""+decimalFormat.format(player.getWeapon().getCritChance()*100)+"%",215,88,32,Align.center,false);
        if(!CritChanceItem.isSpawneable()) game.font.draw(game.batch,"MAX",215,68,32,Align.center,false);

        //CritDamage
        game.batch.draw(new Texture("ItemIcons/critDamage_Icon.png"),320,118);
        game.font.draw(game.batch,"Crit.Damage",320,108,32,Align.center,false);
        game.font.draw(game.batch,"x"+decimalFormat.format(player.getWeapon().getCritDamage()),320,88,32,Align.center,false);
        if(!CritDamageItem.isSpawneable()) game.font.draw(game.batch,"MAX",320,68,32,Align.center,false);

        //Projectile Speed
        game.batch.draw(new Texture("ItemIcons/pSpeed_Icon.png"),433,118);
        game.font.draw(game.batch,"Attack Speed",433,108,32,Align.center,false);
        game.font.draw(game.batch,""+decimalFormat.format(100f-(player.getWeapon().getpSpeed()*100)),433,88,32,Align.center,false);
        if(!PSpeedItem.isSpawneable()) game.font.draw(game.batch,"MAX",433,68,32,Align.center,false);

        //Armor
        game.batch.draw(new Texture("ItemIcons/armor_Icon.png"),520,118);
        game.font.draw(game.batch,"Armor",520,108,32,Align.center,false);
        game.font.draw(game.batch,""+decimalFormat.format(player.getArmor()*100)+"%",520,88,32,Align.center,false);
        if(!ArmorItem.isSpawneable()) game.font.draw(game.batch,"MAX",520,68,32,Align.center,false);

        //Dodge
        game.batch.draw(new Texture("ItemIcons/dodge_Icon.png"),580,118);
        game.font.draw(game.batch,"Dodge",580,108,32,Align.center,false);
        game.font.draw(game.batch,""+decimalFormat.format(player.getDodgeChance()*100)+"%",580,88,32,Align.center,false);
        if(!DodgeItem.isSpawneable()) game.font.draw(game.batch,"MAX",580,68,32,Align.center,false);

        //Speed
        game.batch.draw(new Texture("ItemIcons/speed_Icon.png"),640,118);
        game.font.draw(game.batch,"Speed",640,108,32,Align.center,false);
        game.font.draw(game.batch,""+(int)player.getSpeed(),640,88,32,Align.center,false);
        if(!SpeedItem.isSpawneable()) game.font.draw(game.batch,"MAX",640,68,32,Align.center,false);

        game.batch.end();
    }
    public void itemInteraction(Item item){
        if(player.getHitBox().overlaps(item.getItemHitbox())){
            displayDescription(item);
            if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
                Sound pickUp = Gdx.audio.newSound(Gdx.files.internal("Sounds/item_pickUp.mp3"));
                pickUp.play();
                item.pickUp(player);
                clearItems();
            }
        }
    }
    public void itemInteractionR(Item item){
        if(player.getHitBox().overlaps(item.getItemHitbox())){
            displayDescription(item);
            if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
                Sound pickUp = Gdx.audio.newSound(Gdx.files.internal("Sounds/item_pickUp.mp3"));
                pickUp.play();
                item.pickUp(player);
                clearHPItem();
                hpItemSpawned=false;
            }
        }
    }
    public void displayDescription(Item item){
        game.batch.begin();
        game.font.draw(game.batch,item.getName(),item.getItemHitbox().x-135,item.getItemHitbox().y-20,300f, Align.center,false);
        game.font.draw(game.batch,item.getDecription(),item.getItemHitbox().x-135,item.getItemHitbox().y-40,300f,Align.center,false);
        game.font.draw(game.batch,"[E]",item.getItemHitbox().x-135,item.getItemHitbox().y-60,300f,Align.center,false);
        game.batch.end();
    }
    public void clearItems(){   //Mueve los objetos fuera de la pantalla y abre la puerta
        item1.getItemHitbox().y = -100;
        item2.getItemHitbox().y = -100;
        item3.getItemHitbox().y = -100;

        levelComplete = true;
    }
    public void clearHPItem(){
        itemR.getItemHitbox().y=-100;
    }
	private void dispararProyectil(int direccionX, int direccionY) {
        if (direccionX != 0 || direccionY != 0) {
            float currentTime = TimeUtils.nanoTime() / 1000000000.0f; // Obtiene el tiempo actual en segundos
            float elapsedTime = currentTime - lastShotTime; // Calcula el tiempo transcurrido desde el último disparo
            if (elapsedTime >= player.getWeapon().getpSpeed()) {
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
    private void proyectileRender(){
        game.batch.begin();
        recorridoProyectil(elapsedTime);
        for (Proyectil proyectil : proyectiles) {
            game.batch.draw(proyectilTexture, proyectil.getPosicion().x, proyectil.getPosicion().y);
        }
        game.batch.end();
    }
    private void recorridoProyectil(float deltaTime) {
        for (int i = proyectiles.size() - 1; i >= 0; i--) {
            Proyectil proyectil = proyectiles.get(i);
            proyectil.update();
            if (proyectil.getPosicion().x > 618 || proyectil.getPosicion().x < 160 || proyectil.getPosicion().y > 660 || proyectil.getPosicion().y < 230) {
                proyectiles.remove(i);
            }
        }
    }
    public void spawnObstacles(){
        if(!objSpawned) {
            objSpawned=true;
            spawnObj();
        }else{
            game.batch.begin();
            for(int i=0; i<hitboxes.size();i++){
                game.batch.draw(hitboxes.get(i).getTexture(), hitboxes.get(i).getHitbox().x - 5, hitboxes.get(i).getHitbox().y - 5);
            }
            game.batch.end();

        }
        if(!trapsSpawned) {
            trapsSpawned=true;
            spawnTraps();
        }else{
            game.batch.begin();
            for(int i=0;i<hitboxes2.size();i++){
                game.batch.draw(hitboxes2.get(i).getTextura(), hitboxes2.get(i).getHitbox().x , hitboxes2.get(i).getHitbox().y );
                game.batch.draw(hitboxes2.get(i).getBala().getTextura(), hitboxes2.get(i).getBala().getHitbox().x-10, hitboxes2.get(i).getBala().getHitbox().y-12);
            }
            game.batch.end();
        }
    }
    public void spawnObj(){
        objetos=new Objetos();
        hitboxes=new ArrayList<>();
        for(int i=0;i<floorNumber+1;i++) hitboxes.add(objetos.randomObj());
    }
    public void spawnTraps() {
        traps = new Trampas();
        hitboxes2 = new ArrayList<>();
        if (floorNumber != 0)
            System.out.println(floorNumber);
            if (floorNumber < 8) {
                for (int i = 0; i < floorNumber; i++) hitboxes2.add(traps.randomTrp());
            }else{
                for (int i = 0; i < 8; i++) hitboxes2.add(traps.randomTrp());
            }
    }
    public ArrayList generadorEnemigos (){
        ArrayList l=new ArrayList<>();
        Random r=new Random();
        for (int i=0;i<player.getLevel();i++){
            l.add(new Zombie());
            l.add(new Slime());
        }
        return l;
    }
    public void enemyLogic(){
        //Logica de enemigos

        for (int i=0;i<z.size();i++) {
            game.batch.begin();
            ataqueEnemigo(z.get(i));

            ///Muerte enemigo
            for (Proyectil p : proyectiles) {
                recibirProyectil(z.get(i), p);
                if(enemyGetsShot){
                    p.getPosicion().x=1000;
                }
            }

            ///Animacion enemigos
            if (z.get(i).getClass()==Zombie.class)animacionZombie((Zombie) z.get(i),player);
            else if (z.get(i).getClass()== Slime.class)animacionSlime((Slime) z.get(i),player);
            game.batch.end();

            ///Persecucion
            checkEnemiesCollisions();
            if (!z.get(i).getHitBoxAtaque().overlaps(player.getHitBox()) && z.get(i).getHealth() > 0) {
                if (z.get(i).getHitBoxAtaque().y != player.getHitBox().y) {
                    if (z.get(i).getHitBoxAtaque().y > player.getHitBox().y) {
                        if(z.get(i).getHitBox().overlaps(enemiesCol)){
                            z.get(i).getHitBox().y += z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                            z.get(i).getHitBoxAtaque().y += z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                        }else {
                            z.get(i).getHitBox().y -= z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                            z.get(i).getHitBoxAtaque().y -= z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                        }
                    }
                    if (z.get(i).getHitBoxAtaque().y < player.getHitBox().y) {
                        if(z.get(i).getHitBox().overlaps(enemiesCol)){
                            z.get(i).getHitBox().y -= z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                            z.get(i).getHitBoxAtaque().y -= z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                        }else{
                            z.get(i).getHitBox().y += z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                            z.get(i).getHitBoxAtaque().y += z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                        }
                    }
                }
                if (z.get(i).getClass()==Zombie.class) {
                    if (z.get(i).getHitBoxAtaque().x != player.getHitBox().x) {
                        if (z.get(i).getHitBoxAtaque().x > player.getHitBox().x) {
                            if(z.get(i).getHitBox().overlaps(enemiesCol)){
                                z.get(i).getHitBox().x += z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                                z.get(i).getHitBoxAtaque().x += z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                            }else{
                                z.get(i).setDirection(-1);
                                z.get(i).setPosModifier(32);
                                z.get(i).getHitBox().x -= z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                                z.get(i).getHitBoxAtaque().x -= z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                            }
                        }
                        if (z.get(i).getHitBoxAtaque().x < player.getHitBox().x) {
                            if(z.get(i).getHitBox().overlaps(enemiesCol)){
                                z.get(i).getHitBox().x -= z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                                z.get(i).getHitBoxAtaque().x -= z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                            }else {
                                z.get(i).setPosModifier(0);
                                z.get(i).setDirection(1);
                                z.get(i).getHitBox().x += z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                                z.get(i).getHitBoxAtaque().x += z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                            }
                        }
                    }
                } else if (z.get(i).getClass()==Slime.class) {
                    if (z.get(i).getHitBoxAtaque().x != player.getHitBox().x) {
                        if(z.get(i).getHitBox().overlaps(enemiesCol)){
                            z.get(i).getHitBox().x += z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                            z.get(i).getHitBoxAtaque().x += z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                        }else {
                            if (z.get(i).getHitBoxAtaque().x > player.getHitBox().x) {
                                z.get(i).setDirection(1);
                                z.get(i).setPosModifier(0);
                                z.get(i).getHitBox().x -= z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                                z.get(i).getHitBoxAtaque().x -= z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                            }
                        }
                        if (z.get(i).getHitBoxAtaque().x < player.getHitBox().x) {
                            if(z.get(i).getHitBox().overlaps(enemiesCol)){
                                z.get(i).getHitBox().x -= z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                                z.get(i).getHitBoxAtaque().x -= z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                            }else {
                                if (z.get(i).getHitBoxAtaque().x < player.getHitBox().x) {
                                    z.get(i).setPosModifier(32);
                                    z.get(i).setDirection(-1);
                                    z.get(i).getHitBox().x += z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                                    z.get(i).getHitBoxAtaque().x += z.get(i).getSpeed() * Gdx.graphics.getDeltaTime();
                                }
                            }
                        }
                    }
                }
            }

        }
    }
    private void ataqueEnemigo(Enemy e){
        float currentTime = TimeUtils.nanoTime() / 1000000000.0f; // Obtiene el tiempo actual en segundos
        float elapsedTime = currentTime - lastAttackTime; // Calcula el tiempo transcurrido desde el último disparo
        if (player.getHealth()>0 && e.getHealth()>0) {
            if (e.getHitBoxAtaque().overlaps(player.getHitBox()) && elapsedTime>=attackSpeed) {
                player.setHealth((player.getHealth() - player.attacked(e.getDamage())));
                playerGetsDamage=true;
                lastAttackTime=currentTime;
            }
        }
    }
    public void animacionZombie (Zombie z, Player player){
        if (z.getHitBox().overlaps(player.getHitBox()) && z.getHealth() > 0) {
            game.batch.draw((TextureRegion) game.gameAnimations.zombieAttack.getKeyFrame(elapsedTime, true), z.getHitBox().x+z.getPosModifier()+8, z.getHitBox().y+8, 32 * z.getDirection(), 32);
        }

        if (z.getHealth() < 0 && !game.gameAnimations.zombieDie.isAnimationFinished(elapsedTime)) {
            game.batch.draw((TextureRegion) game.gameAnimations.zombieDie.getKeyFrame(elapsedTime, true), z.getHitBox().x+z.getPosModifier()+8, z.getHitBox().y+8, 32 * z.getDirection(), 32);
        }

        if (z.getHealth() <= 0) {
            game.batch.draw((TextureRegion) game.gameAnimations.zombieMorido.getKeyFrame(elapsedTime, true), z.getHitBox().x+z.getPosModifier()-1000+8, z.getHitBox().y+8, 32 * z.getDirection(), 32);
        }

        if (z.getHealth() > 0 && !z.getHitBox().overlaps(player.getHitBox())) {
            game.batch.draw((TextureRegion) game.gameAnimations.zombieMove.getKeyFrame(elapsedTime, true), z.getHitBox().x+z.getPosModifier()+8, z.getHitBox().y+8, 32 * z.getDirection(), 32);
        }
        for(Proyectil p: proyectiles){
            if(z.getHitBox().overlaps(p.getHitBox())){
                game.batch.draw((TextureRegion) game.gameAnimations.zombieDamaged.getKeyFrame(elapsedTime,true),z.getHitBox().x+z.getPosModifier(), z.getHitBox().y+8, 32 * z.getDirection(), 25);
            }
        }
    }
    public void animacionSlime (Slime z,Player player){
        if (z.getHitBox().overlaps(player.getHitBox()) && z.getHealth() > 0) {
            game.batch.draw((TextureRegion) game.gameAnimations.slimeAttack.getKeyFrame(elapsedTime, true), z.getHitBox().x+z.getPosModifier()+5, z.getHitBox().y+12, 32 * z.getDirection(), 25);
        }

        if (z.getHealth() <= 0) {
            game.batch.draw((TextureRegion) game.gameAnimations.slimeMorido.getKeyFrame(elapsedTime, true), z.getHitBox().x+z.getPosModifier()-1000+5, z.getHitBox().y+12, 32 * z.getDirection(), 25);
        }

        if (z.getHealth() > 0 && !z.getHitBox().overlaps(player.getHitBox())) {
            game.batch.draw((TextureRegion) game.gameAnimations.slimeMove.getKeyFrame(elapsedTime, true), z.getHitBox().x+z.getPosModifier()+5, z.getHitBox().y+12, 32 * z.getDirection(), 25);
        }
        for(Proyectil p: proyectiles){
            if(z.getHitBox().overlaps(p.getHitBox())){
                game.batch.draw((TextureRegion) game.gameAnimations.slimeDamaged.getKeyFrame(elapsedTime,true),z.getHitBox().x+z.getPosModifier()+5, z.getHitBox().y+12, 32 * z.getDirection(), 25);
            }
        }
    }
    private void recibirProyectil (Enemy e,Proyectil p){
        if (e.getHitBox().overlaps(p.getHitBox())) {
            e.setHealth(e.getHealth() - player.getWeapon().attack());
            if(e.getHealth()<=0){
                e.setStatus(false);
                chanceHPItemSpawn(e);
            }
            enemyGetsShot=true;
        }else{
            enemyGetsShot=false;
        }
        ///Puntaje enemigos
        if(e.getHealth()<=0){
            player.setScore((int) (player.getScore()+e.getScore()));
            e.setScore(0);
            player.setXp((int) (player.getXp()+e.getXp()));
            e.setXp(0);
            subirLvl();
        }
    }
    public void chanceHPItemSpawn(Enemy e){
        Random random=new Random();
        if(random.nextDouble(0d,1d)<0.1d && !hpItemSpawned){
            hpItemSpawned=true;
            itemR.getItemHitbox().x=e.getHitBox().x-1000;
            itemR.getItemHitbox().y=e.getHitBox().y;
        }
    }
    public void subirLvl (){
        if (player.getXp()>=100){
            player.setXp(player.getXp()-100);
            player.setLevel(player.getLevel()+1);
            Enemy.difficulty += 0.75;
        }
    }
    private boolean noEnemies(){
        deadEnemies=new HashSet<>();
        for(Enemy e: z){
            if(!e.status){
                deadEnemies.add(e);
            }
        }
        if((z.size())==deadEnemies.size()){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void dispose() {
        walls.dispose();
        floor.dispose();
        door.dispose();
        gate.dispose();
    }
}
