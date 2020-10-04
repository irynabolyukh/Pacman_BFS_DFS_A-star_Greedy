package com.zetcode;

import java.util.ArrayList;
import java.util.List;

public abstract class PathSearcher {

    private short[][] screenData;

    List<Point> path = new ArrayList<Point>();

    int curr_x =0, curr_y=0;
    Point.Direction curr_dir;

    private boolean canMoveLeft(){
        return true;
    };

    private boolean canMoveRight(){
        return true;
    };

    private boolean canMoveUp(){
        return true;
    };

    private boolean canMoveDown(){
        return true;
    };

    private void moveLeft(){

    }

    private void moveRight(){

    }

    private void moveUp(){

    }

    private void moveDown(){

    }

}
