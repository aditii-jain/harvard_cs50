import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Person here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Person extends Actor
{
    int playerW = getImage().getWidth();
    int playerH = getImage().getHeight();
    private int num = 0;
    boolean isTouchingLadder;
    Actor belowLeftLadder;
    Actor belowRightLadder;
    Actor aboveLeftLadder;
    Actor aboveRightLadder;
    private int barNum = 0;
    String command;
    Actor leftPlatform;
    Actor rightPlatform;
    boolean isWalking = false;
    Brick leftBrick;
    Brick rightBrick;
    int score = 0;
    int level = 1;
    int lives = 5;
    boolean wasGoingUp = false;
    Actor leftBar;
    Actor rightBar;
    boolean canTakeCommand = false;

    public String getCommand() {
        return "blablabla";
    }

    public boolean isTouchingLadder()  {
        if (belowLeftLadder!=null||belowRightLadder!=null||aboveLeftLadder!=null||
        aboveRightLadder!=null) {
            return true;
        }
        return false;
    }

    public String getPerson() {
        return "anything";
    }

    // public int getScore() {
    // return score;
    // }

    // public int getLives() {
    // return lives;
    // }

    // public int getLevel() {
    // return level;
    // }

    public boolean isTouchingGold() {
        if (getPerson().equals("player") && isTouching(Gold.class)) {
            removeTouching(Gold.class);
            return true;
        }
        return false;
    }

    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        command = getCommand();
        //  (getObjectsInRange​((playerW/2)+12, Brick.class) == null)
 
        leftPlatform  = getOneObjectAtOffset(-playerW/2, playerH/2, Brick.class);
        rightPlatform  = getOneObjectAtOffset(playerW/2, playerH/2, Brick.class);
    
        Actor checkPlatform = getOneObjectAtOffset(-playerW/2, (playerH/2)+2, Brick.class);
        
        if (checkPlatform!=null) {
            if (leftPlatform == null && rightPlatform == null && !isTouchingLadder && (leftBar == null || rightBar == null)) {
                if (((checkPlatform.getY() - checkPlatform.getImage().getHeight()/2) - (getY()+playerH/2))<=2) {
                    setLocation (getX(), (checkPlatform.getY() - checkPlatform.getImage().getHeight()/2 - playerH/2));
                }
            }
        }
        
        rightBrick = (Brick) getOneObjectAtOffset(playerW/2+12, 0, Brick.class);
        leftBrick = (Brick) getOneObjectAtOffset(-playerW/2-12, 0, Brick.class);
        belowLeftLadder = getOneObjectAtOffset(-playerW/2, playerH/2, Ladder.class); // below
        belowRightLadder = getOneObjectAtOffset(playerW/2, playerH/2, Ladder.class); // below
        aboveLeftLadder = getOneObjectAtOffset(-playerW/2, -playerH/2, Ladder.class); // above
        aboveRightLadder = getOneObjectAtOffset(playerW/2, -playerH/2, Ladder.class); // above
        isTouchingLadder = isTouching(Ladder.class);
        leftBar = getOneObjectAtOffset(-playerW/2, -playerH/2, Bar.class);
        rightBar = getOneObjectAtOffset(playerW/2, -playerH/2, Bar.class);

        if (leftBar!= null || rightBar!=null) {
            if (command.equals("down"))  {
                setImage(getPerson() + "_bar_hang_01.png");
                setRotation(90);
                for (int i = 0; i < 3; i++) {
                    rightPlatform  = getOneObjectAtOffset(playerW/2, playerH/2, Brick.class);
                    leftPlatform  = getOneObjectAtOffset(-playerW/2, playerH/2, Brick.class);
                    if (rightPlatform == null && leftPlatform == null) {
                        move(1);
                    }
                }
                setRotation(0);
            } else { 
                if (leftBar!=null) {
                    setLocation(getX(), leftBar.getY()+playerH/2); 
                } else {
                    setLocation(getX(), rightBar.getY()+playerH/2);
                }

                if (command.equals("right")) { // rightKey
                    if (getX() + playerW/2 < getWorld().getWidth()) {
                        setImage(getPerson() + "_bar_hang_0" + barNum + ".png");
                        if (barNum == 1) {
                            barNum = 0;
                        }
                        barNum++;
                        setRotation(0);
                        move(2); 
                    }

                } else if (command.equals("left")) { // leftKey
                    if (getX() - playerW/2 > 0) {
                        setImage(getPerson()+ "_bar_hang_0" + barNum + ".png");
                        if (barNum == 1) {
                            barNum = 0;
                        }
                        barNum++;
                        GreenfootImage img = getImage();
                        img.mirrorVertically();
                        setImage(img);
                        setRotation(180);
                        move(2); 
                    }
                }
            }
        }

        if (!(command.equals("left") || command.equals("right")) && 
        (command.equals("up") || command.equals("down"))) { 
            // touching ladder and not pressing left or right
            if (belowLeftLadder!=null) {
                setLocation(belowLeftLadder.getX(),getY());
            } else if(belowRightLadder!=null) {
                setLocation(belowRightLadder.getX(),getY());
            } else if (aboveLeftLadder!=null) {
                setLocation(aboveLeftLadder.getX(),getY());
            } else if (aboveRightLadder!=null) {
                setLocation(aboveRightLadder.getX(),getY());
            }
            
            if ((aboveLeftLadder != null || aboveRightLadder != null) || wasGoingUp) {
                if (wasGoingUp) {
                    if ((belowLeftLadder != null || belowRightLadder != null)) {
                        wasGoingUp = false;
                    }
                }
                if (command.equals("up")) {
                    wasGoingUp = true;
                    setImage(getPerson()+"_climb_ladder.png");
                    setRotation(270);
                    for (int i = 0; i < 3; i++) {
                        move(1);
                    } 
        
                    setRotation(0);
                }
            }

            if ((belowLeftLadder != null || belowRightLadder != null))  {
                if (command.equals("down")) {
                    setImage(getPerson()+"_climb_ladder.png");
                    setRotation(90);
                    if (getPerson().equals("player")) {
                        for (int i = 0; i < 3; i++) {
                            move(1);
                        } 
                    } else {
                        for (int i = 0; i < 2; i++) {
                            move(1);
                        }
                    }
                    setRotation(0);
                }
            }
        }

        if (leftPlatform==null && rightPlatform==null && (belowLeftLadder == null && belowRightLadder == null) && (leftBar==null && rightBar == null)) {
            
            // there is no platform and no ladder beneath and not touching bar // fall
            setImage(getPerson()+"_bar_hang_01.png");
            setRotation(90);
            for (int i = 0; i < 3; i++) {
                leftPlatform = getOneObjectAtOffset(-playerW/2, playerH/2, Brick.class);
                rightPlatform = getOneObjectAtOffset(playerW/2, playerH/2, Brick.class);
                if (leftPlatform == null && rightPlatform == null) {
                    move(1);
                }
                //System.out.println("moved down " + i + " times");
            }
            setRotation(0);

        } else if (leftPlatform!=null || belowLeftLadder!=null || belowRightLadder!=null || rightPlatform!=null) {
            // there is platform beneath or touching ladder
            //if (platform!=null) {
            if (leftPlatform != null) {
                if ((getY()+playerH/2<=leftPlatform.getY()-leftPlatform.getImage().getHeight()/2)) {
                    canTakeCommand = true;
                } 
            } else if (rightPlatform != null) {
                if ((getY()+playerH/2<=rightPlatform.getY()-rightPlatform.getImage().getHeight()/2)) {
                    canTakeCommand = true;
                }
            } else if (belowLeftLadder!=null || belowRightLadder!=null) {
                canTakeCommand = true;
            } else {
                canTakeCommand = false;
            }
            if (canTakeCommand) {
                if (command.equals("right")) { // rightKey
                    if (getX() + playerW/2 < getWorld().getWidth()) {
                        setImage(getPerson()+"_run_0" + num + ".png");
                        num++;
                        if (num == 3) {
                            num = 0;
                        }
                        setRotation(0);
                        if (getPerson().equals("player")) {
                            move(2); 
                        } else {
                            move(3/2); 
                        }

                    }
                } else if (command.equals("left")) { // leftKey
                    if (getX() - playerW/2 > 0) {
                        setImage(getPerson()+"_run_0" + num + ".png");
                        GreenfootImage img = getImage();
                        img.mirrorVertically();
                        setImage(img);
                        num++;
                        if (num == 3) {
                            num = 0;
                        }
                        setRotation(180);
                        if (getPerson().equals("player")) {
                            move(2); 
                        } else {
                            move(3/2); 
                        }
                    }
                }
                canTakeCommand = false;
            }
        }

    }

}
