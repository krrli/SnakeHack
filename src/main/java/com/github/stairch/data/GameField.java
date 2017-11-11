package com.github.stairch.data;

public class GameField {
    private final int x;
    private final  int y;
    private int h;
    private int g;
    private int f;

    public GameField(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void calculateF(){
        this.f = this.g+ this.h;
    }

    public int getY() {
        return y;
    }


    public int getX() {
        return x;
    }

}