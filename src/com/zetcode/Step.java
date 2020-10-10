package com.zetcode;

public class Step {
    private MyPoint from;
    private int g;
    private double weight;

    public Step(MyPoint from, int g, double weight) {
        this.from = from;
        this.g = g;
        this.weight = weight;
    }

    public MyPoint getFrom() {
        return from;
    }

    public void setFrom(MyPoint from) {
        this.from = from;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Step{" +
                "from=" + from +
                ", g=" + g +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Step step = (Step) obj;
        return from.equals(step.from)  &&
                g == step.g &&
                weight == step.weight;
    }
}
