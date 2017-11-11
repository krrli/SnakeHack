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
        for(int i=0; i<gameArea.length; i++) {
            for(int j=0; j<gameArea[i].length; j++) {
                gameArea[i][j] = new GameField(0,0,false);
                System.out.println("Values at arr["+i+"]["+j+"] is "+gameArea[i][j]);
            }
        }
    }

    /*
    public GameField[][] getGameArea(){
        return this.gameArea;
    }*/


    public GameField getField(int x, int y){
        return gameArea[y][x];
    }
}
