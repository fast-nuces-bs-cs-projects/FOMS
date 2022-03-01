package com.pkg.Model;

import com.pkg.Connect_Db;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class Mod_User {

    //-> Call Class
    Connect_Db con_db = new Connect_Db();

    //-> Variables
    private Statement s;
    private String UPLOADED_FOLDER = System.getProperty("user.dir") + "/src/main/resources/public/UserImg/";
    private String Name ;
    private String Email;
    private String Add;
    private String Phno;
    private String Pswd;
    private String Type;
    private String File;
    private String Created_at;

    //-> Getter Functions
    public String getUPLOADED_FOLDER() {return UPLOADED_FOLDER;}
    public String getName() {return Name;}
    public String getEmail() {return Email;}
    public String getAdd() {return Add;}
    public String getPhno() {return Phno;}
    public String getPswd() {return Pswd;}
    public String getType() {return Type;}
    public String getFile() {return File;}

    //-> Setter Functions
    public void setName(String name)   {Name = name;}
    public void setEmail(String email) {Email = email;}
    public void setAdd(String add) {Add = add;}
    public void setPhno(String phno) {Phno = phno;}
    public void setPswd(String pswd) {Pswd = pswd;}
    public void setType(String type) {Type = type;}
    public void setFile(String file) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(file);
        Path path = Paths.get(UPLOADED_FOLDER + getName()+".png");
        Files.write(path, bytes);
        File = getName()+".png";
    }

    //-->  Get Request

    //-> All Users
    public String get_all_user() throws JSONException {
        JSONObject userList = new JSONObject();
        try{
            String Query = "SELECT * FROM `users`";
            PreparedStatement statement = con_db.Connect_Db().prepareStatement(Query);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                List<String> temp = new ArrayList<>();
                temp.add(result.getString("Name"));
                temp.add(result.getString("Email"));
                temp.add(result.getString("Type"));
                temp.add(result.getString("Created_at"));
                userList.put(result.getString("ID"),temp);
            }
            return String.valueOf(userList);
        }
        catch (SQLException e){
            e.getErrorCode();
        }
        return null;
    }



    //-->  Post Request

    //-> Add Operator/Customer
    public Boolean add_user(){
        try {
            String Query = "INSERT INTO `users`(`Name`, `Email`, `Pswd`, `Type`, `Created_at`, `Img`) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement =  con_db.Connect_Db().prepareStatement(Query);
            //-> Current Date & Time
            java.util.Date date=new java.util.Date();
            java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime());
            statement.setString(1, getName());
            statement.setString(2, getEmail());
            statement.setString(3, getPswd());
            statement.setString(4, getType());
            statement.setString(5, String.valueOf(sqlTime));
            statement.setString(6, getFile());
            statement.executeUpdate();
            return true;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return false;}
    }

    //-> Add Customer Details
    public Boolean add_customer_details(){
        try {
            String Query = "INSERT INTO `customers_details`(`Email`, `PhoneNo`, `Address`) VALUES (?,?,?)";
            PreparedStatement statement =  con_db.Connect_Db().prepareStatement(Query);
            statement.setString(1, getEmail());
            statement.setString(2, getPhno());
            statement.setString(3, getAdd());
            statement.executeUpdate();
            return true;
        }
        catch(SQLException e){ return  false; }
    }

    //-> Login User
    public Map<String, String> verify_credentials(){
        HashMap<String, String> userInfo = new HashMap<String, String>();
        try{
            String Query = "SELECT * FROM `users` Where `Email` = ? and `Pswd` = ?";
            PreparedStatement statement = con_db.Connect_Db().prepareStatement(Query);
            statement.setString(1, getEmail());
            statement.setString(2, getPswd());
            ResultSet result = statement.executeQuery();
            while(result.next()){
                userInfo.put("ID",result.getString("ID"));
                userInfo.put("Name",result.getString("Name"));
                userInfo.put("Email",result.getString("Email"));
                userInfo.put("Type", result.getString("Type"));
                userInfo.put("Img", "http://localhost:8080/UserImg/"+result.getString("Img"));
            }
        }
        catch (SQLException e){
            e.getErrorCode();
        }

        return userInfo;
    }




}
