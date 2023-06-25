package com.rogue.game.interfaces;

import com.rogue.game.objects.Player;
import com.rogue.game.objects.items.Item;

public interface ItemLogic {
    void pickUp(Player player);
    void copy(Item item);

}
