import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Person
{
    public boolean isTouchingEnemy(){
        return isTouching(Enemy.class);
    }

    public String getCommand() {
        MyLevelWorld world = (MyLevelWorld)getWorld();
        if (world.getGameOver()) {
            return "";
        } else {
            if (Greenfoot.isKeyDown("down")) {
                return "down";
            } else if (Greenfoot.isKeyDown("right")) {
                return "right";
            } else if (Greenfoot.isKeyDown("left")) {
                return "left";
            } else if (Greenfoot.isKeyDown("up")) {
                return "up";
            }
            return "";
        }

        
    }

    public String getPerson() {
        return "player";
    }
}
