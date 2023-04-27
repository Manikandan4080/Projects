package com.example.estore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;

    Customer customer = null;

    @FXML
    private TextField loginEmailFeild;
    @FXML
    private PasswordField loginPasswordFeild;

    @FXML
    protected void loginButtonAction(ActionEvent event) throws IOException {
        if(loginEmailFeild.getText().isBlank() || loginPasswordFeild.getText().isBlank()){
            showDialogue.show("Login Status", "Enter Credentials");
        }
        else{
            customer = Login.customerLogin(loginEmailFeild.getText(), loginPasswordFeild.getText());
            if(customer != null){
                showDialogue.show("Login Status", "Welcome "+customer.name);
                switchToHome(event);
            }
            else{
                showDialogue.show("Login Status", "Check Credentials");
            }
        }
    }

    public void newUserButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void switchToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        root = loader.load();

        Home home = loader.getController();
        home.setCustomer(customer);
        home.setWelcomeText();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setX(150);
        stage.setY(50);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private TextField newName;
    @FXML
    private TextField newEmail;
    @FXML
    private TextField newMobile;
    @FXML
    private TextField newPass;
    @FXML
    private TextField conPass;

    @FXML
    protected void createAccountButton(ActionEvent event) throws IOException {
        String name = newName.getText();
        String email = newEmail.getText();
        String mobile = newMobile.getText();
        String password = newPass.getText();
        String confirmPassword = conPass.getText();

        if(name.isBlank() || email.isBlank() || mobile.isBlank() || confirmPassword.isBlank() || password.isBlank()){
            showDialogue.show("Account Status", "Enter All Credentials in Every Feild!!");
        }
        else{
            if (!password.equals(confirmPassword)) {
                showDialogue.show("Account status", "Enter Same Password");
            }
            else {
                boolean status = SignUp.createAccount(name, email, mobile, password);
                if (status) {
                    showDialogue.show("Account Status ", "Account successfully created!!  Welcome");

                    root = FXMLLoader.load(getClass().getResource("login.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    showDialogue.show("Account status", "Failed !! Email already Exists");
                }
            }
        }
    }
    public void alreadyHaveAccountButtonAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logOut(){
        customer = null;
    }

}