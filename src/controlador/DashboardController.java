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
        inicializar();
    }
    
    private void inicializar() {
        
    // 1. Asignar listeners (eventos)
    vista.getBtnCerrarSesion().addActionListener(e -> cerrarSesion());

    vista.getBtnNavHome().addActionListener(e -> 
        vista.getCardLayout().show(vista.getPanelContent(), "home")
    );
    vista.getBtnNavInventario().addActionListener(e -> 
        vista.getCardLayout().show(vista.getPanelContent(), "inventario")
    );
    // Aquí añado otros listeners
    vista.getBtnNavEntradas().addActionListener(e -> 
    vista.getCardLayout().show(vista.getPanelContent(), "entradas")
    );

   
    String rol = usuario.getRolNombre();

    
    vista.getBtnNavInventario().setVisible(false);
    vista.getBtnNavEntradas().setVisible(false);
    vista.getBtnNavSalidas().setVisible(false);
    

    
    switch (rol) {
        case "Administrador":
            
            vista.getBtnNavInventario().setVisible(true);
            vista.getBtnNavEntradas().setVisible(true);
            vista.getBtnNavSalidas().setVisible(true);
            
            break;
        case "Ventas":
            
            vista.getBtnNavSalidas().setVisible(true);
            
            break;
        case "Almacen":
           
            vista.getBtnNavInventario().setVisible(true);
            vista.getBtnNavEntradas().setVisible(true);
           
            break;
    }
}
    
    
    private void cerrarSesion() {
        // Crea la nueva vista de Login
        LoginWindow loginView = new LoginWindow();
        // Crea su controlador
        new LoginController(loginView);
        // Navega
        NavigationManager.navigateTo(vista, loginView);
    }
}
