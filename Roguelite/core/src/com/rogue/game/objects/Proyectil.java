package com.rogue.game.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Proyectil {
    private Vector2 posicion;
    private Vector2 direccion;
    private float velocidad;
    private float cooldown;
    private Rectangle hitBox;

    public void setPosicion(Vector2 posicion) {
        this.posicion = posicion;
    }
    public Vector2 getDireccion() {
        return direccion;
    }
    public void setDireccion(Vector2 direccion) {
        this.direccion = direccion;
    }
    public float getVelocidad() {
        return velocidad;
    }
    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }
    public float getCooldown() {
        return cooldown;
    }
    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }
    public Rectangle getHitBox() {
        return hitBox;
    }
    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public Proyectil(Vector2 posicion, Vector2 direccion, float velocidad) {
        this.posicion = posicion;
        this.direccion = direccion;
        this.velocidad = velocidad;
        this.hitBox = new Rectangle(posicion.x, posicion.y,8,8);
    }
    public void update() {
        float speedX = direccion.x * velocidad;
        float speedY = direccion.y * velocidad;
        posicion.add(speedX, speedY);
        hitBox.setPosition(posicion);
    }

    public Vector2 getPosicion() {
        return posicion;
    }
}
