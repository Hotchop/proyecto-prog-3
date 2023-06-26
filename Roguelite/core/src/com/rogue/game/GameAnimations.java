package com.rogue.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAnimations {
    protected TextureRegion[] animationFrames;
    protected Animation playerRun;
    protected Animation playerRunDamage;
    protected Animation playerIdle;
    protected Animation playerIdleDamage;

    protected Animation playerExit;
    protected Animation playerDie;
    protected Animation slimeMove;
    protected Animation slimeDamaged;
    protected Animation slimeAttack;
    protected Animation slimeMorido;
    protected Animation zombieMove;
    protected  Animation zombieAttack;
    protected Animation zombieDamaged;
    protected Animation zombieDie;
    protected Animation zombieMorido;
    protected Animation batMove;

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
        playerDie = new Animation<>(1f/6f,animationFrames);

        //Daño running
        tempFrames = TextureRegion.split(new Texture("Animations/AnimationSheet_Character_Damage.png"),32,32);
        animationFrames = new TextureRegion[8];
        index = 0;
        for(int j = 0; j < 8; j++){
            animationFrames[index++] = tempFrames[3][j];
        }
        playerRunDamage = new Animation(1f/16f,animationFrames);

        //Daño idle Animation
        animationFrames = new TextureRegion[2];
        index = 0;
        for(int j = 0; j < 2; j++){
            animationFrames[index++] = tempFrames[0][j];
        }
        playerIdleDamage = new Animation<>(1f/8f,animationFrames);

        /**Slime**/
        ///Move
        tempFrames = TextureRegion.split(new Texture("Animations/slime-Sheet.png"),32,25);
        animationFrames = new TextureRegion[8];
        index = 0;
        for(int j = 0; j < 8; j++){
            animationFrames[index++] = tempFrames[0][j];
        }
        slimeMove = new Animation<>(1f/8f,animationFrames);

        ///Attack
        animationFrames = new TextureRegion[8];
        index = 0;
        for(int j = 0; j < 8; j++){
            animationFrames[index++] = tempFrames[1][j];
        }
        slimeAttack = new Animation<>(1f/8f,animationFrames);

        ///Morido
        animationFrames=new TextureRegion[1];

        animationFrames[0] = tempFrames[2][4];

        slimeMorido=new Animation<>(1f/8f,animationFrames);

        ///Daño
        slimeMorido=new Animation<>(1f/8f,animationFrames);
        tempFrames = TextureRegion.split(new Texture("Animations/slime-Sheet-damage.png"),32,25);
        animationFrames = new TextureRegion[8];
        index = 0;
        for(int j = 0; j < 8; j++){
            animationFrames[index++] = tempFrames[0][j];
        }
        slimeDamaged = new Animation<>(1f/8f,animationFrames);

        /**Zombie**/
        tempFrames = TextureRegion.split(new Texture("Animations/Zombie.png"),32,32);

        ///Run animation
        animationFrames=new TextureRegion[8];
        index=0;
        for(int j = 0; j < 8; j++){
            animationFrames[index++] = tempFrames[2][j];
        }
        zombieMove=new Animation<>(1f/8f,animationFrames);

        ///Attack animation
        animationFrames=new TextureRegion[7];
        index=0;
        for(int j = 0; j < 7; j++){
            animationFrames[index++] = tempFrames[1][j];
        }
        zombieAttack=new Animation<>(1f/8f,animationFrames);

        ///Die animation
        animationFrames=new TextureRegion[8];
        index=0;
        for(int j = 0; j < 8; j++){
            animationFrames[index++] = tempFrames[5][j];
        }
        zombieDie=new Animation<>(1f/8f,animationFrames);

        animationFrames=new TextureRegion[1];

        animationFrames[0] = tempFrames[5][7];

        zombieMorido=new Animation<>(1f/8f,animationFrames);

        ///Daño
        zombieMorido=new Animation<>(1f/8f,animationFrames);
        tempFrames = TextureRegion.split(new Texture("Animations/Zombie-damage.png"),32,25);
        animationFrames = new TextureRegion[8];
        index = 0;
        for(int j = 0; j < 8; j++){
            animationFrames[index++] = tempFrames[0][j];
        }
        zombieDamaged = new Animation<>(1f/8f,animationFrames);

        /**Bat**/
        animationFrames=new TextureRegion[2];
        index=0;
        for(int j = 0; j < 2; j++){
            animationFrames[index++] = tempFrames[0][j];
        }
        batMove=new Animation<>(1f/8f,animationFrames);
    }
}
