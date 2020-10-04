package com.zetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public abstract class PathSearcher {

    public short[][] screenData;

    public List<MyPoint> path = new ArrayList<MyPoint>();
    public Stack<MyPoint> path_stack = new Stack<MyPoint>();
    public LinkedList<MyPoint> path_queue = new LinkedList<MyPoint>();

    public boolean isGoalReached(int i, int j) {
        return ((screenData[i][j] & 16) != 0);
    }

    public boolean isGoalReached(MyPoint pos) {
        return isGoalReached(pos.getX(), pos.getY());
    }

    public boolean isClearLeft(int i, int j) {
        assert (isInMaze(i, j));
        short ch;
        ch = screenData[i][j];
        return ((ch & 4) == 0);
    }

    public boolean isClearLeft(MyPoint pos) {
        return isClearLeft(pos.getX(), pos.getY());
    }

    public boolean isClearRight(int i, int j) {
        assert (isInMaze(i, j));
        short ch;
        ch = screenData[i][j];
        return ((ch & 1) == 0);
    }

    public boolean isClearRight(MyPoint pos) {
        return isClearRight(pos.getX(), pos.getY());
    }

    public boolean isClearUp(int i, int j) {
        assert (isInMaze(i, j));
        short ch;
        ch = screenData[i][j];
        return ((ch & 8) == 0);
    }

    public boolean isClearUp(MyPoint pos) {
        return isClearUp(pos.getX(), pos.getY());
    }

    public boolean isClearDown(int i, int j) {
        assert (isInMaze(i, j));
        short ch;
        ch = screenData[i][j];
        return ((ch & 2) == 0);
    }

    public boolean isClearDown(MyPoint pos) {
        return isClearDown(pos.getX(), pos.getY());
    }

    public boolean isInMaze(int i, int j) {

        if (i >= 0 && i < 15 && j >= 0 && j < 15) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInMaze(MyPoint pos) {
        return isInMaze(pos.getX(), pos.getY());
    }


    public abstract void searchForPath();

}
