package com.reconocimiento.database;

import java.sql.*;

public class Conexion {
    public static Connection conexion = getConnection();
    
    //Conexion a la base de datos
    public static Connection getConnection() {

        Connection connection;
        String query = "jdbc:mysql://localhost:3306/";
        String database = "institucion";
        String user = "root";
        String pass = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(query + database, user, pass);

            System.out.println("Conexion a la base de datos");

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        
        return connection;
    }
}
