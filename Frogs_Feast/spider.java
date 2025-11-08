import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class spider extends Actor
{
    // Speed should be slower than frog
    private static final int FULL_SPEED = 2;
    private static final int CRAWL_SPEED = 1;
    
    // Constant for checking a small distance ahead
    private static final int CHECK_DISTANCE = 3;
    
    public void act()
    {
        // Add your action code here.
        // 1. Find the target (the frog)
        frog player = findTarget();
        
        if (player != null)
        {
            // 2. Turn to face frog
            turnTowards(player.getX(), player.getY());
            
            // 3. Calculate Speed
            int speed = calculateSpeed();
            
            // 4. Move forward
            move(speed);
        }

    }
    
    private int calculateSpeed()
    {
        // Using Greefoot's look method to check a small distance ahead
        // Using getObjectsInRange(radius, class)
        if (!getObjectsInRange(CHECK_DISTANCE, Actor.class).isEmpty())
        {
            // Something (rock, plant, even an insect) is immediately in front
            return CRAWL_SPEED;
        }
        else
        {
            return FULL_SPEED;
        }
    }
    
    private frog findTarget()
    {
        // getObjects() returns a list of all objects of the specified class.
        // There will only be one frog, or return null
        if (getWorld().getObjects(frog.class).isEmpty()) {
            return null;
        }
        return (frog) getWorld().getObjects(frog.class).get(0);
    }
    
}
