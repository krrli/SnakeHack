package com.github.stairch.data;

public class Gamearea {

    private GameField[][] gameArea;

    public Gamearea(int width, int height){
        gameArea = new GameField[height][width];
    }

    /**
     * init every field of gamearea
     */
    public void initGameField(){

    }

    public GameField getField(int x, int y){
        return gameArea[y][x];
    }
}
