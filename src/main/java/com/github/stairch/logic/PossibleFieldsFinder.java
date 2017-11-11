package com.github.stairch.logic;

import com.github.stairch.data.GameField;
import com.github.stairch.data.PlayerDestinationBundle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class PossibleFieldsFinder {
    public ArrayList<GameField> findPossibleFields(GameField[][] gameArea, PlayerDestinationBundle bundle) {
        ArrayList<GameField> closedList = new ArrayList<>();
        ArrayList<GameField> openList = new ArrayList<>();

        this.findNextField(closedList, openList, bundle, gameArea);

        return closedList;
    }

    private void findNextField(ArrayList<GameField> closedList, ArrayList<GameField> openList, PlayerDestinationBundle bundle, GameField[][] gameArea) {
        closedList.add(this.getGameFieldByPosition(gameArea, bundle.getCurrentPosition()));

        this.lookForWalkableFields(closedList, openList, bundle.getCurrentPosition(), gameArea);

        GameField bestOption = this.getFieldWithLowestScore(bundle.getDestination(), openList);
        openList.remove(bestOption);

        if(bestOptionIsCurrentPosition(bundle, bestOption)){
            closedList.add(this.getGameFieldByPosition(gameArea, bundle.getCurrentPosition()));
            return;
        }

        PlayerDestinationBundle newBundle = new PlayerDestinationBundle(
                new Point(bestOption.getX(), bestOption.getY()),
                bundle.getDestination());

        this.findNextField(closedList, openList, newBundle, gameArea);
    }

    private boolean bestOptionIsCurrentPosition(PlayerDestinationBundle bundle, GameField bestOption) {
        return bestOption.getX() == bundle.getDestination().getX() && bestOption.getY() == bundle.getDestination().getY();
    }

    private GameField getFieldWithLowestScore(Point destination, ArrayList<GameField> openList) {
        openList.sort(Comparator.comparingInt(o -> o.calculateF(destination)));
        return openList.stream().findFirst().get();
    }

    private void lookForWalkableFields(ArrayList<GameField> closedList, ArrayList<GameField> openList, Point player, GameField[][] gameArea) {
        GameField currentPosition = this.getGameFieldByPosition(gameArea ,player);

        if (player.getX() > 0)
        {
            GameField field = gameArea[((int) player.getY())][(int) (player.getX() - 1)];
            this.addFieldToListIfNotAlreadyChoosenAndFree(openList, field, currentPosition, closedList);
        }

        if (player.getX() < (gameArea[0].length - 1))
        {
            GameField field = gameArea[(int) player.getY()][(int) (player.getX() + 1)];
            this.addFieldToListIfNotAlreadyChoosenAndFree(openList, field, currentPosition, closedList);
        }

        if (player.getY() > 0)
        {
            GameField field = gameArea[(int) (player.getY() - 1)][(int) player.getX()];
            this.addFieldToListIfNotAlreadyChoosenAndFree(openList, field, currentPosition, closedList);
        }

        if (player.getY() < (gameArea.length - 1))
        {
            GameField field = gameArea[(int) (player.getY() + 1)][(int) player.getX()];
            this.addFieldToListIfNotAlreadyChoosenAndFree(openList, field, currentPosition, closedList);
        }

        /*if (openList.stream().findAny().isPresent() == false)
        {
            throw new OperationNotSupportedException("No way found");
        }*/
    }

    private void addFieldToListIfNotAlreadyChoosenAndFree(ArrayList<GameField> openList, GameField field, GameField currentPosition, ArrayList<GameField> closedList) {
        if (ListDoesNotContainField(field, closedList) && field.getIsBusy() == false)
        {
            /*if (field.movementCostAreLower(currentPosition))
            {
                field.setPreviousField(currentPosition);
            }*/

            if (ListDoesNotContainField(field, openList))
            {
                openList.add(field);
                field.setPreviousGameField(currentPosition);
            }
        }
    }

    private boolean ListDoesNotContainField(GameField field, ArrayList<GameField> list) {
        return list.contains(field) == false;
    }

    private GameField getGameFieldByPosition(GameField[][] gameArea, Point position){
        return gameArea[(int) position.getY()][(int) position.getX()];
    }
}
