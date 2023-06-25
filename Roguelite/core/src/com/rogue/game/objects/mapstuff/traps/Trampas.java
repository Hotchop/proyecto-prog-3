package com.rogue.game.objects.mapstuff.traps;

import java.util.ArrayList;

public class Trampas {
    ArrayList<Trap> trampas;

    public Trampas() {
        this.trampas = new ArrayList<>();
        ArrayList<Trap> copy = new ArrayList<>();
        do {
            trCreator();
            for (Trap t : trampas) {
                if (!isOverlap(copy, t)) {
                    copy.add(t);
                }
            }
        }while(copy.size()<10);
        trampas.clear();
        trampas.addAll(copy);
    }

    private boolean isOverlap(ArrayList<Trap> copy, Trap trap) {
        for (Trap t : copy) {
            if (trap.getHitbox().overlaps(t.getHitbox())) {
                return true; // overlapean
            }
        }
        return false; // No overlapean
    }
    public Trap randomTrp(){
        int num=(int)(Math.random()*(trampas.size()-1));
        trampas.get(num).setSpawned(true);
        return this.trampas.get(num);
    }
    private void trCreator(){
        for(int i=0;i<15;i++)
            trampas.add(new Trap());
    }
    public ArrayList<Trap> getTrampas() {
        return trampas;
    }

    public void setTrampas(ArrayList<Trap> trampas) {
        this.trampas = trampas;
    }
}
