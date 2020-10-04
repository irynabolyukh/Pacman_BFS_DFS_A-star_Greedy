package com.zetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class PathSearcher {

    public short[][] screenData;

    public List<MyPoint> path = new ArrayList<MyPoint>();
    public Stack<MyPoint> path_stack = new Stack<MyPoint>();

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
        path.add(new MyPoint(curr_x, curr_y, MyPoint.Direction.LEFT));
        curr_x -=1;
    }
    public void moveRight(){
        path.add(new MyPoint(curr_x, curr_y, MyPoint.Direction.RIGHT));
        curr_x +=1;
    }
    public void moveUp(){
        path.add(new MyPoint(curr_x, curr_y, MyPoint.Direction.UP));
        curr_y -=1;
    }
    public void moveDown(){
        path.add(new MyPoint(curr_x, curr_y, MyPoint.Direction.DOWN));
        curr_y += 1;
    }

    public abstract void searchForPath();

}
