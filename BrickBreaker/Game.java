package com.example.brickbreaker;

import javax.swing.JFrame;

public class Game {
    public static void main(String[] args){

        JFrame frame = new JFrame("Brick Breaker");
        frame.setVisible(true);
        frame.setBounds(150, 100, 800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePlay game = new GamePlay();
        frame.add(game);
    }
}