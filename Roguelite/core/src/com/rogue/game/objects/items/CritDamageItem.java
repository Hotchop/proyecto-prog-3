package com.rogue.game.objects.items;

import com.rogue.game.objects.Player;

public class CritDamageItem extends Item{
    private static boolean spawneable;
    public CritDamageItem() {
    }

    public CritDamageItem(String name, String decription, String imagePath, int posX, int posY) {
        super(name, decription, imagePath, posX, posY);
        spawneable = true;
    }

    public static boolean isSpawneable() {
        return spawneable;
    }

    public static void setSpawneable(boolean spawneable) {
        CritDamageItem.spawneable = spawneable;
    }

    @Override
    public void pickUp(Player player) {
        if (player.getWeapon().getCritDamage() < player.getWeapon().getMAX_CRITDAMAGE()) {
            player.getWeapon().setCritDamage(player.getWeapon().getCritDamage() + 0.1f);
        }
        if(player.getWeapon().getCritDamage() == player.getWeapon().getMAX_CRITDAMAGE()){
            CritDamageItem.spawneable = false;
        }
        if(player.getWeapon().getCritDamage() > player.getWeapon().getMAX_CRITDAMAGE()){
            player.getWeapon().setCritDamage(player.getWeapon().getMAX_CRITDAMAGE());
            CritDamageItem.spawneable = false;
        }
    }
}
