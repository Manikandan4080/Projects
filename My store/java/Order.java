package com.example.estore;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Order{
    static Database conn = new Database();
    public static boolean placeOrder(Customer customer, Product product){
        try{
            String query = "INSERT INTO orders(cid,pid,status) values("+customer.getId()+","+product.getId()+",'Ordered');";
            return conn.insertUpdates(query);
        }
        catch (Exception e){e.printStackTrace();}
        return false;
    }

    public static void updateQty(Product product, int quty){
        try{
            String query ="Update products set quantity = "+ (product.getQty() - quty) +" where pid = "+product.getId()+";";
        }
        catch (Exception e){e.printStackTrace();}
    }

    public static boolean addToCart(Customer customer, Product product) {
        try{
            String query = "INSERT INTO cart(cid,pid) values("+customer.getId()+","+product.getId()+");";
            return conn.insertUpdates(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static List<Product> myOrders(Customer customer){
        List<Product> products = new ArrayList<>();
        Database db = new Database();
        String query = "select * from products where pid in (select pid from orders where cid = "+customer.getId()+");";
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
                products.add(product);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return products;
    }

    public static boolean removeCancell(String toDo, Customer customer, Product product){
        if(toDo.equals("Remove")){
            String query = "delete from cart where cid = "+customer.getId()+" and pid ="+product.getId()+";";
            return conn.insertUpdates(query);
        }
        else{
            String query = "delete from orders where cid = "+customer.getId()+" and pid ="+product.getId()+";";
            return conn.insertUpdates(query);
        }
    }
}