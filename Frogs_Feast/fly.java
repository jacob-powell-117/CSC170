import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class fly extends Actor
{
    private static final int BASE_SPEED = 4; // Fast movement, matching the frog
    private static final int TURN_PROBABILITY = 15; // Higher number = more turns
    private static final int MAX_TURN_ANGLE = 45; // Maximum angle to turn by
    
    public void act()
    {
        // 1. Move foward quickly
        move(BASE_SPEED);
        
        // 2. Check if it should change direction
        checkDirectionChange();
        
        // 3. Keep the fly inside the world boundaries
        bounceAtEdge();
    }
    
    private void checkDirectionChange()
    {
        // Check if a random number (0-99) is less than the turn probability
        if (Greenfoot.getRandomNumber(100) < TURN_PROBABILITY)
        {
            int turnAngle = Greenfoot.getRandomNumber(2 * MAX_TURN_ANGLE) - MAX_TURN_ANGLE;
            
            turn(turnAngle);
        }
        
    }
    
    private void bounceAtEdge()
    {
        if (isAtEdge())
        {
            // Turn a large amount to get away from the edge
            turn(Greenfoot.getRandomNumber(90) + 90);
        }
    }
}
