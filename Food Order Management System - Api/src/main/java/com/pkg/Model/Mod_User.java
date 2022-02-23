package com.pkg.Model;

import java.sql.*;

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
    /*public boolean addUser(String Name,String Email,String Pswd,String Image){
        try {
            String login_Query = "INSERT INTO `users`(`Name`, `Email`, `Pswd`, `Img`) VALUES (?,?,?,?)";
            PreparedStatement statement = connect_db().prepareStatement(login_Query);
            statement.setString(1, Name);
            statement.setString(2, Email);
            statement.setString(3, Pswd);
            statement.setString(4, Image);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return true;
            }
            else{
                return false;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }*/


}
