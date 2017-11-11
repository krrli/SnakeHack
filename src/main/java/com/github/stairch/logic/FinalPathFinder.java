package com.github.stairch.logic;

import com.github.stairch.data.GameField;

import java.awt.*;
import java.util.ArrayList;

public class FinalPathFinder {
    public ArrayList<GameField> findFinalPath(GameField destination){
        ArrayList<GameField> finalPath = new ArrayList<>();

        this.addNextPreviousFieldUntilNull(finalPath, destination);

        return finalPath;
    }

    private void addNextPreviousFieldUntilNull(ArrayList<GameField> finalPath, GameField currentField) {
        finalPath.add(currentField);

        GameField previousGameField = currentField.getPreviousGameField();
        if(previousGameField == null){
            return;
        }

        this.addNextPreviousFieldUntilNull(finalPath, previousGameField);
    }
}
