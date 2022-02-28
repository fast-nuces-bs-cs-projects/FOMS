package com.pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect_Db {

    //-> Connect Database
    public Connection Connect_Db() throws SQLException {
        String db_username = "root";
        String db_password = null;
        Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/foms?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",db_username,db_password);
        return cn;
    }
}
