package com.zetcode;

public class PointHeuristic implements Comparable<PointHeuristic>{

    public MyPoint getP() {
        return p;
    }

    public Double getValue() {
        return value;
    }

    private MyPoint p;
    private Double value;

    public PointHeuristic(MyPoint p, Double value){
        this.p = p;
        this.value = value;
    }

    @Override
    public int compareTo(PointHeuristic o) {
        return this.getValue().compareTo(o.getValue());
    }

    public String toString() {
        return "point: " + getP() + ", value: " + getValue();
    }

}