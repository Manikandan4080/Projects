package com.example.ecommerce;
import java.sql.*;

public class Database {

    String dbUrl = "jdbc:mysql://localhost:3306/ecommerce";
    String user = "root";
    String password = "Mani400800#";

    private Statement getStatement(){
        try{
            Connection con = DriverManager.getConnection(dbUrl, user, password);
            return con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResultSet getQueryTable(String query){

        Statement statement = getStatement();
        try {
            return statement.executeQuery(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertValues(String query){
        Statement statement = getStatement();
        try {
            statement.executeUpdate(query);
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

//    public static void main(String[] args) {
//        String query = "SeLECT * FROM products";
//        Database dbCon = new Database();
//        ResultSet rs = dbCon.getQueryTable(query);
//
//        if(rs != null) System.out.println("Connected");
//    }
}
