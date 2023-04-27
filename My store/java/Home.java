package com.example.estore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Home implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public Customer customer = new Customer();
    public Product currProduct = new Product();

    public void setCustomer(Customer cust) {
        customer = cust;
    }

    @FXML
    public Label welcomeText = new Label();
    public void setWelcomeText(){
        if(customer != null){
            welcomeText.setText("Welcome "+customer.getName());
        }
        else {
            System.out.println("Null!!");
        }
    }

    @FXML
    private ComboBox<Integer> quantityBox;
    @FXML
    private ImageView productImg;
    @FXML
    private Label productName;
    @FXML
    private Label productPrice;
    @FXML
    private Label about;
    @FXML
    private TextField searchFeild;
    @FXML
    private FlowPane flowPane;
    int height = 150;

    private List<Product> productList = new ArrayList<>();
    private MyListener myListener;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productList.addAll(getData());
        if(productList.size() > 0){
            setChosenPro(productList.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product products) {
                    setChosenPro(products);
                }
            };

            int count = 1;
            for(int i = 0; i < productList.size(); i++){
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(EStore.class.getResource("Items.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    Item item = fxmlLoader.getController();
                    item.setData(productList.get(i),myListener);

                    flowPane.setPrefHeight(height*(i+1));
                    flowPane.getChildren().add(anchorPane);

                }catch (Exception e) { e.printStackTrace();}
            }
        }
    }
    private List<Product> getData(){
        List<Product> pList = new ArrayList<>();
        Database db = new Database();
        String query = "Select * from products;";
        ResultSet rs = db.getTable(query);
        try{
            while (rs != null && rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("pid"));
                product.setDesc(rs.getString("about"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQty(rs.getInt("quantity"));

                Blob blob = rs.getBlob("pic");
                InputStream stream =blob.getBinaryStream();
                Image img = new Image(stream);
                product.setImage(img);
                pList.add(product);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return pList;
    }
    private void setChosenPro(Product product){
        currProduct = product;
        productName.setText(product.getName());
        productPrice.setText("₹."+product.getPrice());
        productImg.setImage(product.getImage());
        about.setText(product.getDesc());
        quantityBox.getItems().clear();

        int q = product.getQty();
        if(q == 0){
            quantityBox.getItems().add(0);
            quantityBox.setValue(0);
        }
        else{
            quantityBox.setValue(1);
            for( int i = 1; i <= q; i++){
                quantityBox.getItems().add(i);
            }
        }
    }

    public void searchButtonOnAction(ActionEvent event){
        //System.out.println(customer.getName());
        productList.clear();
        productList.addAll(getSearched(searchFeild.getText()));
        flowPane.getChildren().clear();
        flowPane.setPrefHeight(height);

        if(productList.size() > 0){
            setChosenPro(productList.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product products) {
                    setChosenPro(products);
                }
            };

            int count = 1;
            for(int i = 0; i < productList.size(); i++){
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(EStore.class.getResource("Items.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    Item item = fxmlLoader.getController();
                    item.setData(productList.get(i),myListener);

                    flowPane.setPrefHeight(height*(i+1));
                    flowPane.getChildren().add(anchorPane);

                }catch (Exception e) { e.printStackTrace();}
            }
        }
    }
    private List<Product> getSearched(String word){
        List<Product> pList = new ArrayList<>();
        Database db = new Database();
        String query = "Select * from products where name like '%"+word+"%'";

        ResultSet rs = db.getTable(query);
        try{
            while (rs != null && rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("pid"));
                product.setDesc(rs.getString("about"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQty(rs.getInt("quantity"));

                Blob blob = rs.getBlob("pic");
                InputStream stream =blob.getBinaryStream();
                Image img = new Image(stream);
                product.setImage(img);
                pList.add(product);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return pList;
    }

    public void addToCartButtonOnAction(ActionEvent event){
        //System.out.println(customer.getName());

        if(customer != null){
            if (currProduct.getQty() == 0) {
                showDialogue.show("Cart Status", "Product Out Of Stock");
            } else {
                boolean status = Order.addToCart(customer, currProduct);

                if (status) showDialogue.show("Cart Status", "Successfully added");

                else showDialogue.show("Cart Status", "Somthing went wrong");
            }
        }
        else{
            System.out.println("Not Good");
        }
    }

    public void cartButtonOnAction(ActionEvent event){
        productList.clear();
        productList.addAll(getCartItems());
        flowPane.getChildren().clear();
        flowPane.setPrefHeight(height);

        if(productList.size() > 0){
            setChosenPro(productList.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product products) {
                    setChosenPro(products);
                }
            };

            int count = 1;
            for(int i = 0; i < productList.size(); i++){
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(EStore.class.getResource("Items.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    Item item = fxmlLoader.getController();
                    item.setData(productList.get(i),myListener);

                    flowPane.setPrefHeight(height*(i+1));
                    flowPane.getChildren().add(anchorPane);

                }catch (Exception e) { e.printStackTrace();}
            }
        }

    }
    private List<Product> getCartItems(){
        List<Product> products = new ArrayList<>();
        Database db = new Database();
        String query = "select * from products where pid in (select pid from cart where cid = "+customer.getId()+");";
        //System.out.println(query);
        ResultSet rs = db.getTable(query);
        try {
            while (rs != null && rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("pid"));
                product.setDesc(rs.getString("about"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQty(rs.getInt("quantity"));

                Blob blob = rs.getBlob("pic");
                InputStream stream =blob.getBinaryStream();
                Image img = new Image(stream);
                product.setImage(img);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public void buyButtonOnAction(ActionEvent event){
        if(customer != null){
            if (currProduct.getQty() == 0) {
                showDialogue.show("Cart Status", "Product Out Of Stock");
            } else {
                boolean placed = Order.placeOrder(customer, currProduct);
                if (placed) {
                    Order.updateQty(currProduct, quantityBox.getValue());
                    showDialogue.show("Order Status", "Ordered SuccessFully !!!");
                } else {
                    showDialogue.show("Order Status", "Something Went Wrong");
                }
            }
        }
        else{
            System.out.println("Not Good");
        }
    }

    public void logOutButtonAction(ActionEvent event){
        customer = null;
        currProduct = null;
        try{
            showDialogue.show("Log Out status", "Successfully Logout!!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            root = loader.load();

            Controller controller = loader.getController();
            controller.logOut();

            scene = new Scene(root);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void myOrdersButtonOnAction(ActionEvent event){
        if(customer != null){
            productList.clear();
            productList.addAll(Order.myOrders(customer));
            flowPane.getChildren().clear();
            flowPane.setPrefHeight(height);

            if (productList.size() > 0) {
                setChosenPro(productList.get(0));
                myListener = new MyListener() {
                    @Override
                    public void onClickListener(Product products) {
                        setChosenPro(products);
                    }
                };

                int count = 1;
                for (int i = 0; i < productList.size(); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(EStore.class.getResource("Items.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        Item item = fxmlLoader.getController();
                        item.setData(productList.get(i), myListener);

                        flowPane.setPrefHeight(height * (i + 1));
                        flowPane.getChildren().add(anchorPane);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        else{
            System.out.println("Not Good");
        }
    }

    public void backButtonOnAction(ActionEvent event) throws IOException {
        productList.addAll(getData());
        if(productList.size() > 0){
            setChosenPro(productList.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product products) {
                    setChosenPro(products);
                }
            };

            int count = 1;
            for(int i = 0; i < productList.size(); i++){
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(EStore.class.getResource("Items.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    Item item = fxmlLoader.getController();
                    item.setData(productList.get(i),myListener);

                    flowPane.setPrefHeight(height*(i+1));
                    flowPane.getChildren().add(anchorPane);

                }catch (Exception e) { e.printStackTrace();}
            }
        }
    }

    public void removeButtonOnAction(ActionEvent event){
        if(Order.removeCancell("Remove", customer, currProduct)){
            showDialogue.show("Cart Status", "Removed From Cart");
        }
        else{
            showDialogue.show("Cart Status", "Something went wrong");
        }
    }

    public void cancelbuttonSetOnAction(ActionEvent event){
        if(Order.removeCancell("Cancel", customer, currProduct)){
            showDialogue.show("Order Status", "Order Cancelled");
        }
        else{
            showDialogue.show("Order Status", "Something went wrong");
        }
    }

}
