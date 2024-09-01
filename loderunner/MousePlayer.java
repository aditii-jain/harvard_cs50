import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MousePlayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MousePlayer extends Player
{
    boolean onTheWayUp = false;
    boolean onTheWayDown = false;
    MouseInfo mouse;
    public boolean mouseToRight() {
        mouse = Greenfoot.getMouseInfo();
        if (mouse!=null) {
            if (mouse.getX() > (getX() + playerW/2)) {
                return true;
            }
        }
        return false;
    }

    public boolean mouseToLeft() {
        mouse = Greenfoot.getMouseInfo();
        if (mouse!=null) {
            if (mouse.getX() < (getX() - playerW/2)) {
                return true;
            }
        }
        return false;
    }

    public boolean mouseAbove() {
        mouse = Greenfoot.getMouseInfo();
        if (mouse!=null) {
            if (mouse.getY() < (getY() - playerH/2)) {
                return true;
            }
        }
        return false;
    }

    public boolean mouseBelow() {
        mouse = Greenfoot.getMouseInfo();
        if (mouse!=null) {
            if (mouse.getY() > (getY() + playerH/2)) {
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

    public String getCommand() {
        leftPlatform  = getOneObjectAtOffset(-playerW/2, playerH/2, Brick.class);
        rightPlatform  = getOneObjectAtOffset(playerW/2, playerH/2, Brick.class);
        belowLeftLadder = getOneObjectAtOffset(-playerW/2, playerH/2, Ladder.class); // below
        belowRightLadder = getOneObjectAtOffset(playerW/2, playerH/2, Ladder.class); // below
        aboveLeftLadder = getOneObjectAtOffset(-playerW/2 , -playerH/2, Ladder.class); // above
        aboveRightLadder = getOneObjectAtOffset(playerW/2, -playerH/2, Ladder.class); // above
        isTouchingLadder = isTouching(Ladder.class);
        Actor belowLadder = getOneObjectAtOffset(0, playerH/2, Ladder.class);
        Actor aboveLadder = getOneObjectAtOffset(0, 0, Ladder.class);
        Actor bar = getOneObjectAtOffset(-playerW/2, -playerH/2, Bar.class);
        
        if (bar != null) {
            if (mouseToRight()) {
                return "right";
            } else if (mouseToLeft()) {
                return "left";
            } else if (mouseBelow()) {
                return "down";
            } 
        }
        
        if (isTouchingLadder) {
            if (mouseBelow()){
                if (belowLadder!=null) {
                    onTheWayDown = true;
                    return "down";
                }
            }
            if (onTheWayDown) {
                if (leftPlatform==null && rightPlatform==null) {
                    //System.out.println("on ladder and no platform beneath");
                    return "down";
                } else {
                    onTheWayDown = false;
                    if (leftPlatform!=null && rightPlatform!=null) {
                        if (mouseToLeft()) {
                            return "left";
                        }
                        if (mouseToRight()){
                            return "right";
                        }
                    }
                }
            }
            
            if (mouseAbove()){
                
                if (aboveLadder!= null) {
                    onTheWayUp = true;
                    return "up";
                }
                
                
                
            }
            if (isTouching(Brick.class) && isTouching(Ladder.class) && ladderBelow()) {
                    onTheWayUp = true;
                    return "up";
            }
            
            if (onTheWayUp) { 
                if (ladderBelow()) {
                    if (aboveLeftLadder==null && aboveRightLadder==null){
                        onTheWayUp = false;
                        if (mouseToRight()){
                            return "right";
                        } else if (mouseToLeft()){
                             return "left";
                        }
                    }
                    return "up";
                } else {
                    onTheWayUp = false;
                }
            }
            if (leftPlatform!=null && rightPlatform!=null) {
                if (mouseToLeft()) {
                    return "left";
                }
                if (mouseToRight()){
                    return "right";
                }
            }
            
            
            
        }
        
        if (leftPlatform!=null && rightPlatform!=null) {
            if (mouseToLeft()) {
                return "left";
            }
            if (mouseToRight()){
                return "right";
            }
        }
        

        // if (mouseBelow() && mouseToRight()) {
        // if (belowLadder!=null) {
        // onTheWayDown = true;
        // return "down";
        // }
        // if (onTheWayDown) {
        // if (platform==null) {
        // //System.out.println("on ladder and no platform beneath");
        // return "down";
        // } else {
        // onTheWayDown = false;
        // }
        // }
        // return "right";
        // } else if (mouseAbove() && mouseToRight()) {
        // if (onTheWayDown) {
        // if (platform==null) {
        // //System.out.println("on ladder and no platform beneath");
        // return "down";
        // } else {
        // onTheWayDown = false;
        // }
        // }
        // if (onTheWayUp) {
        // if (ladderBelow()) {
        // return "up";
        // } else {
        // onTheWayUp = false;
        // }
        // }
        // if (aboveLadder != null) {
        // onTheWayUp = true;
        // return "up";
        // }
        // if (isTouching(Brick.class) && isTouching(Ladder.class) && ladderBelow()) {
        // return "up";
        // }  
        // return "right";
        // } else if (mouseBelow() && mouseToLeft()) {
        // if (ladderBelow()) {
        // return "down";
        // }
        // return "left";
        // } else if (mouseAbove() && mouseToLeft()) {
        // if (aboveLeftLadder!=null || aboveRightLadder!=null) {
        // return "up";
        // }
        // return "left";
        // } else if (mouseToRight()) {
        // // if (onTheWayUp) {
        // // if (platform==null) {
        // // //System.out.println("on ladder and no platform beneath");
        // // return "up";
        // // } else {
        // // onTheWayUp = false;
        // // }
        // // } 
        // if (onTheWayDown) {
        // if (platform==null) {
        // //System.out.println("on ladder and no platform beneath");
        // return "down";
        // } else {
        // onTheWayDown = false;
        // }
        // }
        // if (platform!=null || bar!=null) {
        // return "right";
        // }
        // }  else if (mouseToLeft()) {
        // return "left";
        // } else if (mouseAbove()) {
        // if (onTheWayDown) {
        // if (platform==null) {
        // //System.out.println("on ladder and no platform beneath");
        // return "down";
        // } else {
        // onTheWayDown = false;
        // }
        // }
        // return "up";
        // } else if (mouseBelow()) {
        // return "down";
        // }
        return "";
    }  
}

/**
 * Act - do whatever the MousePlayer wants to do. This method is called whenever
 * the 'Act' or 'Run' button gets pressed in the environment.
 */

