/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Enrique Zegarra
 */
package utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogService {
    private static final Logger logger = Logger.getLogger("SistemaInventario");
    private static FileHandler fileHandler;

    static {
        try {
            fileHandler = new FileHandler("sistema.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL); 
           
            System.out.println(">>> ARCHIVO LOG CREADO EN: " + new java.io.File("sistema.log").getAbsolutePath());
            
        } catch (IOException | SecurityException e) {
            System.err.println("Error al iniciar el sistema de logs: " + e.getMessage());
        }
    }

    /**
     * Registra un mensaje informativo "Usuario inició sesión"
     */
    public static void info(String mensaje) {
        logger.info(mensaje);
    }

    /**
     * Registra un mensaje de advertencia "Stock bajo detectado"
     */
    public static void warning(String mensaje) {
        logger.warning(mensaje);
    }

    /**
     * Registra un error grave con su excepción "Error SQL"
     */
    public static void error(String mensaje, Throwable error) {
        logger.log(Level.SEVERE, mensaje, error);
    }
}
