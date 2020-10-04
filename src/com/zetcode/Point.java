package com.zetcode;

public class Point {
    int x,y;
    Direction d;

    public Point(int x, int y, Direction d){
        this.x = x;
        this.y = y;
        this.d = d;
    }

    public enum Direction{
        DOWN,
        RIGHT,
        UP,
        LEFT,
    }
}
