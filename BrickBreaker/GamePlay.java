package com.example.brickbreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 24;
    private Timer timer;
    private int delay = 8;
    private int sliderX = 350;
    private int ballX = 300;
    private int ballY = 350;
    private int ballDictX = -1;
    private int ballDictY = -5;
    private Map map;

    public GamePlay(){
        map = new Map(3,8);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }
    public void paint(Graphics graphics){
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, 800, 600);

        map.draw((Graphics2D) graphics);

        graphics.setColor(Color.cyan);
        graphics.fillRect(0, 0,3,  600);
        graphics.fillRect(0, 0,800,  3);
        graphics.fillRect(781, 0,3,  600);

        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Cambria", Font.BOLD, 25));
        graphics.drawString(""+score, 700, 30);

        //slider
        graphics.setColor(Color.GREEN);
        graphics.fillRect(sliderX, 550, 100, 8);

        //ball
        graphics.setColor(Color.lightGray);
        graphics.fillOval(ballX, ballY, 20,20);

        if(ballY > 570){
            play = false;
            ballDictX = 0;
            ballDictY = 0;

            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("Cambria", Font.BOLD, 25));
            graphics.drawString("        Game Over ", 190, 300);

            graphics.setFont(new Font("Cambria", Font.BOLD, 25));
            graphics.drawString("        Your Score : "+score, 190, 340);

            graphics.setFont(new Font("Cambria", Font.BOLD, 25));
            graphics.drawString("Press Enter to Restart ", 190, 380);
        }

        if(totalBricks == 0){
            play = false;
            ballDictX = 0;
            ballDictY = 0;
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("Cambria", Font.BOLD, 25));
            graphics.drawString("                  Congratulation ", 190, 300);

            graphics.setFont(new Font("Cambria", Font.BOLD, 25));
            graphics.drawString("You Have breaked all the Bricks :"+score, 190, 340);

            graphics.setFont(new Font("Cambria", Font.BOLD, 25));
            graphics.drawString("          Press Enter to Restart ", 190, 380);
        }
        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(play){
            if(new Rectangle(ballX, ballY, 20,20).intersects(new Rectangle(sliderX, 550, 100, 2))){
                ballDictY = -ballDictY;
            }

            A:
            for(int i = 0; i < map.map.length; i++){
                for(int j = 0; j < map.map[0].length; j++){
                    if(map.map[i][j] > 0){
                        int brickX = j*map.brickWidth+80;
                        int brickY = i*map.brickHeight+50;

                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballX, ballY, 20,20);
                        Rectangle bricRect = rect;

                        if(ballRect.intersects(bricRect)){
                            if(map.map[i][j] == 3) score+=5;
                            if(map.map[i][j] == 2) score+=10;
                            if(map.map[i][j] == 1) score+=15;

                            if(map.map[i][j] == 1) totalBricks--;
                            map.setBrickVal(i, j);

                            if(ballX+19 <= bricRect.x || ballX+1 >= bricRect.x+brickWidth) ballDictX = -ballDictX;
                            else ballDictY = -ballDictY;
                            break A;
                        }
                    }
                }
            }

            ballX += ballDictX;
            ballY += ballDictY;

            if(ballX < 0 || ballX > 770) ballDictX = -ballDictX;
            if(ballY < 0) ballDictY = -ballDictY;

            repaint();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_RIGHT){
            if(sliderX >= 690) sliderX = 690;
            else moveRight();
        }
        if(key == KeyEvent.VK_LEFT){
            if(sliderX < 10) sliderX = 10;
            else moveLeft();
        }

        if(key == KeyEvent.VK_ENTER){
            if(!play){
                sliderX = 350;
                ballX = 300;
                ballY = 350;
                ballDictX = -1;
                ballDictY = -5;
                score = 0;
                totalBricks = 24;
                map = new Map(3,8);

                repaint();
            }
        }

    }

    private void moveLeft() {
        play = true;
        sliderX -= 20;
    }

    private void moveRight() {
        play = true;
        sliderX += 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
