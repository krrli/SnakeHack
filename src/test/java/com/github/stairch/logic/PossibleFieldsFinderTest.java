package com.github.stairch.logic;

import com.github.stairch.data.GameField;
import com.github.stairch.data.PlayerDestinationBundle;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PossibleFieldsFinderTest {

    @Test
    public void Test_FindPossibleFields() throws OperationNotSupportedException {
        // arrange
        PossibleFieldsFinder testee = new PossibleFieldsFinder();
        GameField[][] gameArea = new GameField[][]{
                { new GameField(0,0, false), new GameField(1,0, true), new GameField(2,0, false) },
                { new GameField(0,1, false), new GameField(1,1, false), new GameField(2,1, false)},
                { new GameField(0,2, false), new GameField(1,2, false), new GameField(2,2, false)} ,
        };
        PlayerDestinationBundle bundle = new PlayerDestinationBundle(
                new Point(0, 0),
                new Point(2, 2));

        // act
        ArrayList<GameField> closedList = testee.findPossibleFields(gameArea, bundle);

        // assert
        assertEquals(5, closedList.size());
    }
}
