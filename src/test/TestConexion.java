package test;

import java.sql.Connection;
import java.sql.SQLException;
import utils.ConexionBD;

public class TestConexion {
    public static void main(String[] args) throws SQLException{
        // Establecer conexión
        ConexionBD.getConexion();
    }
}

