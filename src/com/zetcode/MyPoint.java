package com.zetcode;

class MyPoint {
    int x,y;
    Direction d;

    public MyPoint(int x, int y, Direction d){
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
