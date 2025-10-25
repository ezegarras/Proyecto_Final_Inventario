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
    // (Añadiremos los otros listeners de navegación aquí)

    // 2. Aplicar Permisos (RBAC)
    String rol = usuario.getRolNombre();

    // Por defecto, ocultamos todo menos el 'Home' y 'Cerrar Sesión'
    vista.getBtnNavInventario().setVisible(false);
    vista.getBtnNavEntradas().setVisible(false);
    vista.getBtnNavSalidas().setVisible(false);
    // (Aquí irán los botones de Clientes, Proveedores, Reportes, Usuarios)

    // Aplicamos las reglas que definiste
    switch (rol) {
        case "Administrador":
            // Admin ve todo
            vista.getBtnNavInventario().setVisible(true);
            vista.getBtnNavEntradas().setVisible(true);
            vista.getBtnNavSalidas().setVisible(true);
            // (Habilitar Clientes, Proveedores, Reportes, Usuarios...)
            break;
        case "Ventas":
            // Ventas ve Salidas y Clientes
            vista.getBtnNavSalidas().setVisible(true);
            // (Habilitar Clientes...)
            break;
        case "Almacen":
            // Almacén ve Inventario, Entradas y Proveedores
            vista.getBtnNavInventario().setVisible(true);
            vista.getBtnNavEntradas().setVisible(true);
            // (Habilitar Proveedores...)
            break;
    }
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
