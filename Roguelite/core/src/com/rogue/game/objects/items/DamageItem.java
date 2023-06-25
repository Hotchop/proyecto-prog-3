package com.rogue.game.objects.items;

import com.rogue.game.objects.Player;

public class DamageItem extends Item{
    private static boolean spawneable;
    public DamageItem() {
    }

    public DamageItem(String name, String decription, String imagePath, int posX, int posY) {
        super(name, decription, imagePath, posX, posY);
        DamageItem.spawneable = true;
    }

    @Override
    public void pickUp(Player player) {
        player.getWeapon().setDamage(player.getWeapon().getDamage() + 10);
    }
}
