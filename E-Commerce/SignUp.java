package com.example.ecom;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUp{

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
    private static boolean create(String email, String name, String number, String pass){

        String enPas = getEncPass(pass);

        System.out.println(enPas);

        try{
            String query = "insert into customer(name, email, mobile, password) values('"+
                    name+"',"+"'"+email+"',"+"'"+Integer.parseInt(number)+"',"+"'"+enPas+"')";
            Database db = new Database();
            return db.insertValues(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
    static int createAccount(String email, String name, String number,String pass, String conPass){
        if(!pass.equals(conPass)) return 1;

        //select * from customer where email like "abc@gmail.com";

        String query = "select * from customer where email like '" + email+"'";
        Database con = new Database();

        ResultSet rs = con.getQueryTable(query);
        try {
            if(rs != null && rs.next()) return 2;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
//insert into customer(name, email, mobile, password) values('Mithun', 'abc@gmail.com', 34576, 'mithun123'),

        boolean account = create(email, name, number, pass);

        return 0;
    }
}
