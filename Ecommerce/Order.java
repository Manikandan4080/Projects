package com.example.ecommerce;


import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Order{
    public TableView<Product> orderTable;
    public boolean placeOrder(Customers customer , Product product){
        int cid = customer.getId();
        int pid = product.getId();

        try {
            String query = "INSERT INTO orders(cid, pid, status) values ("+cid+","+pid+", ' Ordered')";
            Database db = new Database();
            return db.insertValues(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public  int cartOrder(ObservableList<Product> cartList, Customers customer){
        int count = 0;
        for(Product product : cartList){
            if(placeOrder(customer, product)) count++;
        }

        return count;
    }

    public Pane getAllProducts(){
        ObservableList<Product> pList = Product.getAllProduct();

        return tableForList(pList);
    }

    public Pane tableForList(ObservableList<Product> orderList){
        TableColumn id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn<>("name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn<>("price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        orderTable = new TableView<>();
        orderTable.setItems(orderList);
        orderTable.getColumns().addAll(id, name, price);

        Pane pane = new Pane();
        pane.getChildren().add(orderTable);

        return pane;
    }

    public Pane myOrder(Customers customer){
        String query = "select orders.oid, products.name,products.price from " +
                "orders inner join products on orders.pid = products.pid " +
                "where cid = "+customer.getId();

        ObservableList<Product> orderList = Product.getProducts(query);

        return tableForList(orderList);
    }
}
