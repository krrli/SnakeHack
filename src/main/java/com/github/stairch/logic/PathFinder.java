package com.github.stairch.logic;

import com.github.stairch.data.GameField;
import com.github.stairch.data.PlayerDestinationBundle;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;

public class PathFinder {
    private final FinalPathFinder finalFieldsFinder;
    private final PossibleFieldsFinder possibleFieldsFinder;

    public PathFinder() {
        possibleFieldsFinder = new PossibleFieldsFinder();
        finalFieldsFinder = new FinalPathFinder();
    }

    public ArrayList<GameField> findPathToDestination(GameField[][] gameArea, PlayerDestinationBundle bundle) throws OperationNotSupportedException {
        this.possibleFieldsFinder.findPossibleFields(gameArea, bundle);
        ArrayList<GameField> finalPath = this.finalFieldsFinder.findFinalPath(
                AreaHelper.GetGameFieldByPosition(gameArea, bundle.getDestination()));
        Collections.reverse(finalPath); //so that start is at the top and end at the bottom
        return finalPath;
    }
}
