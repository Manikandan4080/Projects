package com.example.brickbreaker;

import java.awt.*;
import java.util.Random;

public class Map{
    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public Map(int row, int col){
        map = new int[row][col];
        for(int[] rows : map){
            for(int i = 0; i < col; i++) rows[i] = 3;
        }
        brickWidth = 640/col;
        brickHeight = 150/row;
    }
    public void draw(Graphics2D graphics){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] == 1){
                    graphics.setColor(Color.green);
                    graphics.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);

                    graphics.setStroke(new BasicStroke(2));
                    graphics.setColor(Color.BLACK);
                    graphics.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                }
                if(map[i][j] == 2){
                    graphics.setColor(Color.ORANGE);
                    graphics.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);

                    graphics.setStroke(new BasicStroke(2));
                    graphics.setColor(Color.BLACK);
                    graphics.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                }
                if(map[i][j] == 3){
                    graphics.setColor(Color.RED);
                    graphics.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);

                    graphics.setStroke(new BasicStroke(2));
                    graphics.setColor(Color.BLACK);
                    graphics.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                }
            }
        }
    }
    public void setBrickVal(int row, int col){
        map[row][col] = map[row][col] - 1;
    }
}
