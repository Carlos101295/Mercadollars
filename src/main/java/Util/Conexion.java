package Util;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Fran
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/MERCADOLLARS_DB";
    private static final String USER = "root";
    private static final String PASS = "YouAreMyLive-2033";

    private static Connection conectar = null;

    public static Connection obtenerConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (conectar == null || conectar.isClosed()) {
                conectar = DriverManager.getConnection(URL, USER, PASS);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el Driver de MySQL. Añade el JAR a las librerías.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error: No se pudo conectar a la base de datos. ¿Está encendido el servidor?");
            e.printStackTrace();
        }
        return conectar;
    }
}
