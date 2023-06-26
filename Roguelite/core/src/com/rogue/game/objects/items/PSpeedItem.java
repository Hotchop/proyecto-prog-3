package com.rogue.game.objects.items;

import com.rogue.game.objects.Player;

public class PSpeedItem extends Item{
    private static boolean spawneable;
    public PSpeedItem() {
    }

    public PSpeedItem(String name, String decription, String imagePath, int posX, int posY) {
        super(name, decription, imagePath, posX, posY);
        spawneable = true;
    }

    public static boolean isSpawneable() {
        return spawneable;
    }

    public static void setSpawneable(boolean spawneable) {
        PSpeedItem.spawneable = spawneable;
    }

    @Override
    public void pickUp(Player player) {
        if (player.getWeapon().getpSpeed() > player.getWeapon().getMAX_PSPEED()) {
            player.getWeapon().setpSpeed(player.getWeapon().getpSpeed() - 0.05f);
        }
        if(player.getWeapon().getpSpeed() == player.getWeapon().getMAX_PSPEED()){
            PSpeedItem.spawneable = false;
        }
        if(player.getWeapon().getpSpeed() < player.getWeapon().getMAX_PSPEED()){
            player.getWeapon().setpSpeed(player.getWeapon().getpSpeed());
            PSpeedItem.spawneable = false;
        }
    }
}
