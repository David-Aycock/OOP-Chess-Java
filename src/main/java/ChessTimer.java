package main.java;

import java.util.concurrent.TimeUnit;

/**
 * Represents a Chess timer with decrementing time.    
 * Additionally implements time increment: when a player makes a move, they get n amount of time back.
 * Used to manage the time for each player as the game progresses.
 */
public class ChessTimer {
    
    // Remaining time and active state of the timer
    private int remainingTime;
    private boolean isActive;
    private int increment;
    
    /**
     * Constructor.
     * Initializes the timer with a start time and time increment.
     * 
     * @param startTime The starting time for the timer in seconds.
     * @param increment The time increment added after each move in seconds.
     */
    public ChessTimer(int startTime, int increment) {
        this.remainingTime = startTime;
        this.isActive = false;
        this.increment = increment;
    }
    
    /**
     * Returns the remaining time in a formatted string (MM:SS)
     * 
     * @return A string representing the formatted remaining time.
     */
    public String getFormattedTime() {
        long minutes = TimeUnit.SECONDS.toMinutes(remainingTime);
        long seconds = TimeUnit.SECONDS.toSeconds(remainingTime) - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d", minutes, seconds);
    }
    
    /**
     * Decrements the remaining time by one second if the timer is active and there is time remaining.
     */
    public void decrementTime() {
        if (isActive && remainingTime > 0) {
            remainingTime--;
        }
    }
    
    /**
     * Activates the timer, allowing it to decrement when decrementTime() is called.
     */
    public void activate() {
        isActive = true;
    }
    
    /**
     * Deactivates the timer, preventing it from decrementing when decrementTime() is called.
     */
    public void deactivate() {
        isActive = false;
    }
    
    /**
     * Adds the time increment to the remaining time.
     * Called after a move is made in the game.
     */
    public void addIncrement() {
            remainingTime += increment;
    }
    
    /**
     * Sets the time increment value.
     * 
     * @param increment The time increment in seconds.
     */
    public void setIncrement(int increment) {
        this.increment = increment;
    }
}