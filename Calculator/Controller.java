package com.example.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.awt.event.*;

public class Controller{
    @FXML
    private Button DelButton;
    @FXML
    private Button button0;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Button closeBracket;
    @FXML
    private Button clrButton;
    @FXML
    private Button divButton;
    @FXML
    private Button equalButton;
    @FXML
    private Button minusButton;
    @FXML
    private Button mulButton;
    @FXML
    private Button openBracket;
    @FXML
    private Button plusButton;
    @FXML
    private Button powButt;
    @FXML
    private TextArea textArea;

    Button[] numbers = new Button[]{
            button0,button1,button2,button3,button4,
            button5,button6,button7,button8,button9
    };

    public void zero(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+0);
    }
    public void one(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+1);
    }
    public void two(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+2);
    }
    public void three(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+3);
    }
    public void four(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+4);
    }
    public void five(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+5);
    }
    public void six(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+6);
    }
    public void seven(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+7);
    }
    public void eight(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+8);
    }
    public void nine(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+9);
    }

    public void plusButton(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+"+");
    }
    public void minusButton(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+"-");
    }
    public void divButton(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+"/");
    }
    public void multiButton(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+"x");
    }

    public void openButton(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+"(");
    }
    public void closeButton(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+")");
    }

    public void powButtonAction(ActionEvent e){
        String str = textArea.getText();
        textArea.setText(str+"^");
    }

    public void equalButton(ActionEvent e){
        String ans = Calculations.solve(textArea.getText());
        textArea.clear();
        textArea.setText(ans);
    }

    public void clrButtonAction(ActionEvent e){
        textArea.clear();;
    }
    public void delButtonAction(ActionEvent e){
        String str = textArea.getText();
        int len = str.length();
        textArea.setText(str.substring(0, len-1));
    }

    private boolean check(String str, int i){
        if(str.charAt(i) != '+') return true;
        if(str.charAt(i) != '-') return true;
        if(str.charAt(i) != 'X') return true;
        if(str.charAt(i) != '/') return true;
        if(str.charAt(i) != '(') return true;
        if(str.charAt(i) != ')') return true;
        return false;
    }
}