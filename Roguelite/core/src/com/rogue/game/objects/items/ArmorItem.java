package com.rogue.game.objects.items;

import com.rogue.game.objects.Player;

public class ArmorItem extends Item{
    private static boolean spawneable;
    public ArmorItem() {
    }

    public ArmorItem(String name, String decription, String imagePath, int posX, int posY) {
        super(name, decription, imagePath, posX, posY);
        spawneable = true;
    }

    public static boolean isSpawneable() {
        return spawneable;
    }

    public static void setSpawneable(boolean spawneable) {
        ArmorItem.spawneable = spawneable;
    }

    @Override
    public void pickUp(Player player) {
        if (player.getArmor() < player.getMAX_ARMOR()) {
            player.setArmor(player.getArmor() + 0.01f);
        }
        if(player.getArmor() == player.getMAX_ARMOR()){
            ArmorItem.spawneable = false;
        }
        if(player.getArmor() > player.getMAX_ARMOR()){
            player.setArmor(player.getMAX_ARMOR());
            ArmorItem.spawneable = false;
        }
    }
}
