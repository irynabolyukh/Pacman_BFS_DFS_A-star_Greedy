package com.zetcode;

import java.util.Objects;

class MyPoint {

    private int x,y;
    private Direction d;

    public MyPoint(int x, int y, Direction d){
        this.x = x;
        this.y = y;
        this.d = d;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getD() {
        return d;
    }

    public MyPoint moveLeft(){
        return (new MyPoint(x-1, y, Direction.LEFT));
    }

    public MyPoint moveRight(){
        return (new MyPoint(x+1, y, Direction.RIGHT));
    }

    public MyPoint moveUp(){
        return (new MyPoint(x, y-1, Direction.UP));
    }

    public MyPoint moveDown(){
        return (new MyPoint(x, y+1, Direction.DOWN));
    }

    public String toString() {
        return "x: " + getX() + ", y: " + getY() +", dir: " + getD();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyPoint myPoint = (MyPoint) o;
        return x == myPoint.x &&
                y == myPoint.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public enum Direction{
        DOWN,
        RIGHT,
        UP,
        LEFT,
        STILL
    }
}
