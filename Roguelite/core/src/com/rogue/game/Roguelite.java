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

public class Roguelite extends ApplicationAdapter {
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	TextureRegion[] animationFrames;
	Animation runAnimation;
	float elapsedTime;
	Rectangle player;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,800);
		batch = new SpriteBatch();
		img = new Texture("AnimationSheet_Character.png");

		player = new Rectangle();
		player.x = 400;
		player.y = 400;
		player.width = 64;
		player.height = 64;

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

	}

	@Override
	public void render () {
		elapsedTime += Gdx.graphics.getDeltaTime();	//Tiempo de juego
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw((TextureRegion) runAnimation.getKeyFrame(elapsedTime,true),player.x,player.y);
		batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.W)) player.y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.A)) player.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.S)) player.y -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.D)) player.x += 200 * Gdx.graphics.getDeltaTime();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
