package com.zetcode;

class MyPoint {
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getD() {
        return d;
    }

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
    public MyPoint moveLeft(){
        return (new MyPoint(x-1, y, MyPoint.Direction.LEFT));
    }
    public MyPoint moveRight(){
        return (new MyPoint(x+1, y, MyPoint.Direction.RIGHT));
    }
    public MyPoint moveUp(){
        return (new MyPoint(x, y-1, MyPoint.Direction.UP));
    }
    public MyPoint moveDown(){
        return (new MyPoint(x, y+1, MyPoint.Direction.DOWN));
    }

    public String toString() {
        return "x: " + getX() + ", y: " + getY() +", dir: " + getD();
    }
}
