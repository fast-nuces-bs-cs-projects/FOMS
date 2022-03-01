package com.pkg.Model;

import com.pkg.Connect_Db;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Mod_Item {

    //--> Call Class
    Connect_Db con_db = new Connect_Db();

    //--> Vairables
    private Statement s;
    private String UPLOADED_FOLDER = System.getProperty("user.dir") + "/src/main/resources/public/FoodImg/";
    private String ItemName;
    private String ItemDetail;
    private String Price;
    private String File;

    //-> Getter Functions
    public String getItemName() {return ItemName;}
    public String getItemDetail() {return ItemDetail;}
    public String getPrice() {return Price;}
    public String getFile() {return File;}

    //-> Setter Functions
    public void setItemName(String itemName) {ItemName = itemName;}
    public void setItemDetail(String itemDetail) {ItemDetail = itemDetail;}
    public void setPrice(String price) {Price = price;}
    public void setFile(String file) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(file);
        Path path = Paths.get(UPLOADED_FOLDER + getItemName()+".png");
        Files.write(path, bytes);
        File = getItemName()+".png";
    }


    //-->  Get Request

    //-> All Users
    public String get_all_item() throws JSONException {
        JSONObject itemList = new JSONObject();
        try{
            String Query = "SELECT * FROM `items`";
            PreparedStatement statement = con_db.Connect_Db().prepareStatement(Query);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                List<String> temp = new ArrayList<>();
                temp.add(result.getString("Item_Name"));
                temp.add(result.getString("Item_Detail"));
                temp.add(result.getString("Price"));
                temp.add("http://localhost:8080/FoodImg/"+result.getString("Img"));
                itemList.put(result.getString("ID"),temp);
            }
            return String.valueOf(itemList);
        }
        catch (SQLException e){
            e.getErrorCode();
        }
        return null;
    }


    //-->  Post Request

    //-> Add Item
    public Boolean addItem(){
        try {
            String Query = "INSERT INTO `items`(`Item_Name`, `Item_Detail`, `Price`, `Img`) VALUES (?,?,?,?)";
            PreparedStatement statement = con_db.Connect_Db().prepareStatement(Query);
            statement.setString(1, getItemName());
            statement.setString(2, getItemDetail());
            statement.setString(3, getPrice());
            statement.setString(4, getFile());
            statement.executeUpdate();
            return true;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
