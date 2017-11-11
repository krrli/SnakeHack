package com.github.stairch.logic;

import com.github.stairch.data.GameField;
import com.github.stairch.data.PlayerDestinationBundle;
import com.github.stairch.types.Move;

import javax.naming.OperationNotSupportedException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class NextMoveFinder {
    private final PathFinder pathFinder;

    public NextMoveFinder(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }

    public Move findNextMove(GameField[][] gameArea, PlayerDestinationBundle bundle) {
        ArrayList<GameField> pathToDestination = null;
        try{
            pathToDestination = this.pathFinder.findPathToDestination(gameArea, bundle);

            GameField currentGameField = pathToDestination.get(0);
            GameField nextGameField = pathToDestination.get(1);

            if(nextGameField.getIsBusy() == false)
            {
                return this.findMoveAccordingToField(currentGameField, nextGameField);
            }

            return this.anyPossibleMove(bundle.getCurrentPosition(), gameArea);
        }
        catch (OperationNotSupportedException e) { // NoPathFound
            return this.anyPossibleMove(bundle.getCurrentPosition(), gameArea);
        }
    }

    private Move anyPossibleMove(Point currentPosition, GameField[][] gameArea) {
        ArrayList<Move> availableMoves = new ArrayList<>();
        GameField currentField = AreaHelper.GetGameFieldByPosition(gameArea, currentPosition);

        if(currentField.getY() > 0 && gameArea[currentField.getY()-1][currentField.getX()].getIsBusy() == false){
            availableMoves.add(Move.up);
        }
        if(currentField.getY() < gameArea.length - 1 && gameArea[currentField.getY()+ 1][currentField.getX()].getIsBusy() == false){
            availableMoves.add(Move.down);
        }
        if(currentField.getX() < gameArea[0].length - 1 && gameArea[currentField.getY()][currentField.getX()+ 1].getIsBusy() == false){
            availableMoves.add(Move.right);
        }
        if(currentField.getX() > 0 && gameArea[currentField.getY()][currentField.getX()- 1].getIsBusy() == false){
            availableMoves.add(Move.left);
        }

        return availableMoves.get(new Random().nextInt(availableMoves.size()));
    }

    private Move findMoveAccordingToField(GameField currentGameField, GameField nextGameField) {
        if(currentGameField.getX() == nextGameField.getX() && currentGameField.getY() - 1 == nextGameField.getY()){
            return Move.up;
        }
        if(currentGameField.getX() == nextGameField.getX() && currentGameField.getY() + 1 == nextGameField.getY()){
            return Move.down;
        }
        else if(currentGameField.getX() + 1 == nextGameField.getX() && currentGameField.getY() == nextGameField.getY()){
            return Move.right;
        }
        if(currentGameField.getX() - 1 == nextGameField.getX() && currentGameField.getY() == nextGameField.getY()){
            return Move.left;
        }
        return null;
    }
}
