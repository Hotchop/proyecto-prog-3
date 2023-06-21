package com.rogue.game.objects.items;

import com.rogue.game.objects.Player;

public class CritChanceItem extends Item{
    private static boolean spawneable;
    public CritChanceItem() {
    }

    public CritChanceItem(String name, String decription, String imagePath, int posX, int posY) {
        super(name, decription, imagePath, posX, posY);
        spawneable = true;
    }

    public static boolean isSpawneable() {
        return spawneable;
    }

    public static void setSpawneable(boolean spawneable) {
        CritChanceItem.spawneable = spawneable;
    }

    @Override
    public void pickUp(Player player) {
        if (player.getWeapon().getCritChance() < player.getWeapon().getMAX_CRITCHANCE()) {
            player.getWeapon().setCritChance(player.getWeapon().getCritChance() + 0.01f);
        }
        if(player.getWeapon().getCritChance() == player.getWeapon().getMAX_CRITCHANCE()){
            CritChanceItem.spawneable = false;
        }
        if(player.getWeapon().getCritChance() > player.getWeapon().getMAX_CRITCHANCE()){
            player.getWeapon().setCritChance(player.getWeapon().getMAX_CRITCHANCE());
            CritChanceItem.spawneable = false;
        }
    }
}
