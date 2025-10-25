/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

/**
 *
 * @author Enrique Zegarra
 */

import modelo.Usuario;
import utils.NavigationManager;
import vista.DashboardWindow;
import vista.LoginWindow;

public class DashboardController {

    private final DashboardWindow vista;
    private final Usuario usuario;
    
    public DashboardController(DashboardWindow vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        
        // Asignar eventos (listeners)
        setupActionListeners();
    }
    
    private void setupActionListeners() {
        
        // --- Botón Cerrar Sesión ---
        vista.getBtnCerrarSesion().addActionListener(e -> cerrarSesion());
        
        // --- Navegación del Menú ---
        vista.getBtnNavHome().addActionListener(e -> 
            vista.getCardLayout().show(vista.getPanelContent(), "home")
        );
        
        vista.getBtnNavInventario().addActionListener(e -> 
            
            vista.getCardLayout().show(vista.getPanelContent(), "inventario")
        );
        
        // (Añadiremos los otros listeners aquí)
    }
    
    private void cerrarSesion() {
        // 1. Crea la nueva vista de Login
        LoginWindow loginView = new LoginWindow();
        // 2. Crea su controlador
        new LoginController(loginView);
        // 3. Navega
        NavigationManager.navigateTo(vista, loginView);
    }
}
