package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RaceHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int userId;
    private String horseName;
    private int position;
    private int totalHorses;
    private int coinsEarned;
    private String timestamp;
    
    // Constructor for creating new history (from game)
    public RaceHistory(int userId, String horseName, int position, int totalHorses, int coinsEarned) {
        this.userId = userId;
        this.horseName = horseName;
        this.position = position;
        this.totalHorses = totalHorses;
        this.coinsEarned = coinsEarned;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
    
    public int getUserId() {
        return userId;
    }
    
    public String getHorseName() {
        return horseName;
    }
    
    public int getPosition() {
        return position;
    }
    
    public int getTotalHorses() {
        return totalHorses;
    }
    
    public int getCoinsEarned() {
        return coinsEarned;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Timestamp ts) {
        this.timestamp = ts.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
    
    @Override
    public String toString() {
        return String.format("[%s] Position %d/%d - Earned: %d coins", 
                           timestamp, position, totalHorses, coinsEarned);
    }
}
