package com.bbw.ch;

public class Node {
    private double x;
    private double y;
    private String name;

    public Node(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public double calcWay(Node node) {
        double x = node.getX() > this.getX() ? node.getX() - this.getX() : this.getX() - node.getX();
        double y = node.getY() > this.getY() ? node.getY() - this.getY() : this.getY() - node.getY();

        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
