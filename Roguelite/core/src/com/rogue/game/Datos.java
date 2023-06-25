package com.rogue.game;

public class Datos {
    private String name;
    private int level;
    private int score;

    @Override
    public String toString() {

            return "Nombre: " + name + " NivelMAX: " + level + ", Puntuacion: " + score;
    }

    public Datos(String name, int level, int score) {
        this.name = name;
        this.level = level;
        this.score = score;
    }

}