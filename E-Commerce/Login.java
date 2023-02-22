package com.example.ecom;
import javafx.util.converter.BigIntegerStringConverter;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
public class Login {

    private static byte[] getSha(String inp){

        try{
            MessageDigest ms = MessageDigest.getInstance("SHA-256");
            return ms.digest(inp.getBytes(StandardCharsets.UTF_8));
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getEncPass(String password){
        try{
            BigInteger num = new BigInteger(1, getSha(password));
            StringBuilder pass = new StringBuilder(num.toString(16));
            return pass.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }
    public static Customers customerLogin(String email, String password){
        String pass = getEncPass(password);

        String query = "select * from customer where email = '"+ email +"' and password = '"+ pass +"'";

        Database con = new Database();
        try {
            ResultSet rs = con.getQueryTable(query);
            if(rs!= null && rs.next()){
                return new Customers(
                        rs.getInt("cid"),
                        rs.getString("name"),
                        rs.getString("email")
                        );
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

//    public static void main(String[] args) {
//
//        System.out.println(customerLogin("def@gmail.com", "asdfghjk"));
//
//        System.out.println(getEncPass("abc"));
//    }
}
