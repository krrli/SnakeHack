package com.github.stairch.logic;

import com.github.stairch.data.GameField;

import java.awt.*;

public class AreaHelper {
    public static GameField GetGameFieldByPosition(GameField[][] gameArea, Point position){
        return gameArea[(int) position.getY()][(int) position.getX()];
    }
}
