package com.rogue.game;
///Clase para guardar JSON
public class Datos implements Comparable<Datos> {
    private String name;
    private int level;
    private int score;

    public Datos() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {

        return "Nombre: " + name + " NivelMAX: " + level + ", Puntuacion: " + score;
    }

    public Datos(String name, int level, int score) {
        this.name = name;
        this.level = level;
        this.score = score;
    }


    @Override
    public int compareTo(Datos o) {
        if (this.score > o.getScore()) {
            return -1;
        } else if (this.score < o.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }
}