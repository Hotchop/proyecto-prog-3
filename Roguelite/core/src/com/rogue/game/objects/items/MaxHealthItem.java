package com.rogue.game.objects.items;

import com.rogue.game.objects.Player;

public class MaxHealthItem extends Item{
    private static boolean spawneable;
    public MaxHealthItem() {
    }

    public MaxHealthItem(String name, String decription, String imagePath, int posX, int posY) {
        super(name, decription, imagePath, posX, posY);
        MaxHealthItem.spawneable = true;
    }

    @Override
    public void pickUp(Player player) {
        player.setMaxHealth(player.getMaxHealth() + 25);
        player.setHealth(player.getHealth() + 25);
    }
}
