package com.rogue.game.objects.mapstuff;

import java.util.ArrayList;

public class Objetos {
    ArrayList<Objecto> objetos;

    public Objetos( ) {
        objetos=new ArrayList<>();
        objCreator();
        ArrayList<Objecto> copy = new ArrayList<>();
        do {
            for (Objecto o : objetos) {
                if (!isOverlap(copy, o)) {
                    copy.add(o);
                }
            }
        }while(copy.size()<10);
        objetos.clear();
        objetos.addAll(copy);

    }
    private boolean isOverlap(ArrayList<Objecto> copy, Objecto obj) {
        for (Objecto t : copy) {
            if (obj.getHitbox().overlaps(t.getHitbox())) {
                return true; // overlapean
            }
        }
        return false; // No overlapean
    }
    public Objecto randomObj(){
        int num=(int)(Math.random()*(objetos.size()-1));
        objetos.get(num).setSpawned(true);
        return this.objetos.get(num);
    }
    private void objCreator(){
        for(int i=0;i<15;i++)
            objetos.add(new Objecto());
    }
    public ArrayList<Objecto> getObjetos() {
        return objetos;
    }

    public void setObjetos(ArrayList<Objecto> objetos) {
        this.objetos = objetos;
    }
}
