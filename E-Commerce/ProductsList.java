package com.example.ecom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class ProductsList {

    public TableView<Products> productTable;

    public Pane getAllProducts(){
        TableColumn id = new TableColumn("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<Products> data = FXCollections.observableArrayList();
        data.addAll(new Products(123, "LAPTOP", 500.0),
                new Products(456, "MOBILE", 800.0)
        );


        ObservableList<Products> pList = Products.getAllProducts();

        productTable = new TableView<>();
        productTable.setItems(pList);
        productTable.getColumns().addAll(id, name, price);

        Pane pane = new Pane();
        pane.getChildren().add(productTable);

        return pane;
    }


    public Products getSelected(){

        return productTable.getSelectionModel().getSelectedItem();
    }
}
