package com.example.estore;
import java.sql.*;

public class Database{
    String dbUrl = "jdbc:mysql://localhost:3306/ecommerce";
    String user = "root";
    String password = "Mani400800#";

    private Statement getStatement(){
        try {
            Connection con = DriverManager.getConnection(dbUrl, user, password);
            return con.createStatement();
        }
        catch (SQLException e){e.printStackTrace();}

        return null;
    }
    public ResultSet getTable(String query){
        Statement statement = getStatement();
        try {
            return statement.executeQuery(query);
        }
        catch (SQLException e){e.printStackTrace();}
        return null;
    }

    public boolean insertUpdates(String query){
        Statement statement = getStatement();
        try {
            statement.executeUpdate(query);
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
