package model;

public class RaceHorse {
    private String name;
    private String color;
    private int speed;
    private int position;
    private boolean isPlayer;
    private boolean finished;
    
    public RaceHorse(String name, String color, int speed, boolean isPlayer) {
        this.name = name;
        this.color = color;
        this.speed = speed;
        this.position = 0;
        this.isPlayer = isPlayer;
        this.finished = false;
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
    
    public int getPosition() {
        return position;
    }
    
    public void setPosition(int position) {
        this.position = position;
    }
    
    public void move() {
        int movement = (int) (Math.random() * (speed / 10 + 1)) + 1;
        position += movement;
    }
    
    public boolean isPlayer() {
        return isPlayer;
    }
    
    public boolean isFinished() {
        return finished;
    }
    
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
