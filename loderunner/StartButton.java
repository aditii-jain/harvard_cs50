import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StartButton extends Actor
{
    public void scaleAndColor() {
        
        
        if (first) {
            setImage("darkerButton.png");
            GreenfootImage img = getImage();
            img.scale(400, 240);
            //img.setColor(lighter);
            setImage(img);
            first = false;
        } else if (count>20 && !done){
            //System.out.println("here");
            setImage("lighterButton.png");
            GreenfootImage img = getImage();
            img.scale(440, 280);
            //img.setColor(darker);
            setImage(img);
            done = true;
        } else {
            count++;
        }
        
        
        
        
        
    }
    //Color lighter = new Color(340,250,1);
    //Color darker = new Color(25,195,0);
    boolean first = true;
    boolean done = false;
    int count = 0;
    int worldcounter = 0;
    boolean hasBeenClicked = false;
    // GreenfootSound​(java.lang.String filename)
    //GreenfootSound​clickSound = new GreenfootSound("Mouse Click - Sound Effect.mp3");
    /**
     * Act - do whatever the StartButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {  
        
        if (Greenfoot.mouseClicked(this) && !hasBeenClicked) {
            Greenfoot.playSound("mouseclick.mp3");
            hasBeenClicked = true;
        }
        if (hasBeenClicked) {
            scaleAndColor();
            if (worldcounter == 45) {
                MyLevelWorld myWorld = new MyLevelWorld();
                Greenfoot.setWorld(myWorld);
            } else {
                worldcounter++;
            }
        }
        
        
    
        // Add your action code here.
    }
}
