package com.example.estore;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

public class SignUp{
    public static boolean createAccount(String name, String email, String num, String pass){

        String query = "SELECT * FROM customer WHERE email LIKE '"+email+"'";
        Database con = new Database();
        ResultSet rs = con.getTable(query);

        try{
            if(rs != null && rs.next()) return false;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        boolean account = create(name, email, num, pass);
        return true;
    }
    private static boolean create(String name, String email, String num, String pass){
        String enPass = getEncPass(pass);
        String  query = "INSERT INTO customer (name, email, mobile, password) values ('" +
                name+"','"+email+"','"+num+"','"+enPass+"')";
        try{
            Database db = new Database();
            return db.insertUpdates(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
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
