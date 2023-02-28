package com.example.ecommerce;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class ProductList{
    public TableView<Product> productTable;

    public Pane getAllProducts(){
        ObservableList<Product> pList = Product.getAllProduct();

        return tableForList(pList);
    }

    public Pane tableForList(ObservableList<Product> pList){
        TableColumn id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn<>("name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn<>("price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable = new TableView<>();
        productTable.setItems(pList);
        productTable.getColumns().addAll(id, name, price);

        Pane pane = new Pane();
        pane.getChildren().add(productTable);

        return pane;
    }

    public Pane productInCart(ObservableList<Product> pList){
        return tableForList(pList);
    }

    public Product getSelected(){
        return  productTable.getSelectionModel().getSelectedItem();
    }
}
