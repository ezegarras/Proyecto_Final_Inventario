/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Enrique Zegarra
 */
public class ConexionBD {
    
// CAMBIA ESTOS DATOS POR LOS TUYOS
    private static final String URL = "jdbc:mysql://localhost:3306/inventario_mype_v2";
    private static final String USER = "admin";
    private static final String PASSWORD = "Java2025";

    private static Connection conexion;

    private ConexionBD() { } // Constructor privado (Singleton)

    public static Connection getConexion() throws SQLException {
        
        try {
            // 1. Cargamos el driver (buena práctica)
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver de MySQL no encontrado.");
            throw new SQLException("Driver no encontrado", e);
        }
        
        // 2. Devolvemos una conexión NUEVA
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
