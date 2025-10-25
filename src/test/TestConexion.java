package test;

import java.sql.Connection;
import utils.ConexionBD;

public class TestConexion {
    public static void main(String[] args) {
        // Establecer conexión
        ConexionBD.getConexion();
    }
}

