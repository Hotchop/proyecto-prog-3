package com.rogue.game.objects.enemies;

import com.badlogic.gdx.Game;
import com.rogue.game.GameScreen;

import java.util.ArrayList;

public class Zombies {
    ArrayList<Zombie>zombies;

    public Zombies() {
        this.zombies = new ArrayList<>();
        ArrayList<Zombie> copy=new ArrayList<>();
        zCreator();
        do{
            for(Zombie z: zombies){
                if(!isOverlap(copy,z)){
                    copy.add(z);
                }
            }
        }while(copy.size()<GameScreen.floorNumber);
        zombies.clear();
        zombies.addAll(copy);
    }

    private boolean isOverlap(ArrayList<Zombie>copy, Zombie z){
        for(Zombie e: copy){
            if(e.getHitBox().overlaps(z.getHitBox())){
                return true;
            }
        }
        return false;
    }
    private void zCreator(){
        for(int i = 0; i<GameScreen.floorNumber*2; i++){
            zombies.add(new Zombie());
        }
    }
}
