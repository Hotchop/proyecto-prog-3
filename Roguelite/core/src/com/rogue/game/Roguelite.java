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
	Rectangle playerHitbox;
	Player player;
	Weapon playerWeapon;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,800);
		batch = new SpriteBatch();
		img = new Texture("AnimationSheet_Character.png");
		walls = new Texture("Walls.png");
		floor = new Texture("Floor.png");
		door = new Texture("Door Frame.png");
		gate = new Texture("Door Gate.png");
		playerWeapon = new Weapon();
		player = new Player("Jason",playerWeapon);

		playerHitbox = new Rectangle();
		playerHitbox.x = 384;
		playerHitbox.y = 250;
		playerHitbox.width = 24;
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
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(floor,160,160);
		batch.draw(walls,128,160);
		batch.draw(door,370,640);
		batch.draw(gate,384,640);
		batch.draw((TextureRegion) idleAnimation.getKeyFrame(elapsedTime,true),playerHitbox.x,playerHitbox.y);
		batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.W)) playerHitbox.y += player.getSpeed() * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.A)) playerHitbox.x -= player.getSpeed() * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.S)) playerHitbox.y -= player.getSpeed() * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.D)) playerHitbox.x += player.getSpeed() * Gdx.graphics.getDeltaTime();

		if(playerHitbox.x < 160) playerHitbox.x = 160;
		if(playerHitbox.x > 608) playerHitbox.x = 608;
		if(playerHitbox.y < 240) playerHitbox.y = 240;
		if(playerHitbox.y > 640) playerHitbox.y = 640;

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
