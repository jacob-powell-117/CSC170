import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class ladybug extends Actor
{
    private static final int BASE_SPEED = 1; // Very slow movement
    private static final int TURN_PROBABILITY = 3;
    private static final int MAX_TURN_ANGLE = 15;
    
    // How far to "look" ahead to detect an obstacle
    private static final int LOOK_AHEAD_DISTANCE = 15;
    
    public void act()
    {
        // 1. Check for immediate obstacles (Environmentals, Spider, Frog)
        if (checkObstacleAhead()) {
            // If something is blocking the path, turn and try again
            turn(Greenfoot.getRandomNumber(90) + 90);
        }
        
        // 2. Move forward (only if path is clear)
        move(BASE_SPEED);
        
        //3. Gentle direction change to prevent linear movement
        checkDirectionChange();
        
        // 4. Stay inside the world boundaries
        checkRegionalBounds();
    }
    
    private void checkDirectionChange()
    {
        // Check if a random number (0-99) is less than the turn probability (3)
        // This means it has only a 3% chance to turn every cycle.
        if (Greenfoot.getRandomNumber(100) < TURN_PROBABILITY)
        {
            // Generate a small, random angle between -15 and +15 degrees
            int turnAngle = Greenfoot.getRandomNumber(2 * MAX_TURN_ANGLE) - MAX_TURN_ANGLE;
            
            // Apply the turn
            turn(turnAngle);
        }
    }
    
    private void checkRegionalBounds()
    {
        if (isAtEdge())
        {
            // Turn aggressively to re-enter the main area
            turn(Greenfoot.getRandomNumber(90) + 90);
        }
        
        MyWorld world = (MyWorld) getWorld();
        if (world == null) return;
        
        // Defind the boundary. Set to stay within 100 pixels of the edge
        final int BORDER = 100;
        
        // Check if ladybug is near a boarder and turn it back toward the center
        if (getX() < BORDER || getX() > world.getWidth() - BORDER ||
            getY() < BORDER || getY() > world.getHeight() - BORDER)
            {
                // Turn toward the center of the world gently
                turnTowards(world.getWidth() / 2, world.getHeight() / 2);
                // Add a slight random element so it doesn't get stuck
                turn(Greenfoot.getRandomNumber(10) - 5);
            }
    }
    
    private boolean checkObstacleAhead()
    {
        // Calculate the coordinates of the point to check
        int xOffset = (int)(Math.cos(Math.toRadians(getRotation())) * LOOK_AHEAD_DISTANCE);
        int yOffset = (int)(Math.sin(Math.toRadians(getRotation())) * LOOK_AHEAD_DISTANCE);
        
        // Get the actor (if any) at that spot. We will ignore other ladybugs and flies
        Actor ahead = getWorld().getObjectsAt(getX() + xOffset, getY() + yOffset, Actor.class)
            .stream()
            // Filter out the actors it can run into
            .filter(a -> !(a instanceof ladybug) && !(a instanceof fly))
            .findFirst()
            .orElse(null);
        
        // The world edge is alwasy an obstacle
        if (isAtEdge()) {
            return true;
        }
        
        // Return true if any non-foot actor is found ahead
        return ahead != null;
    }
}
