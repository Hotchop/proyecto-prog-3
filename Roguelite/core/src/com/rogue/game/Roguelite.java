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
		batch.draw((TextureRegion) idleAnimation.getKeyFrame(elapsedTime,true),player.getHitBox().x,player.getHitBox().y);
		batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.W)) player.getHitBox().y += player.getSpeed() * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.A)) player.getHitBox().x -= player.getSpeed() * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.S)) player.getHitBox().y -= player.getSpeed() * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.D)) player.getHitBox().x += player.getSpeed() * Gdx.graphics.getDeltaTime();

		//Area de juego: x - 160 a 608, y - 240 a 640
		if(player.getHitBox().x < 160) player.getHitBox().x = 160;
		if(player.getHitBox().x > 608) player.getHitBox().x = 608;
		if(player.getHitBox().y < 240) player.getHitBox().y = 240;
		if(player.getHitBox().y > 640) player.getHitBox().y = 640;

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
