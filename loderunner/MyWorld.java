import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    int men = 5;
    int level = 1;
    Player player;
    boolean changedScore = true;
    boolean changedLives = true;
    boolean changedLevel = true;
    int prvsLives = 1;
    int prvsLevel = 0;
    int prvsScore = 1;
    Text scoreText;
    Text menText;
    Text levelText;
    Brick brick = new Brick();
    Ladder ladder = new Ladder();
    int ladderW = ladder.getImage().getWidth();
    int gridWidth = brick.getImage().getWidth();
    int gridHeight = ladder.getImage().getHeight();
    boolean first = true;
    Color blue = new Color​(41,173,255);
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {  
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1);
        
        
        GreenfootImage background = new GreenfootImage​(getWidth(),getHeight());
        background.setColor(Color.BLACK);
        background.fillRect(0, 0, getWidth(), getHeight());
        setBackground​(background); 
        
        
        drawRow(7,0,generateGridCoordinates(52));
        drawRow(13,0,generateGridCoordinates(155));
        drawRow(14,0,generateGridCoordinates(292));
        drawRow(5,generateGridCoordinates(290),generateGridCoordinates(195));
        drawRow((getWidth()/brick.getImage().getWidth())+1, 0, generateGridCoordinates((getHeight()-brick.getImage().getHeight()/2)-20));
        drawRow(9,generateGridCoordinates((getWidth()-9*brick.getImage().getWidth()) + brick.getImage().getWidth()/2),generateGridCoordinates(270));
        drawRow(3,generateGridCoordinates((getWidth()-3*brick.getImage().getWidth()) + brick.getImage().getWidth()/2),generateGridCoordinates(100));
        //System.out.println(brick.getImage().getHeight());
        drawLadder(4, generateGridCoordinates(brick.getImage().getWidth()*5), generateGridCoordinates(52));
        drawLadder(3, generateGridCoordinates(brick.getImage().getWidth()*8),generateGridCoordinates(292));
        drawLadder(7, generateGridCoordinates((getWidth()-2*brick.getImage().getWidth()) + ladderW/2),generateGridCoordinates(100));
        drawLadder(4, generateGridCoordinates(brick.getImage().getWidth()*13),generateGridCoordinates(200));
        drawLadder(2, generateGridCoordinates(brick.getImage().getWidth()*12),generateGridCoordinates(150));
        drawBar(10,generateGridCoordinates(320),generateGridCoordinates(155)-gridHeight/2);
        drawBar(6,generateGridCoordinates(410),generateGridCoordinates(195)-gridHeight/2);
        BlueRect blueRect = new BlueRect(getWidth(),6);
        addObject(blueRect, getWidth()/2, generateGridCoordinates(384) - gridHeight/2 + 3);
        player = new Player();
        addObject(player, getWidth()/2,generateGridCoordinates(340));
        
        showGold(24,26);
        showGold(79,336);
        showGold(330,170);
        showGold(104,268);
        showGold(250,125);
        showGold(540,340);
        showGold(454,240);
        showGold(570,80);
        
        
        // scoreText = new Text("SCORE " + player.getScore(), blue, 20);
        // addObject(scoreText, scoreText.getImage().getWidth()/2, getHeight()- scoreText.getImage().getHeight()/2);
        
        // menText = new Text("MEN " + player.getLives(), blue, 20);
        // addObject(menText, getWidth()/2, getHeight()- menText.getImage().getHeight()/2);
        
        // levelText = new Text("LEVEL " + player.getLevel(), blue, 20);
        // addObject(levelText, getWidth()- levelText.getImage().getWidth()/2, getHeight()- levelText.getImage().getHeight()/2);
        
        
        
    }
    
    public void showGold(int x, int y) {
        Gold gold = new Gold();
        int goldH = gold.getImage().getHeight();
        addObject(gold, generateGridCoordinates(x), generateGridCoordinates(y) + gridHeight/2 - goldH/2 - 2);
    }
    
    // public int generateBarCoordinates(int coordinate) {
        // int remainder = coordinate % 24;
        // return coordinate - remainder;
    // }
    public int generateGridCoordinates(int coordinate) {
        int remainder = coordinate % 12;
        return coordinate - remainder;
    }
    public void drawRow(int num, int xPos, int yPos) {
        Brick brick;
        for (int i = 0; i < num; i++) {
            brick = new Brick();
            addObject(brick, xPos + i*brick.getImage().getWidth(), yPos);
        }
    }
    
    public void drawBar(int num, int xPos, int yPos) {
        Bar bar;
        for (int i = 0; i < num; i++) {
            bar = new Bar();
            addObject(bar, xPos + i*bar.getImage().getWidth(), yPos);
        }
    }
    
    public void drawLadder(int height, int xPos, int yPos) {
        Ladder ladder;
        for (int i = 0; i < height; i++) {
            ladder = new Ladder();
            addObject(ladder, xPos, yPos + i*ladder.getImage().getHeight());
        }
  
    }
    
    // public void updateLives() {
        // GreenfootImage updatedLives = new GreenfootImage("MEN " + player.getLives(), 20, blue, null);
        // menText.setImage(updatedLives);
        // menText.setLocation(getWidth()/2, getHeight()- menText.getImage().getHeight()/2);
    // }
    
    // public void updateScore() {
        // GreenfootImage updatedScore = new GreenfootImage("SCORE " + player.getScore(), 20, blue, null);
        // scoreText.setImage(updatedScore);
        // scoreText.setLocation(scoreText.getImage().getWidth()/2, getHeight()- scoreText.getImage().getHeight()/2);
    // }
    
    // public void updateLevel() {
        // GreenfootImage updatedLevel = new GreenfootImage("LEVEL " + player.getScore(), 20, blue, null);
        // levelText.setImage(updatedLevel);
        // levelText.setLocation(getWidth()- levelText.getImage().getWidth()/2, getHeight()- levelText.getImage().getHeight()/2);
    // }
    
    // public void act()  {
        // List <Player> players = getObjects(Player.class);
        // if (players.size() > 0) {
            // player = players.get(0);
            // if (player.getScore() == prvsScore) {
                // changedScore = false;
            // } else {
                // changedScore  = true;
                // prvsScore = player.getScore();
            // }      
            
            // if (changedScore) {
                // updateScore();
                // changedScore = false;
            // }
        // }
        // // if (first) {
                    // // addObject(scoreText, scoreText.getImage().getWidth()/2, getHeight()- scoreText.getImage().getHeight()/2);
                    // // first = false;
                // // } else {
                    // // GreenfootImage updatedScore = new GreenfootImage("SCORE " + player.getScore(), 20, blue, null);
                    // // scoreText.getImage().drawImage​(updatedScore, scoreText.getImage().getWidth()/2, getHeight()- scoreText.getImage().getHeight()/2);
        // // }
        
        
        // if (players.size() > 0) {
            // player = players.get(0);
            // if (player.getLives() == prvsLives) {
                // changedLives = false;
            // } else {
                // changedLives  = true;
                // prvsLives = player.getLives();
            // }            
            
            // if (changedLives) {
                // updateLives();
                // changedLives = false;
            // }
        // }
        
        // if (players.size() > 0) {
            // player = players.get(0);
            // if (player.getLevel() == prvsLevel) {
                // changedLevel = false;
            // } else {
                // changedLevel  = true;
                // prvsLevel = player.getLevel();
            // }            
            
            // if (changedLevel) {
                // updateLevel();
                // changedLevel = false;
            // }
        // }
        
        
        
        
        
        
        
        
        
        
        
    // }
    
    
    
}
