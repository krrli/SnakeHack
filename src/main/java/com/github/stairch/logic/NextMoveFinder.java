package com.github.stairch.logic;

import com.github.stairch.data.GameField;
import com.github.stairch.data.PlayerDestinationBundle;
import com.github.stairch.types.Move;

import java.util.ArrayList;

public class NextMoveFinder {
    private final PathFinder pathFinder;

    public NextMoveFinder(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }

    public Move findNextMove(GameField[][] gameArea, PlayerDestinationBundle bundle) {
        ArrayList<GameField> pathToDestination = this.pathFinder.findPathToDestination(gameArea, bundle);

        GameField currentGameField = pathToDestination.get(0);
        GameField nextGameField = pathToDestination.get(1);
        return this.findMoveAccordingToField(currentGameField, nextGameField);
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
