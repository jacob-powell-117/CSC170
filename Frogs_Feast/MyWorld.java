import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class MyWorld extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;
    private int currentLevel = 1; //Start at level 1
    private static final int FINAL_LEVEL = 7; //Ends on after level 7
    private int score = 0; //Frog's score, determines when level up
    private static final int SCORE_TO_LEVEL_UP = 25;
    private Scoreboard scoreboard;
    
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(WIDTH, HEIGHT, 1);
        
        prepare();
    }
    
    private void prepare() 
    {
        // 1. Clear the world first for a new level
        removeObjects(getObjects(null)); 
    
        // BASE NUMBERS (Used for Level Scaling)
        final int BASE_ROCKS = 5;
        final int BASE_GRASS = 7;
        final int BASE_MUSHROOMS = 5;
        final int BASE_LADYBUGS = 4;
        final int BASE_FLIES = 2;
        final int BASE_SPIDERS = 1; // Base number for spiders (usually 1, scales up)

        int scaleFactor = currentLevel;

        // --- Spawn Environmentals (Set the board) ---
        int totalRocks = BASE_ROCKS + (int)(BASE_ROCKS * (scaleFactor / 2.0));
        int totalGrass = BASE_GRASS + (int)(BASE_GRASS * (scaleFactor / 2.0));
        int totalMushrooms = BASE_MUSHROOMS + (int)(BASE_MUSHROOMS * (scaleFactor / 2.0));

        spawnActors(rock.class, totalRocks);
        spawnActors(grass.class, totalGrass); 
        spawnActors(mushroom.class, totalMushrooms);

        // --- Spawn Food ---
        int totalLadybugs = BASE_LADYBUGS + (int)(BASE_LADYBUGS * (scaleFactor / 2.0));
        int totalFlies = BASE_FLIES + (int)(BASE_FLIES * (scaleFactor / 2.0));

        spawnActors(ladybug.class, totalLadybugs);
        spawnActors(fly.class, totalFlies);
    
        // --- Spawn Spider Hazard ---
        // The number of spiders also scaleS with the level for increased difficulty.
        int totalSpiders = BASE_SPIDERS * (currentLevel / 2 + 1); 
        spawnActors(spider.class, totalSpiders);


        // The frog is always placed in the center
        addObject(new frog(), WIDTH / 2, HEIGHT / 2);
    
        // --- Spawn Scoreboard ---
        scoreboard = new Scoreboard(score, currentLevel);
        addObject(scoreboard, WIDTH - 150, 20); // Place near top right
    }

    private void spawnActors(Class<? extends Actor> actorClass, int count)
    {
        for (int i = 0; i < count; i++)
        {
            int[] coords = findEmptyLocation();
            if (coords != null) {
                try {
                    addObject(actorClass.getDeclaredConstructor().newInstance(), coords[0], coords[1]);
                } catch (Exception e) {
                    return;
                }
            } else {
                break;
            }
        }
    }
    
    private int[] findEmptyLocation()
    {
        final int MAX_ATTEMPTS = 100;
    // NEW: Define a safety radius around the frog's starting position
    final int FROG_SAFETY_RADIUS = 200; 
    final int FROG_START_X = getWidth() / 2;
    final int FROG_START_Y = getHeight() / 2;
    
    for (int i = 0; i < MAX_ATTEMPTS; i++)
        {
            int x = Greenfoot.getRandomNumber(getWidth());
            int y = Greenfoot.getRandomNumber(getHeight());
        
            // --- NEW: Check if the random location is too close to the frog's start ---
            int distanceSq = (x - FROG_START_X) * (x - FROG_START_X) + (y - FROG_START_Y) * (y - FROG_START_Y);
        
            if (distanceSq < FROG_SAFETY_RADIUS * FROG_SAFETY_RADIUS) {
                continue; // Too close to the frog, try again
            }
            // --------------------------------------------------------------------------
        
            if (getObjectsAt(x, y, Actor.class).isEmpty())
        {
            return new int[]{x, y};
        }
    }
        return null;
    }
    
    public void increaseScore(int amount)
    {
        score += amount;
        scoreboard.updateScore(score);
        
        // Check for level advancement
        if (score >= SCORE_TO_LEVEL_UP) {
            advanceLevel();
        }
    }
    
    private void advanceLevel()
    {
        if (currentLevel < FINAL_LEVEL) {
            // Reset score for the new level
            score = 0;
            currentLevel++;
            
            // Update the scoreboard
            scoreboard.updateLevel(currentLevel);
            
            //Display notification
            showText("LEVEL " + currentLevel + " START!", WIDTH / 2, HEIGHT / 2);
            Greenfoot.delay(60); // Pause for a moment
            showText("", WIDTH / 2, HEIGHT / 2); // Clear message
            
            // Repopulate the world
            prepare();
        } else {
            // Final Level reached
            endGame(true);
        }
    }
    
    public void endGame(boolean win)
    {
        Greenfoot.stop();
        
        if (win) {
            showText("CONGRATULATIONS! YOU WON!", WIDTH / 2, HEIGHT / 2);
            Greenfoot.playSound("win.mp3");
        } else {
            showText("GAME OVER! Score: " + score + " Level: " + currentLevel, WIDTH / 2, HEIGHT / 2);
            Greenfoot.playSound("lose.mp3");
        }
    }
}
