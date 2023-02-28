package com.example.ecommerce;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product{
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    //private  SimpleIntegerProperty quantity;

    public int getId(){
        return id.get();
    }
    public String getName(){
        return name.get();
    }
    public Double getPrice(){
        return price.get();
    }

    public Product(int id, String name, Double price){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    public static ObservableList<Product> getAllProduct(){
        String query = "SELECT * FROM products";
        return getProducts(query);
    }

    public static ObservableList<Product>  getProducts(String  query){
        Database db = new Database();
        ResultSet rs = db.getQueryTable(query);
        ObservableList<Product> products = FXCollections.observableArrayList();
        try{
            if(rs != null){
                while(rs.next()){
                    products.add(new Product(
                            rs.getInt("pid"),
                            rs.getString("name"),
                            rs.getDouble("price")
                    ));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return products;
    }

}
