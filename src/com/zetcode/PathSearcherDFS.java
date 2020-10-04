package com.zetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class PathSearcherDFS extends PathSearcher{


    @Override
    public void searchForPath() {
        solveDFS();
    }
    public void solveDFS() {
        //insert the start
        path_stack.push(new MyPoint(0, 0, MyPoint.Direction.RIGHT));

        MyPoint crt;   //current node
        MyPoint next;  //next node
        while (!path_stack.empty()) {

            //get current position
            crt = path_stack.pop();
            if (isGoalReached(crt)) {
                break;
            }

            //to remember all visited
            path.add(crt);

            //put its neighbours in the queue
            next = crt.moveDown();  // go down from the current node
            if (isInMaze(next) && isClearDown(next)) {
                path_stack.push(next);
            }
            next = crt.moveRight();    //go right from the current node
            if (isInMaze(next) && isClearRight(next)) {
                path_stack.push(next);
            }
            next = crt.moveUp();    // go up
            if (isInMaze(next) && isClearUp(next)) {
                path_stack.push(next);
            }
            next = crt.moveLeft();    //go left from the current node
            if (isInMaze(next) && isClearLeft(next)) {
                path_stack.push(next);
            }
        }

    }

}
