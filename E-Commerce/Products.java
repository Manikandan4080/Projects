package com.example.ecom;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Products {

    private SimpleIntegerProperty id;

    private SimpleStringProperty name;

    private SimpleDoubleProperty price;

    public int getId(){
        return id.get();
    }
    public String getName(){
        return name.get();
    }
    public Double getPrice(){
        return price.get();
    }
    public Products(int id, String name, Double price){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    public static ObservableList<Products> getAllProducts(){
        String allList = "SELECT * FROM products";
        return getProducts(allList);
    }
    public static ObservableList<Products> getProducts(String query){
        Database dbCon = new Database();
        ResultSet rs = dbCon.getQueryTable(query);
        ObservableList<Products> products = FXCollections.observableArrayList();

        try{
            if(rs != null){
                while (rs.next()){

                    products.add(new Products(
                            rs.getInt("pid"),
                            rs.getString("name"),
                            rs.getDouble("price")
                            )
                    );
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return products;
    }
}
