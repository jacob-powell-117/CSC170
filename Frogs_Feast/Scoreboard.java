import greenfoot.*;

public class Scoreboard extends Actor
{
    private String text;
    private int scoreValue;
    private int levelValue;
    
    // Constructor to set up the initial display
    public Scoreboard(int initialScore, int initialLevel)
    {
        this.scoreValue = initialScore;
        this.levelValue = initialLevel;
        updateImage();
    }
    
    // Method to update the score (called by MyWorld)
    public void updateScore(int newScore)
    {
        this.scoreValue = newScore;
        updateImage();
    }
    
    // Method to update the level (called by MyWorld)
    public void updateLevel(int newLevel)
    {
        this.levelValue = newLevel;
        updateImage();
    }

    // Method to generate and set the image for the actor
    private void updateImage()
    {
        // Format the text to display score and level
        text = "Score: " + scoreValue + " | Level: " + levelValue;
        
        // Create a new image with the text
        GreenfootImage img = new GreenfootImage(text, 28, Color.WHITE, new Color(0, 0, 0, 100));
        
        setImage(img);
    }
}