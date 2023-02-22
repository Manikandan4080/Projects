package com.example.ecom;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class eCommerce extends Application {

    private int width = 500, height = 500, headerLine = 50;
    Pane bodyPane;
    ProductsList productsList = new ProductsList();

    Button signInButton = new Button("Sign In");
    Button signUpButton = new Button("Sign Up");
    Label welcome = new Label();

    Customers currCustomer;
    private GridPane headerBar(){
        GridPane head = new GridPane();

        TextField searchBar = new TextField();
        Button searchButton = new Button("Search");

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().addAll(productsList.getAllProducts());
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().addAll(login());
            }
        });

        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().addAll(signUp());
            }
        });
        head.add(searchBar, 0, 0);
        head.add(searchButton, 1, 0);
        head.add(signInButton,2, 0);
        head.add(signUpButton, 3,0);
        head.add(welcome,2,1);

        head.setTranslateX(50);
        head.setTranslateY(20);
        head.setHgap(10);

        return head;
    }

    private GridPane login(){
        Label User = new Label("User Email : ");
        Label Password = new Label("Password : ");

        TextField userFeild = new TextField();
        userFeild.setPromptText("Enter User email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        Button loginButton = new Button("Login");
        Label message = new Label();

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String user = userFeild.getText();
                String pass = passwordField.getText();
                currCustomer = Login.customerLogin(user, pass);

                if(currCustomer != null){
                    String str = "Welcome "+currCustomer.getName();
                    showDialogue.show("Login Successful !!", str);
                }

                else {
                    showDialogue.show("Login - Failed!!", "Check email / Password");
                }
            }
        });

        Button signUpButton = new Button("New User/ Sign Up");
        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().addAll(signUp());

            }
        });

        GridPane loginPane = new GridPane();
        loginPane.setTranslateY(100);
        loginPane.setTranslateX(100);
        loginPane.setHgap(10);
        loginPane.setVgap(10);

        loginPane.add(User, 0, 0);
        loginPane.add(userFeild, 1,0);
        loginPane.add(Password, 0, 1);
        loginPane.add(passwordField, 1,1);
        loginPane.add(loginButton, 0,2);
        loginPane.add(message,1,3);
        loginPane.add(signUpButton, 1,2);

        return loginPane;

    }

    private GridPane signUp(){
        Label email = new Label("Email");
        Label newUser = new Label("User name");
        Label number = new Label("Moblie number");
        Label password = new Label("Password");
        Label confirmPassword = new Label("Confirm Password");

        TextField emailFeild = new TextField();
        emailFeild.setPromptText("Enter email");

        TextField userFeild = new TextField();
        userFeild.setPromptText("Enter name");

        TextField numberFeild = new TextField();
        numberFeild.setPromptText("Enter Mobile Number");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        PasswordField confirm = new PasswordField();
        confirm.setPromptText("Confirm password");

        Button createAccount = new Button("Create Account");
        Label message = new Label();

        GridPane signupPane = new GridPane();

        signInButton.setText("Log In");
        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                bodyPane.getChildren().clear();
                bodyPane.getChildren().addAll(login());
            }
        });

        createAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String mail = emailFeild.getText();
                String name = userFeild.getText();
                String num = numberFeild.getText();
                String pass = passwordField.getText();
                String  confirmPass = confirm.getText();

                int status = SignUp.createAccount(mail, name, num, pass, confirmPass);
                if(status == 1) {
                    showDialogue.show("Error", "Fill Same password");
                }
                else if (status == 2) {
                    showDialogue.show("Error","Email is already Signed In");
                }
                else  showDialogue.show("Successful!!","Account Created!! Click Login");
            }
        });

        signupPane.add(email, 0, 0);
        signupPane.add(emailFeild, 1,0);

        signupPane.add(newUser, 0, 1);
        signupPane.add(userFeild, 1,1);

        signupPane.add(number, 0, 2);
        signupPane.add(numberFeild, 1,2);

        signupPane.add(password, 0, 3);
        signupPane.add(passwordField, 1,3);

        signupPane.add(confirmPassword, 0, 4);
        signupPane.add(confirm, 1,4);

        signupPane.add(signInButton, 0, 5);
        signupPane.add(createAccount, 1,5);

        signupPane.add(message, 1,6);

        signupPane.setTranslateY(100);
        signupPane.setTranslateX(100);
        signupPane.setHgap(10);
        signupPane.setVgap(10);


        return signupPane;
    }

    private  GridPane footerBar(){
        Button buyButton = new Button("Buy Now!");
        GridPane footer = new GridPane();
        footer.add(buyButton, 0, 0);

        buyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Products product = productsList.getSelected();

                boolean orderStat = false;
                if(product != null && currCustomer != null){
                    Order order = new Order();
                    order.placeOrder(currCustomer, product);
                    orderStat = true;
                }

                if(orderStat) {
                    showDialogue.show("Order Status","Order Successful!! ");
                }
                else{
                    //showDialogue("Order failed!! ");
                }
            }
        });

        footer.setTranslateY(headerLine+height);
        footer.setHgap(20);
        return footer;
    }
    private Pane createContent(){
        Pane root = new Pane();
        //root.setBackground(new Background(new BackgroundFill(Color.rgb(0,20,20,), null, null));

        bodyPane = new Pane();
        bodyPane.setPrefSize(width, height);
        bodyPane.setTranslateY(headerLine);
        bodyPane.setTranslateX(10);

        bodyPane.getChildren().addAll(login());

        root.setPrefSize( width, height+2*headerLine);
        root.getChildren().addAll(headerBar(),
                //login(), productsList.getAllProducts()
                bodyPane, footerBar());

        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(eCommerce.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Ecommerce");
        scene.setFill(Color.AQUA);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}