package com.example.ecommerce;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;

public class Ecommerce extends Application {
    Pane root,bodyPane;
    private int width = 500, headerLine = 50, height = 500;
    Button signInButton = new Button("Sign In");
    Button signUpButton = new Button("Sign Up");
    Label welcome = new Label();

    ProductList productList = new ProductList();
    ObservableList<Product> cartList = FXCollections.observableArrayList();
    Customers curCustomer;
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Parent root2 = FXMLLoader.load(getClass().getResource("Main.fxml"));
        //stage.setScene(new Scene(root2));
        Scene scene = new Scene(createContent());
        stage.setTitle("Ecommerce");
        stage.setScene(scene);
        stage.show();
    }
    private Pane createContent(){
        root = new Pane();
        bodyPane = new Pane();

        bodyPane.setPrefSize(width, height);
        bodyPane.setTranslateX(50);
        bodyPane.setTranslateY(100);

        bodyPane.getChildren().add(logIn());
        root.setPrefSize(width, height+2*headerLine);
        root.getChildren().addAll(headerBar(), bodyPane);

        return root;
    }

    private GridPane headerBar(){
        GridPane head = new GridPane();

        Label welcome = new Label("Welcome to My Cart!!");
        Button in = new Button("SignIn");
        Button up = new Button("Sign Up");

        in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(logIn());
            }
        });

        up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add((signUp()));
            }
        });

        head.add(welcome, 0, 0);
        head.add(in, 1,0);
        head.add(up, 2,0);

        head.setTranslateX(50);
        head.setTranslateY(20);
        head.setHgap(20);
        return head;
    }

    private GridPane logIn(){
        GridPane loginPane = new GridPane();

        Label email = new Label("E-Mail : ");
        Label password = new Label("Password : ");

        TextField userFeild = new TextField();
        userFeild.setPromptText("Enter Your Mail Here");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Your Password Here");

        Button loginButton = new Button("Login");
        Button signUp = new Button("New User");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String mail = userFeild.getText();
                String pass = passwordField.getText();

                curCustomer = Login.customerLogin(mail, pass);
                if(curCustomer != null){
                    String str = "Welcome "+curCustomer.getName();
                    showDialogue.show("Login Sucessful !! ", str);
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().addAll(productList.getAllProducts());

                    root.getChildren().clear();
                    root.getChildren().addAll(logOutBar(), bodyPane, footerBar());
                }

                else{
                    showDialogue.show("Login Failed !! ", "Check email / Password");
                }
            }
        });

        signUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().addAll(signUp());
            }
        });

        loginPane.setTranslateX(100);
        loginPane.setTranslateY(100);
        loginPane.setHgap(10);
        loginPane.setVgap(10);

        loginPane.add(email, 0 ,0);
        loginPane.add(userFeild, 1, 0);

        loginPane.add(password, 0,1);
        loginPane.add(passwordField, 1 ,1);

        loginPane.add(loginButton, 0,2);
        loginPane.add(signUp, 1,2);

        return loginPane;
    }

    private  GridPane logOutBar(){
        TextField search = new TextField();
        Button searchButton = new Button("Search");
        Button logOutbutton = new Button("Log Out");
        Button cart = new Button("Cart");

        GridPane logoutPane = new GridPane();
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().addAll(productList.getAllProducts());
            }
        });
        logOutbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                curCustomer = null;
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(logIn());

                root.getChildren().clear();
                root.getChildren().addAll(headerBar(), bodyPane);
                showDialogue.show("Logout!!", "Log Out Successfull!!");
            }
        });
        cart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productList.productInCart(cartList));
            }
        });

        logoutPane.add(search, 0, 0);
        logoutPane.add(searchButton, 1, 0);
        logoutPane.add(logOutbutton, 2, 0);
        logoutPane.add(cart, 3,0);

        logoutPane.setHgap(10);
        logoutPane.setTranslateX(50);
        logoutPane.setTranslateY(10);


        return logoutPane;
    }

    private GridPane signUp(){
        GridPane body = new GridPane();

        Label name, email, number, password, confirm;
        name = new Label("User Name : ");
        email = new Label("User mail : ");
        number = new Label("Mobile Number : ");
        password = new Label("Password : ");
        confirm = new Label("Confirm Password : ");

        TextField nameFeild = new TextField();
        nameFeild.setPromptText("Enter Your Name Here");
        TextField emailFeild = new TextField();
        emailFeild.setPromptText("Enter Your Email Here");
        TextField numberFeild = new TextField();
        numberFeild.setPromptText("Enter user Name Here");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Your Password Here");
        PasswordField confirmFeild  = new PasswordField();
        confirmFeild.setPromptText("Re-Enter Your Password Here");

        Button loginButton = new Button("Login");
        Button createAcc = new Button("Create Your Account");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                body.getChildren().addAll(logIn());
            }
        });

        createAcc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = nameFeild.getText();
                String mail = emailFeild.getText();
                String mob = numberFeild.getText();
                String pass = passwordField.getText();
                String  conPass = confirmFeild.getText();

                int status = SignUp.createAccount(name, mail, mob, pass, conPass);
                if(status == 1){
                    showDialogue.show("Error", "Fill Same Password");
                }
                else if(status == 2){
                    showDialogue.show("Error", "Email already exist");
                }
                else {
                    showDialogue.show("Successfull !!", "Account Created Successfully !! Go to Login Page");
                }
            }
        });

        body.add(name, 0, 0);
        body.add(nameFeild, 1,0);
        body.add(email, 0, 1);
        body.add(emailFeild, 1,1);
        body.add(number, 0,2);
        body.add(numberFeild, 1,2);
        body.add(password, 0,3);
        body.add(passwordField, 1,3);
        body.add(confirm, 0,4);
        body.add(confirmFeild, 1,4);

        body.add(loginButton, 0,5);
        body.add(createAcc, 1,5);

        body.setHgap(10);
        body.setVgap(10);
        body.setTranslateX(100);
        body.setTranslateY(100);

        return body;
    }
    private GridPane footerBar(){
        GridPane footer = new GridPane();

        Button buy = new Button("Buy Now !!");
        Button addToCart = new Button("Add To cart");
        Button place = new Button("Place Order");
        Button myOrders = new Button("My Orders");

        buy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelected();

                boolean orderStat = false;
                if(product != null && curCustomer != null){
                    Order order = new Order();
                    order.placeOrder(curCustomer, product);
                    orderStat = true;
                }
                if(orderStat) showDialogue.show("Order Sucessful", "Nice view more Products");

                else showDialogue.show("Something went Wrong" , "Try Again");
            }
        });

        addToCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelected();
                addItemsToCart(product);
            }
        });

        place.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Order order = new Order();
                int items = order.cartOrder(cartList, curCustomer);
                showDialogue.show("Sucessfull", items+" Items Ordered");
            }
        });

        myOrders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Order o = new Order();
                bodyPane.getChildren().clear();
                bodyPane.getChildren().addAll(o.myOrder(curCustomer));
            }
        });

        footer.add(buy, 0, 0);
        footer.add(addToCart, 1,0);
        footer.add(place, 2,0);
        //footer.add(myOrders, 3,0);

        footer.setTranslateY(headerLine+height);
        footer.setTranslateX(20);
        footer.setVgap(10);
        footer.setHgap(20);

        return footer;
    }
    private void addItemsToCart(Product product){
        if(cartList.contains(product)) return;
        cartList.add(product);
    }

    public static void main(String[] args) {
        launch();
    }
}