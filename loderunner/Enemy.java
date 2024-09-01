import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and playerInfo)
import java.util.List;

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Person
{
    public boolean ladderToLeft() {
        for (int i = 0; i >= -getX(); i-=2) {
            leftLadder = getOneObjectAtOffset(-playerW/2 + i, playerH/2, Ladder.class);
            if (leftLadder!=null)  {
                return true;
            }
            
        }
        return false;
    }
    Player player;
    Actor leftLadder;
    boolean onTheWayUp = false;
    boolean onTheWayDown = false;
    Brick futureleftPlatform;
    Brick futurerightPlatform;
    public String getPerson() {
        return "enemy";
    }
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public String getCommand() {
        Ladder ladder = new Ladder();
        Brick brick = new Brick();
        
        //System.out.println(ladder.getImage().getWidth());
        
        MyLevelWorld world = (MyLevelWorld)getWorld();
        if (world.getGameOver()) {
            return "";
        }
        boolean isTouchingLadder = isTouching(Ladder.class);
        // check coordinates of ladder to see if up or down, then decide
        
        leftPlatform  = getOneObjectAtOffset(-playerW/2, playerH/2, Brick.class);
        rightPlatform  = getOneObjectAtOffset(playerW/2, playerH/2, Brick.class);
        
        
        futureleftPlatform  = (Brick)getOneObjectAtOffset(-playerW/2-12, playerH/2, Brick.class);
        futurerightPlatform  = (Brick)getOneObjectAtOffset(playerW/2+12, playerH/2, Brick.class);
        
        leftBar = getOneObjectAtOffset(-playerW/2, -playerH/2, Bar.class);
        rightBar = getOneObjectAtOffset(playerW/2, -playerH/2, Bar.class);
        int count = 0;
        List <Player> players = getWorld().getObjects(Player.class);
        player = players.get(0);
        
        String command = "";
        List<Brick> bricksInProximity = getObjectsInRange(player.getImage().getWidth()/2 , Brick.class);
        
        belowLeftLadder = getOneObjectAtOffset(-playerW/2, playerH/2, Ladder.class); // below
        belowRightLadder = getOneObjectAtOffset(playerW/2, playerH/2, Ladder.class); // below
        
        aboveLeftLadder = getOneObjectAtOffset(-playerW/2 , -playerH/2, Ladder.class); // above
        aboveRightLadder = getOneObjectAtOffset(playerW/2, -playerH/2, Ladder.class); // above
        
        Actor belowLadder = getOneObjectAtOffset(0, playerH/2, Ladder.class);
        Actor aboveLadder = getOneObjectAtOffset(0, 0, Ladder.class);
        Actor bar = getOneObjectAtOffset(-playerW/2, -playerH/2, Bar.class); 
        
        Actor belowLadderForUpMovement = getOneObjectAtOffset(0, playerH/2 - 6, Ladder.class);
        
        if (bar != null || isTouching(Bar.class)) {
            snapToLadder();
            if (playerToRight()) {
                //System.out.println("5");
                return "right";
            } else if (playerToLeft()) {
                return "left";
            } else if (playerBelow()) {
                return "down";
            } 
        }
        
        if (isTouchingLadder) {
            if (playerBelow()){
                if (belowLadder!=null) {
                    onTheWayDown = true;
                    return "down";
                }
            }
            if (onTheWayDown && playerBelow()) {
                if (rightPlatform == null) {
                    //System.out.println("on ladder and no leftPlatform beneath");
                    return "down";
                } else {
                    onTheWayDown = false;
                    if (leftPlatform!=null) {
                        if (playerToLeft()) {
                            return "left";
                        }
                        if (playerToRight()){
                            return "right";
                        }
                    }
                }
            }
            
            if (playerAbove()){
                if (aboveLadder!=null || aboveLeftLadder != null || aboveRightLadder != null) {
                    onTheWayUp = true;
                    return "up";
                }
            }
            if (onTheWayUp && playerAbove()) {
                if (belowLadder != null && (futureleftPlatform == null || futurerightPlatform == null)) {
                    onTheWayUp = true;
                    return "up";
                } else {
                    //System.out.println("found right platform: x:  " + rightPlatform.getX() +" y: "+ rightPlatform.getY());
                    // if the top edge of the right platform is above the foot of the player keep going up
                    getOnPlatform();
                    onTheWayUp = false;
                    
                    if (playerToLeft()) {
                        return "left";
                    }
                    if (playerToRight()){
                        return "right";
                    }
                    
                }
            }
            
            if (leftPlatform!=null||rightPlatform!=null) {
                if (playerToLeft()) {
                    return "left";
                }
                if (playerToRight()){
                    return "right";
                }
            }
        }
        
        if (leftPlatform!=null || rightPlatform!=null) {
            if (playerToLeft()) {
                return "left";
            }
            if (playerToRight()){
                return "right";
            }
        }
        
        if (futureleftPlatform != null || futurerightPlatform != null) {
            if (playerToLeft()) {
                return "left";
            }
            if (playerToRight()) {
                return "right";
            }
        }
        return "";
    }
    
    public void getOnPlatform() {
        int offset = 0;
        if (playerToRight()) {
            setLocation(getX() + 6, futurerightPlatform.getY() - futurerightPlatform.getImage().getHeight()/2 - getImage().getHeight()/2);
        }  else {
            setLocation(getX() - 6, futureleftPlatform.getY() - futureleftPlatform.getImage().getHeight()/2 - getImage().getHeight()/2);
        }       
    }
    
    
    public void snapToLadder()  {
        Ladder leftBarLadder = (Ladder)getOneObjectAtOffset(-playerW/2-12, -playerH/2, Ladder.class);
        if (leftBarLadder!=null) {
            setLocation(leftBarLadder.getX(),getY());
        }
        
        Ladder rightBarLadder = (Ladder)getOneObjectAtOffset(playerW/2+12, -playerH/2, Ladder.class);
        if (rightBarLadder!=null) {
            setLocation(rightBarLadder.getX(),getY());
        }
    }
    public boolean playerToRight() {
        if (player!=null) {
            if (player.getX() > (getX() + playerW/2)) {
                return true;
            }
        }
        return false;
    }

    public boolean playerToLeft() {
        if (player!=null) {
            if (player.getX() < (getX() - playerW/2)) {
                return true;
            }
        }
        return false;
    }

    public boolean playerAbove() {
        if (player!=null) {
            if (player.getY() < getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean playerBelow() {
        if (player!=null) {
            if (player.getY() > getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean ladderBelow() {
        if (belowLeftLadder!=null||belowRightLadder!=null) {
            return true;
        }
        return false;
    }
}
