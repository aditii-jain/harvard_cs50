import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MenuPlayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MenuPlayer extends Actor
{
    /**
     * Act - do whatever the MenuPlayer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public MenuPlayer(){
        GreenfootImage img = getImage();
        img.scale(30, 50);
        setImage(img);
    }
}
