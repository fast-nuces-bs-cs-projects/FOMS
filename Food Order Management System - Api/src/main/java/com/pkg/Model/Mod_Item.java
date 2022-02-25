package com.pkg.Model;

import java.sql.*;

public class Mod_Item {

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

    //-> Add Item
    public String addItem(String Name, String Detail,String Image){
        try {
            String Query = "INSERT INTO `items`(`ItemName`, `ItemDetail`, `Img`) VALUES (?,?,?)";
            PreparedStatement statement = connect_db().prepareStatement(Query);
            statement.setString(1, Name);
            statement.setString(2, Detail);
            statement.setString(3, Image);
            statement.executeUpdate();
            return "Added Successfully ..!!";
        }
        catch(SQLException e){
            return "Internal Server Error ..!!' ,'Error Code' : "+String.valueOf(e.getErrorCode());
        }

    }
}
