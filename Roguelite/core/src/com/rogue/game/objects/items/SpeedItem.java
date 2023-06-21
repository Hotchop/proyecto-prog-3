package com.rogue.game.objects.items;

import com.rogue.game.objects.Player;

public class SpeedItem extends Item{
    private static boolean spawneable;
    public SpeedItem() {
    }

    public SpeedItem(String name, String decription, String imagePath, int posX, int posY) {
        super(name, decription, imagePath, posX, posY);
        spawneable = true;
    }

    public static boolean isSpawneable() {
        return spawneable;
    }

    public static void setSpawneable(boolean spawneable) {
        SpeedItem.spawneable = spawneable;
    }

    @Override
    public void pickUp(Player player) {
        if (player.getSpeed() < player.getMAX_SPEED()) {
            player.setSpeed(player.getSpeed() + 10);
        }
        if(player.getSpeed() == player.getMAX_SPEED()){
            SpeedItem.spawneable = false;
        }
        if(player.getSpeed() > player.getMAX_SPEED()){
            player.setSpeed(player.getMAX_SPEED());
            SpeedItem.spawneable = false;
        }
    }
}
