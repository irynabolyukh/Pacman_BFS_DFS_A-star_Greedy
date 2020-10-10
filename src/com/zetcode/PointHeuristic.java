package com.zetcode;

public class PointHeuristic implements Comparable<PointHeuristic>{

    public MyPoint getP() {
        return p;
    }

    public Double getDistance() {
        return distance;
    }

    private MyPoint p;
    private Double distance;

    public PointHeuristic(MyPoint p, Double distance){
        this.p = p;
        this.distance = distance;
    }

    @Override
    public int compareTo(PointHeuristic o) {
        return this.getDistance().compareTo(o.getDistance());
    }

    public String toString() {
        return "point: " + getP() + ", distance: " + getDistance();
    }

}
