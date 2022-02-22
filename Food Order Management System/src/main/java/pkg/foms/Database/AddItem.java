package pkg.foms.Database;

import java.sql.*;

public class AddItem {

    //--> Variables
    private Connection cn;

    //--> Functions

    //-> Connect Db
    public Connection connect_db() throws SQLException {
        String db_username = "root";
        String db_password = null;
        cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/foms?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",db_username,db_password);
        return cn;
    }

    //-> Add Food Item to DB
    






    public boolean Login_User(String username , String pswd){
        try {
            String db_username = null;
            String db_password = null;
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/arpa?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
            String login_Query = "SELECT * FROM login WHERE username= ? and pswd = ?";
            PreparedStatement statement = cn.prepareStatement(login_Query);
            statement.setString(1, username);
            statement.setString(2, pswd);
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


    }
}
