package com.example.estore;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login{
    static Database db = new Database();
    public static Customer customerLogin(String mail, String password){
        String pass = getEncPass(password);

        String query = "SELECT * FROM customer where email = '"+mail+
                "' and password = '"+pass+"'";
        try{
            ResultSet rs = db.getTable(query);
            if(rs != null && rs.next()){
                return new Customer(
                        rs.getInt("cid"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getEncPass(String str){
        try{
            BigInteger num = new BigInteger(1, getSha(str));
            StringBuilder pass = new StringBuilder(num.toString(16));
            return pass.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private  static byte[] getSha(String inp){
        try{
            MessageDigest ms = MessageDigest.getInstance("SHA-256");
            return ms.digest(inp.getBytes(StandardCharsets.UTF_8));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
