package com.github.stairch.data;

import java.awt.*;

public class GameField {
    private final int MOVEMENT_COST = 1;
    private final int x;
    private final  int y;
    private boolean isBusy;
    private GameField previousGameField;
    private int g;

    public GameField(int x, int y, boolean isBusy){
        this.x = x;
        this.y = y;
        this.isBusy = isBusy;
    }

    public int getY() {
        return y;
    }


    public int getX() {
        return x;
    }

    /*public boolean movementCostAreLower(GameField possiblePreviousField) {
        return possiblePreviousField.getG() + this.MOVEMENT_COST < ;
    }*/

    public GameField getPreviousGameField() {
        return previousGameField;
    }

    public void setPreviousGameField(GameField previousGameField) {
        this.previousGameField = previousGameField;
    }

    public int calculateF(Point destination) {
        int h = this.getEstimatedMovementToDestination(destination);
        int g = this.getPreviousGameField().getG() + this.MOVEMENT_COST;
        return h + g;
    }

    private int getEstimatedMovementToDestination(Point destination) {
        int xDiff = (int) Math.abs(this.getX() - destination.getX());
        int yDiff = (int) Math.abs(this.getY() - destination.getY());

        return xDiff + yDiff;
    }

    public int getG() {
        return g;
    }

    public boolean getIsBusy() {
        return isBusy;
    }


    public void setIsBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }
}
