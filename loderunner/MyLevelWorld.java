import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * use this class as a starting point for your Lode Runner. Transfer any extra methods or fields you need from your original class.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyLevelWorld extends LevelWorld {
    boolean changedScore = true;
    boolean changedLives = true;
    boolean changedLevel = true;
    int prvsLives = 1;
    int prvsLevel = 0;
    int prvsScore = 1;
    int count = 0;
    
    //int level;
    int score;
    Text scoreText;
    Text menText;
    Text levelText;
    Player player;
    Brick brick = new Brick();
    Ladder ladder = new Ladder();
    Color blue = new Color​(41,173,255);
    int gridWidth = brick.getImage().getWidth();
    int gridHeight = ladder.getImage().getHeight();
    List <Player> players;
    
    
    public boolean getGameOver() {
        return super.isGameOver;
    }
    // add instance variables to represent the lives and score
    
    
    // static initialization code - runs when the class is loaded, before the main method is called.
    // You can only call static methods and only access static fields.
    // In this case, we need to initialize the margins BEFORE the world is created
    static {
        // set the margins (open space) on the left, right, top and bottom sides of the world
        // The level will be drawn with the given spaces open on each side
        // Set the bottom margin to the height of your HUD
        LevelWorld.setMargins(0, 0, 0, 25);
    }
    
    // Default Constructor (loads the first txt in the levels folder)
    public MyLevelWorld() {
        this(1, 5, 0); // load level 1
        
    }
    
    // loads the level given. For example, if level was 3, it would load the third txt file in the levels folder
    // You can add parameters to this constructor for lives and score. If you do, you need to pass default lives
    // and score values when you call this(1) in the default constructor.
    public MyLevelWorld(int level, int lives, int score) {
        super(level);
        super.lives = lives;
        super.score = score;
        players = getObjects(Player.class);
        if (players.size() > 0) {
            player = players.get(0);
        } 
        setPaintOrder(Player.class, Enemy.class, Ladder.class);
        
        scoreText = new Text("SCORE " + score, blue, 20);
        addObject(scoreText, scoreText.getImage().getWidth()/2, getHeight()- scoreText.getImage().getHeight()/2);
        
        BlueRect blueRect = new BlueRect(getWidth(),6);
        addObject(blueRect, getWidth()/2, getHeight() - scoreText.getImage().getHeight());
        
        menText = new Text("MEN " + lives, blue, 20);
        addObject(menText, getWidth()/2, getHeight()- menText.getImage().getHeight()/2);
        
        levelText = new Text("LEVEL " + level, blue, 20);
        addObject(levelText, getWidth()- levelText.getImage().getWidth()/2, getHeight()- levelText.getImage().getHeight()/2);
        
        super.music.playLoop();
        
    }
    
   
    public int generateGridCoordinates(int coordinate) {
        int remainder = coordinate % 12;
        return coordinate - remainder;
    }
    
    
    @Override
    public void defineClassTypes() {
        // define which classes represent walls, ladders, bars, players, and enemies
        // TODO: REPLACE WITH YOUR CLASSES
        getLoader().setWallClass(Brick.class);
        getLoader().setPlayerClass(Player.class);
        getLoader().setLadderClass(Ladder.class); // you can remove this if you have no ladders in your game
        getLoader().setBarClass(Bar.class); // you can remove this if you have no bars in your game
        getLoader().setEnemyClass(Enemy.class); // you can remove this if you have no enemies in your game
        getLoader().setGoldClass(Gold.class); // you can remove this if you have no gold in your game
    }
    public void updateLives() {
        //super.lives = player.getLives();
        GreenfootImage updatedLives = new GreenfootImage("MEN " + super.lives, 20, blue, null);
        menText.setImage(updatedLives);
        menText.setLocation(getWidth()/2, getHeight()- menText.getImage().getHeight()/2);
    }
    
    public void updateScore() {
        //score = player.getScore();
        GreenfootImage updatedScore = new GreenfootImage("SCORE " + super.score, 20, blue, null);
        scoreText.setImage(updatedScore);
        scoreText.setLocation(scoreText.getImage().getWidth()/2, getHeight()- scoreText.getImage().getHeight()/2);
    }
    
    public void updateLevel() {
        //level = player.getLevel();
        GreenfootImage updatedLevel = new GreenfootImage("LEVEL " + super.level, 20, blue, null);
        levelText.setImage(updatedLevel);
        levelText.setLocation(getWidth()- levelText.getImage().getWidth()/2, getHeight()- levelText.getImage().getHeight()/2);
    }
    public void act() {
        
        if (player.isTouchingGold()) {
            super.score += 250;
        }
        
        
        if (player.isTouchingEnemy()) {
            if (super.lives>0) {
                super.lives--;
                super.score = 0;
            }
            
            //System.out.println(lives);
            if (super.lives == 0) {
                // Text (String text, Color color, int size)
                Text gameOver = new Text("GAME OVER, \"reloding\"...", Color.RED, 40);
                addObject(gameOver, getWidth()/2, getHeight()/2);
                super.isGameOver = true;
                if (count>300) {
                    super.music.stop();
                    MainMenu menu = new MainMenu();
                    Greenfoot.setWorld(menu);
                } else {
                    count++;
                }
            } else {
                super.music.stop();
                MyLevelWorld newWorld = new MyLevelWorld(super.level, super.lives, super.score);
                Greenfoot.setWorld(newWorld);
            }
        }
        

        List <Gold> gold = getObjects(Gold.class);
        if (gold.size() == 0) {
            if (super.level==3) {
                Text gameOver = new Text("YOU WON!!", Color.RED, 40);
                addObject(gameOver, getWidth()/2, getHeight()/2);
                super.isGameOver = true;
                super.music.stop();
                MainMenu menu = new MainMenu();
                Greenfoot.setWorld(menu);
            } else {
                super.level++;
                super.lives++;
                super.music.stop();
                MyLevelWorld newWorld = new MyLevelWorld(super.level, super.lives, super.score);
                Greenfoot.setWorld(newWorld);
            }
        }
        
        List <Player> players = getObjects(Player.class);
        if (players.size() > 0) {
            player = players.get(0);
            if (super.score == prvsScore) {
                changedScore = false;
            } else {
                changedScore  = true;
                prvsScore = super.score;
            }      
            
            if (changedScore) {
                updateScore();
                changedScore = false;
            }
        }
        
        
        if (players.size() > 0) {
            player = players.get(0);
            if (super.lives == prvsLives) {
                changedLives = false;
            } else {
                changedLives  = true;
                prvsLives = super.lives;
            }            
            
            if (changedLives) {
                updateLives();
                changedLives = false;
            }
        }
        
        if (players.size() > 0) {
            player = players.get(0);
            if (super.level == prvsLevel) {
                changedLevel = false;
            } else {
                changedLevel  = true;
                prvsLevel = super.level;
            }            
            
            if (changedLevel) {
                updateLevel();
                changedLevel = false;
            }
        }
        
        
    }
}
