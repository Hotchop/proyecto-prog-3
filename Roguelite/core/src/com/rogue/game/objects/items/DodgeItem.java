package com.rogue.game.objects.items;

import com.rogue.game.objects.Player;

public class DodgeItem extends Item{
    private static boolean spawneable;
    public DodgeItem() {
    }

    public DodgeItem(String name, String decription, String imagePath, int posX, int posY) {
        super(name, decription, imagePath, posX, posY);
        spawneable = true;
    }

    public static boolean isSpawneable() {
        return spawneable;
    }

    public static void setSpawneable(boolean spawneable) {
        DodgeItem.spawneable = spawneable;
    }

    @Override
    public void pickUp(Player player) {
        if (player.getDodgeChance() < player.getMAX_DODGE()) {
            player.setDodgeChance(player.getDodgeChance() + 0.005f);
        }
        if(player.getDodgeChance() == player.getMAX_DODGE()){
            DodgeItem.spawneable = false;
        }
        if(player.getDodgeChance() > player.getMAX_DODGE()){
            player.setDodgeChance(player.getMAX_DODGE());
            DodgeItem.spawneable = false;
        }
    }
}
