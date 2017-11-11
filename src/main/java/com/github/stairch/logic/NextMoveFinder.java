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

        return this.findMoveAccordingToField(pathToDestination.stream().findFirst().get());
    }

    private Move findMoveAccordingToField(GameField gameField) {
        return null;
    }
}
