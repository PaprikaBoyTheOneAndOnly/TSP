package com.bbw.ch;

public class Node {
    private int x;
    private int y;
    private String name;

    public Node(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double calcWay(Node node) {
        int x = node.getX() > this.getX() ? node.getX() - this.getX() : this.getX() - node.getX();
        int y = node.getY() > this.getY() ? node.getY() - this.getY() : this.getY() - node.getY();

        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
