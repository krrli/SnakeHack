package com.github.stairch.logic;

import com.github.stairch.data.GameField;
import com.github.stairch.data.PlayerDestinationBundle;
import com.github.stairch.types.Move;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PathFinderTest {
    @Test
    public void TestFindNextMove(){
        // arrange
        NextMoveFinder testee = new NextMoveFinder(new PathFinder());
        GameField[][] gameArea = new GameField[][]{
            { new GameField(0,0, false), new GameField(1,0, true), new GameField(2,0, false) },
            { new GameField(0,1, false), new GameField(1,1, false), new GameField(2,2, false)},
            { new GameField(0,2, false), new GameField(1,2, false), new GameField(2,2, false)} ,
        };
        PlayerDestinationBundle bundle = new PlayerDestinationBundle(
                new Point(0, 0),
                new Point(3, 3));

        // act
        Move nextMove = testee.findNextMove(gameArea, bundle);

        // assert
        assertEquals(Move.down, nextMove);

    }
}
