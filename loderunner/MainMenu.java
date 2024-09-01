import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MainMenu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MainMenu extends World
{

    /**
     * Constructor for objects of class MainMenu.
     * 
     */
    boolean rightEdge = false;
    MenuPlayer menuplayer =  new MenuPlayer();
    int num = 0;
    public MainMenu()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1, false); 
        GreenfootImage background = new GreenfootImage​(getWidth(),getHeight());
        background.setColor(Color.BLACK);
        background.fillRect(0, 0, getWidth(), getHeight());
        setBackground​(background); 
        Text title = new Text("LODE RUNNER", Color.WHITE, 40);
        //Font font = new Font("OptimusPrinceps", 40);
        //title.setFont(font);
        addObject(title, getWidth()/2, getHeight()/3);

        StartButton start = new StartButton();
        addObject(start, getWidth()/2, 2*(getHeight()/3));

        addObject(menuplayer, menuplayer.getImage().getWidth()/2,getHeight()-menuplayer.getImage().getHeight()/2);
    }

    public void act() {
        
         if (menuplayer.getX() + menuplayer.getImage().getWidth()/2 >= getWidth()) {
             rightEdge = true;
        }
        if (menuplayer.getX() - menuplayer.getImage().getWidth()/2 <= 0) {
            rightEdge = false;
        }
       
        if (!rightEdge) {
            menuplayer.setImage("player_run_0" + num +".png");
            GreenfootImage img = menuplayer.getImage();
            img.scale(30,50);
            menuplayer.setImage(img);
            num++;
            if (num == 3) {
                num = 0;
            }
            menuplayer.setRotation(0);
            menuplayer.move(2); 
        } else {
            menuplayer.setImage("player_run_0" + num + ".png");
            GreenfootImage img = menuplayer.getImage();
            img.mirrorVertically();
            img.scale(30,50);
            menuplayer.setImage(img);
            num++;
            if (num == 3) {
                num = 0;
            }
            menuplayer.setRotation(180);
            menuplayer.move(2); 
        }
    }
}

