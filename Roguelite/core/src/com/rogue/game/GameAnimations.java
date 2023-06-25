package com.rogue.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAnimations {
    protected TextureRegion[] animationFrames;
    protected Animation playerRun;
    protected Animation playerIdle;
    protected Animation playerExit;
    protected Animation playerDie;
    protected Animation fireball;
    protected Animation slimeMove;

    public GameAnimations() {
        /**Player**/
        ///Divide Spritesheet en partes iguales en una matriz
        TextureRegion[][] tempFrames = TextureRegion.split(new Texture("Animations/AnimationSheet_Character.png"),32,32);

        //Crea un animation con un array de texturas de 4 imagenes
        animationFrames = new TextureRegion[8];

        //Guarda la animacion de correr
        int index = 0;
        for(int j = 0; j < 8; j++){
            animationFrames[index++] = tempFrames[3][j];
        }
        playerRun = new Animation(1f/16f,animationFrames);

        //Idle Animation
        animationFrames = new TextureRegion[2];
        index = 0;
        for(int j = 0; j < 2; j++){
            animationFrames[index++] = tempFrames[0][j];
        }
        playerIdle = new Animation<>(1f/8f,animationFrames);

        //Exit Animation
        animationFrames = new TextureRegion[3];
        index = 0;
        for(int j = 0; j < 3; j++){
            animationFrames[index++] = tempFrames[6][j];
        }
        playerExit = new Animation<>(1f/0.8f,animationFrames);

        //Die Animation
        animationFrames = new TextureRegion[8];
        index = 0;
        for(int j = 0; j < 8; j++){
            animationFrames[index++] = tempFrames[7][j];
        }
        playerDie = new Animation<>(1f/8f,animationFrames);

        /**Fireball**/
        tempFrames = TextureRegion.split(new Texture("Animations/fireball 16x16.png"),16,16);
        animationFrames = new TextureRegion[4];
        index = 0;
        for(int j = 0; j < 4; j++){
            animationFrames[index++] = tempFrames[0][j];
        }
        fireball = new Animation<>(1f/8f,animationFrames);


        /**Slime**/
        tempFrames = TextureRegion.split(new Texture("Animations/Slime_Medium_Green.png"),32,32);
        animationFrames = new TextureRegion[4];
        index = 0;
        for(int j = 0; j < 4; j++){
            animationFrames[index++] = tempFrames[2][j];
        }
        slimeMove = new Animation<>(1f/8f,animationFrames);
    }
}
