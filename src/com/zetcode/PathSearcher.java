package com.zetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public abstract class PathSearcher {

    public short[][] screenData = new short[15][15];
    public List<MyPoint> path = new ArrayList<MyPoint>();
    public Stack<MyPoint> path_stack = new Stack<MyPoint>();
    public LinkedList<MyPoint> path_queue = new LinkedList<MyPoint>();

    public int[][] isVisited = new int[15][15];

    long startTime, stopTime, duration;
    double timeInMs;

    int totalSteps;

    long totalUsedMemory, totalUsedMemoryInBytes;

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
        ch = screenData[j][i];
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
