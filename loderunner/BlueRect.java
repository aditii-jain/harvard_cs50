import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BlueRect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BlueRect extends Actor
{
    
    /**
     *
     * Act - do whatever the BlueRect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public BlueRect(int width, int height) {
        GreenfootImage blueRect = new GreenfootImage(width, height);
        Color blue = new Color​(41,173,255);
        blueRect.setColor(blue);
        blueRect.fill();
        setImage(blueRect);
        
    }
}
