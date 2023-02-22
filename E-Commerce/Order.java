package com.example.ecom;

public class Order {

    public boolean placeOrder(Customers customer, Products product){

        int cid = customer.getId();
        int pid = product.getId();

        //insert into orders(cid, pid, status) values(1,1,"Ordered")
        try{
            String query = "insert into orders(cid, pid, status) values("+cid+","+pid+",'Ordered')";
            Database db = new Database();
            return db.insertValues(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
