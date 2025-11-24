package model;

import java.io.Serializable;

public class Horse implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String color;
    private int speed;
    private int stamina;
    private int acceleration;
    private int level;
    
    public Horse(String name, String color) {
        this.name = name;
        this.color = color;
        this.speed = 50;
        this.stamina = 50;
        this.acceleration = 50;
        this.level = 1;
    }
    
    public String getName() {
        return name;
    }
    
    public String getColor() {
        return color;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public int getStamina() {
        return stamina;
    }
    
    public int getAcceleration() {
        return acceleration;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }
    
    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public void upgradeSpeed(int amount) {
        this.speed += amount;
    }
    
    public void upgradeStamina(int amount) {
        this.stamina += amount;
    }
    
    public void upgradeAcceleration(int amount) {
        this.acceleration += amount;
    }
    
    public void levelUp() {
        this.level++;
    }
    
    public int calculateRaceSpeed() {
        // Formula untuk menghitung kecepatan final di race
        return (speed + acceleration + stamina) / 3;
    }
}
