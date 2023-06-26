package com.rogue.game.objects.items;

import com.rogue.game.objects.Player;

public class HealthItem extends Item{
    private static boolean spawneable;
    public HealthItem() {
    }

    public HealthItem(String name, String decription, String imagePath, int posX, int posY) {
        super(name, decription, imagePath, posX, posY);
        spawneable = true;
    }

    public static boolean isSpawneable() {
        return spawneable;
    }

    public static void setSpawneable(boolean spawneable) {
        HealthItem.spawneable = spawneable;
    }

    @Override
    public void pickUp(Player player) {
        if (player.getHealth() < player.getMaxHealth()) {
            player.setHealth(player.getHealth() + (player.getMaxHealth() * 0.25f));
        }
        if(player.getHealth() == player.getMaxHealth()){
            HealthItem.spawneable = false;
        }
        if(player.getHealth() > player.getMaxHealth()){
            player.setHealth(player.getMaxHealth());
            HealthItem.spawneable = false;
        }
    }
}
