package com.zetcode;

import java.util.ArrayList;
import java.util.List;

public abstract class PathSearcher {

    public short[][] screenData;

    public List<Point> path = new ArrayList<Point>();

    public int curr_x =0, curr_y=0;

    private boolean canMoveLeft(){
        short ch;
        ch = screenData[curr_y][curr_x];
        if((ch & 1) != 0){
            return false;
        }
        return true;
    }
    private boolean canMoveUp(){
        short ch;
        ch = screenData[curr_y][curr_x];

        if((ch & 2) != 0){
            return false;
        }
        return true;
    }
    private boolean canMoveRight(){
        short ch;
        ch = screenData[curr_y][curr_x];

        if((ch & 4) != 0){
            return false;
        }
        return true;
    }
    private boolean canMoveDown(){
        short ch;
        ch = screenData[curr_y][curr_x];

        if((ch & 8) != 0){
            return false;
        }
        return true;
    }

    public void moveLeft(){
        path.add(new Point(curr_x, curr_y, Point.Direction.LEFT));
        curr_x -=1;
    }
    public void moveRight(){
        path.add(new Point(curr_x, curr_y, Point.Direction.RIGHT));
        curr_x +=1;
    }
    public void moveUp(){
        path.add(new Point(curr_x, curr_y, Point.Direction.UP));
        curr_y -=1;
    }
    public void moveDown(){
        path.add(new Point(curr_x, curr_y, Point.Direction.DOWN));
        curr_y += 1;
    }

    public abstract void searchForPath();

}
