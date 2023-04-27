package com.example.estore;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Item{
    @FXML
    private Label aboutProduct;
    @FXML
    private Label productName;
    @FXML
    private ImageView productPic;
    @FXML
    private Label productPrice;

    private Product products;
    private MyListener listener;

    public void setData(Product product, MyListener listener){
        this.products = product;
        this.listener = listener;
        productName.setText(products.getName());
        productPrice.setText("Rs. "+products.getPrice());
        aboutProduct.setText(products.getDesc());
        productPic.setImage(products.getImage());
    }
    public void click(javafx.scene.input.MouseEvent mouseEvent) {
        listener.onClickListener(products);
    }
}
