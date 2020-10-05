package com.zetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public abstract class PathSearcher<i> {

    public short[][] screenData = {
            {3, 10, 10, 26,  2, 10, 10, 10, 10, 10, 10, 10,  2, 10,  6},
            {5,  0,  0,  0,  5,  0,  0,  0,  0,  0,  0,  0,  5,  0,  5},
            {5,  0,  0,  0,  5,  0,  3, 10,  6,  0,  3, 10, 12,  0,  5},
            {5,  0,  0,  0,  5,  0,  5,  0,  5,  0,  5,  0,  0,  0,  5},
            {1, 10, 10, 10, 12,  0,  5,  0,  5,  0,  5,  0,  0,  0,  5},
            {5,  0,  0,  0,  0,  0,  5,  0,  5,  0,  5,  0,  0,  0,  5},
            {9,  2, 10,  2, 10, 10, 12,  0,  9, 10,  8,  6,  0,  0,  5},
            {1,  5,  0,  5,  0,  0,  0,  0,  0,  0,  0,  5,  0,  0,  5},
            {1,  5,  0,  1, 10, 10,  6,  0,  3, 10, 10,  8,  6,  0,  5},
            {1,  5,  0,  5,  0,  0,  5,  0,  5,  0,  0,  0,  5,  0,  5},
            {1,  5,  0,  5,  0,  0,  5,  0,  5,  0,  0,  0,  5,  0,  5},
            {1,  5,  0,  9, 10, 10,  8, 10, 12,  0,  0,  0,  5,  0,  5},
            {1,  5,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  9, 10,  4},
            {1,  9, 10, 10, 10, 10, 10, 10, 10, 10,  6,  0,  0,  0,  5},
            {9,  8,  8,  8,  8,  8,  8,  8,  8,  8,  9, 10, 10, 10, 12}
    };

    public List<MyPoint> path = new ArrayList<MyPoint>();
    public Stack<MyPoint> path_stack = new Stack<MyPoint>();
    public LinkedList<MyPoint> path_queue = new LinkedList<MyPoint>();

    public int[][] isVisited = new int[15][15];

    public boolean isGoalReached(int i, int j) {
        short ch;
        ch = screenData[j][i];
        return ((ch & 16) != 0);
    }

    public boolean isGoalReached(MyPoint pos) {
        return isGoalReached(pos.getX(), pos.getY());
    }

    public boolean isClearLeft(int i, int j) {
        assert (isInMaze(i, j));
        short ch;
        ch = screenData[i][j];
        return (((ch & 4) == 0)&&(ch != 0));
    }

    public boolean isClearLeft(MyPoint pos) {
        return isClearLeft(pos.getX(), pos.getY());
    }

    public boolean isClearRight(int i, int j) {
        assert (isInMaze(i, j));
        short ch;
        ch = screenData[j][i];
        return (((ch & 1) == 0)&&(ch != 0));
    }

    public boolean isClearRight(MyPoint pos) {
        return isClearRight(pos.getX(), pos.getY());
    }

    public boolean isClearUp(int i, int j) {
        assert (isInMaze(i, j));
        short ch;
        ch = screenData[j][i];
        return (((ch & 8) == 0)&&(ch != 0));
    }

    public boolean isClearUp(MyPoint pos) {
        return isClearUp(pos.getX(), pos.getY());
    }

    public boolean isClearDown(int i, int j) {
        assert (isInMaze(i, j));
        short ch;
        ch = screenData[j][i];
        return (((ch & 2) == 0)&&(ch != 0));
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

    public void markVisited(MyPoint pos) {
        markVisited(pos.getX(),pos.getY());
    }

    public void markVisited(int x, int y) {
        isVisited[y][x] = 1;
    }

    public boolean isVisited(MyPoint pos){
        return isVisited(pos.getX(),pos.getY());
    }

    public boolean isVisited(int x, int y) {
        return (isVisited[y][x] == 1);
    }

    public abstract void searchForPath();

}
