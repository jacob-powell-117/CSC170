import greenfoot.*;

public class frog extends Actor
{
    private static final int MOVE_SPEED = 4;
    // How far ahead to check for solid obstacles (rocks/plants)
    private static final int LOOK_AHEAD_DISTANCE = 10; 
    
    public void act()
    {
        if (getWorld() == null) {
        return; 
    }
        
        // 1. Handle user input (now includes obstacle check)
        checkKeyInput();
        
        // 2. Check for collisions with food and hazards
        checkCollisions();
    }
    
    // Helper for movement logic
    private void checkKeyInput()
    {
        int targetX = getX();
        int targetY = getY();
        
        // Determine the target coordinates based on key presses
        if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) {
            targetX += MOVE_SPEED;
        }
        
        // Move Left
        if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) {
            targetX -= MOVE_SPEED; // Corrected to subtract speed
        }
        
        if (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("w")) {
            targetY -= MOVE_SPEED;
        }
        
        if (Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("s")) {
            targetY += MOVE_SPEED;
        }
        
        // Only move if the target location is not an obstacle
        if (!isObstacleAt(targetX, targetY)) {
             setLocation(targetX, targetY);
        }
    }
    
    /**
     * Checks if the given coordinates are occupied by a solid obstacle.
     * @param x The target X coordinate.
     * @param y The target Y coordinate.
     * @return true if a rock or plant is present, false otherwise.
     */
    private boolean isObstacleAt(int x, int y)
    {
        // Check for rocks
        if (!getWorld().getObjectsAt(x, y, rock.class).isEmpty()) {
            return true;
        }
        
        // Check for grass
        if (!getWorld().getObjectsAt(x, y, grass.class).isEmpty()) {
            return true;
        }
        
        // Check for mushrooms
        if (!getWorld().getObjectsAt(x, y, mushroom.class).isEmpty()) {
            return true;
        }
        
        // Check for world boundaries 
        if (x <= 0 || x >= getMyWorld().getWidth() - 1 || 
            y <= 0 || y >= getMyWorld().getHeight() - 1) 
        {
            return true;
        }

        return false;
    }
    
    // Access to the world to use its methods
    public MyWorld getMyWorld()
    {
        return (MyWorld) getWorld();
    }
    
    // Helper for collision logic 
    private void checkCollisions()
    {
        // This variable will be null if the actor was removed in the current act cycle.
    greenfoot.World currentWorld = getWorld();
    if (currentWorld == null) {
        return; 
    }
    // ******************************************************
    
    // Get any intersecting spider
    spider hazard = (spider) getOneIntersectingObject(spider.class);
    if (hazard != null) {
        // Collision with a spider is the LOSE scenario
        getMyWorld().endGame(false); 
        return;
    }
    
    // Collision with fly (high reward)
    fly highReward = (fly) getOneIntersectingObject(fly.class);
    if (highReward != null) {
        getMyWorld().increaseScore(8); 
        // Use the safely retrieved currentWorld variable for removal!
        currentWorld.removeObject(highReward); 
        Greenfoot.playSound("chomp.mp3");
        return;
    }
    
    // Collision with ladybug (low reward)
    ladybug lowReward = (ladybug) getOneIntersectingObject(ladybug.class);
    if (lowReward != null) {
        getMyWorld().increaseScore(2);
        // Use the safely retrieved currentWorld variable for removal!
        currentWorld.removeObject(lowReward);
        Greenfoot.playSound("chomp.mp3");
    }
}
}