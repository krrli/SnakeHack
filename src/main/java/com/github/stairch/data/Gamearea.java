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
        for(int y=0; y<gameArea.length; y++) {
            for(int x=0; x<gameArea[y].length; x++) {
                gameArea[y][x] = new GameField(y,x,false);
                //System.out.println("Values at arr["+y+"]["+x+"] is "+gameArea[y][x]);
            }
        }
    }

    public GameField getField(int x, int y){
        return gameArea[y][x];
    }

    public GameField[][] getGameArea(){
        return this.gameArea;
    }
}
