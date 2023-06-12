package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import org.w3c.dom.Text;

import javax.swing.text.View;
import java.util.Iterator;

public class drop extends ApplicationAdapter {
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle bucket;  //Es una clase de Gdx. Se puede usar como el objeto fisico con hitbox de los cuerpos
	private Array<Rectangle> raindrops; //Usa Array en lugar de ArrayList porque es mejor al eliminar basura
	private long lastDropTime;
	private int scoreNum;
	private BitmapFont font;
	private float volume;

	@Override
	public void create () {
		dropImage = new Texture(Gdx.files.internal("drop.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		scoreNum = 0;
		volume = 0.5f;	//Volumen a 50% -> 0.5f
		font = new BitmapFont();

		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		//Pone la musica en loop y setea el volumen
		rainMusic.setLooping(true);
		rainMusic.play();
		rainMusic.setVolume(volume);

		//Crea la carama y su rango
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,480);

		batch = new SpriteBatch();

		//Crea un balde con un hitbox de rectangulo y el tamaño del sprite
		bucket = new Rectangle();
		bucket.x = 800/2 - 64/2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}

	@Override
	public void render () {
		//Un metodo que cambia el color del fondo de la pantalla
		ScreenUtils.clear(0,0,0.2f,1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		//Comienza el renderizado. Todos los renders se deberian hacer en un solo batch de ser posible
		batch.begin();
		font.draw(batch,"Score: "+ scoreNum,10,30);
		batch.draw(bucketImage,bucket.x,bucket.y);
		for(Rectangle raindrop: raindrops) {
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		batch.end();

		//Movimiento con el mouse
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}

		//Moviemiento con las flechas
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 500 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 500 * Gdx.graphics.getDeltaTime();

		//Hitbox horizontal del balde
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800-64) bucket.x = 800 - 64;

		//Timer para spawnear gotas en intervalos
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

		//Movimiento y despawn de las gotas
		for(Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext();){
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0) iter.remove();  //Despawn si pasa de la pantalla
			if(raindrop.overlaps(bucket)) {	//Despawn si toca balde
				dropSound.play(volume);
				iter.remove();
				scoreNum += 100;
			}
		}

	}

	private void spawnRaindrop(){
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0,800-64);  //Spawnea las gotas en una posicion random
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();  //Tiempo en el que se spawneo
	}

	@Override
	public void dispose () {  //Un garbage collector para assets y otras cosas creadas por el usuario
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
		font.dispose();
	}
}
