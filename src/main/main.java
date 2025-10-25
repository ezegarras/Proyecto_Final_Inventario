/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import com.formdev.flatlaf.FlatLightLaf; // Importa FlatLaf
import controlador.LoginController;
import javax.swing.UIManager;
import vista.LoginWindow;

/**
 *
 * @author Enrique Zegarra
 */
public class main {
    
        public static void main(String[] args) {
        
        // 1. Configurar el Look and Feel (FlatLaf)
        try {
            FlatLightLaf.setup();
            // (Opcional) Cambiar la fuente por defecto
            UIManager.put("defaultFont", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        } catch (Exception e) {
            System.err.println("Error al iniciar FlatLaf: " + e.getMessage());
        }

        // 2. Iniciar la aplicación en el hilo de Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            
            // 1. Crea la VISTA
            LoginWindow loginView = new LoginWindow();
            
            // 2. Crea el CONTROLADOR (y le pasa la vista)
            new LoginController(loginView);
            
            // 3. Muestra la VISTA
            loginView.setVisible(true);
        });
    }
}
