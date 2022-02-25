package com.pkg.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mod_User {

    //--> Variables
    private Statement s;

    //--> Functions


    //-> Connect Database
    public Connection connect_db() throws SQLException {
        String db_username = "root";
        String db_password = null;
        Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/foms?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",db_username,db_password);
        return cn;
    }

    //-> Add User
    public String addUser(String Name, String Email, String Pswd, String Image){
        try {
            String Query = "INSERT INTO `users`(`Name`, `Email`, `Pswd`, `Img`) VALUES (?,?,?,?)";
            PreparedStatement statement = connect_db().prepareStatement(Query);
            statement.setString(1, Name);
            statement.setString(2, Email);
            statement.setString(3, Pswd);
            statement.setString(4, Image);
            statement.executeUpdate();
            return "Registered Successfully ..!!";
        }
        catch(SQLException e){
            if(e.getErrorCode() == 1062){
                return "User Already Exists ..!!";
            }
            else{
                return "Internal Server Error ..!!' ,'Error Code' : "+String.valueOf(e.getErrorCode());
            }

        }

    }

    //-> Get All User
    public String getAllUser() throws JSONException {
        JSONObject userList = new JSONObject();
        try{
            String Query = "SELECT * FROM `users`";
            PreparedStatement statement = connect_db().prepareStatement(Query);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                List<String> temp = new ArrayList<>();
                temp.add(result.getString("Name"));
                temp.add(result.getString("Email"));
                userList.put(result.getString("ID"),temp);
            }
            return String.valueOf(userList);
        }
        catch (SQLException e){
            e.getErrorCode();
        }
        return null;
    }

    //-> Login
    public Map<String, String> login(String email , String pswd){
        HashMap<String, String> userInfo = new HashMap<String, String>();
        try{
            String Query = "SELECT * FROM `users` Where `Email` = ? and `Pswd` = ?";
            PreparedStatement statement = connect_db().prepareStatement(Query);
            statement.setString(1, email);
            statement.setString(2, pswd);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                userInfo.put("msg","Valid Credentials ..!!");
                userInfo.put("ID",result.getString("ID"));
                userInfo.put("Name",result.getString("Name"));
                userInfo.put("Email",result.getString("Email"));
                userInfo.put("Img", "http://localhost:8080/UserImg/"+result.getString("Img"));
            }
        }
        catch (SQLException e){
            e.getErrorCode();
        }

        return userInfo;
    }

}
