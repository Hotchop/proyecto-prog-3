package com.rogue.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.rogue.game.objects.Player;
import com.rogue.game.objects.Weapon;

public class Roguelite extends ApplicationAdapter {
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	TextureRegion[] animationFrames;
	Animation runAnimation;
	Animation idleAnimation;
	float elapsedTime;
	Rectangle playerHitbox;
	Player player;
	Weapon playerWeapon;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,800);
		batch = new SpriteBatch();
		img = new Texture("AnimationSheet_Character.png");
		playerWeapon = new Weapon();
		player = new Player("Jason",playerWeapon);

		playerHitbox = new Rectangle();
		playerHitbox.x = 400;
		playerHitbox.y = 400;
		playerHitbox.width = 32;
		playerHitbox.height = 32;

		///Divide Spritesheet en partes iguales en una matriz
		TextureRegion[][] tempFrames = TextureRegion.split(img,32,32);

		//Crea un animation con un array de texturas de 4 imagenes
		animationFrames = new TextureRegion[4];

		//Guarda la animacion de correr
		int index = 0;
		for(int j = 0; j < 4; j++){
				animationFrames[index++] = tempFrames[2][j];
		}
		runAnimation = new Animation(1f/8f,animationFrames);

		//Idle Animation
		animationFrames = new TextureRegion[2];
		index = 0;
		for(int j = 0; j < 2; j++){
			animationFrames[index++] = tempFrames[0][j];
		}
		idleAnimation = new Animation<>(1f/8f,animationFrames);

	}

	@Override
	public void render () {
		elapsedTime += Gdx.graphics.getDeltaTime();	//Tiempo de juego
		ScreenUtils.clear(0, 0, 1, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw((TextureRegion) idleAnimation.getKeyFrame(elapsedTime,true),playerHitbox.x,playerHitbox.y);
		batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.W)) playerHitbox.y += player.getSpeed() * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.A)) playerHitbox.x -= player.getSpeed() * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.S)) playerHitbox.y -= player.getSpeed() * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.D)) playerHitbox.x += player.getSpeed() * Gdx.graphics.getDeltaTime();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
