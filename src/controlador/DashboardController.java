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
import dao.*;
import utils.DataUpdateNotifier;

public class DashboardController {

    private final DashboardWindow vista;
    private final Usuario usuario;
    
    private final IProductoDAO productoDAO;
    private final ICategoriaDAO categoriaDAO;
    private final IProveedorDAO proveedorDAO;
    private final IEntradaDAO entradaDAO;
    private final IClienteDAO clienteDAO;
    private final ISalidaDAO salidaDAO;
    private final IUsuarioDAO usuarioDAO;
    private final IRolDAO rolDAO;
    private final DataUpdateNotifier notifier; 
    
    public DashboardController(DashboardWindow vista, Usuario usuario, IProductoDAO pDAO, ICategoriaDAO cDAO, 
                           IProveedorDAO provDAO, IEntradaDAO eDAO, IClienteDAO cliDAO, 
                           ISalidaDAO sDAO, IUsuarioDAO uDAO, IRolDAO rDAO, DataUpdateNotifier notifier) {

    this.vista = vista;
    this.usuario = usuario;

    // Guarda todos los DAOs
    this.productoDAO = pDAO;
    this.categoriaDAO = cDAO;
    this.proveedorDAO = provDAO;
    this.entradaDAO = eDAO;
    this.clienteDAO = cliDAO;
    this.salidaDAO = sDAO;
    this.usuarioDAO = uDAO;
    this.rolDAO = rDAO;
    this.notifier = notifier;

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
    vista.getBtnNavSalidas().addActionListener(e -> 
    vista.getCardLayout().show(vista.getPanelContent(), "salidas")
    );
    vista.getBtnNavProveedores().addActionListener(e -> 
    vista.getCardLayout().show(vista.getPanelContent(), "proveedores")
    );
    vista.getBtnNavClientes().addActionListener(e -> 
    vista.getCardLayout().show(vista.getPanelContent(), "clientes")
    );
    vista.getBtnNavReportes().addActionListener(e -> 
    vista.getCardLayout().show(vista.getPanelContent(), "reportes") 
    );
    vista.getBtnNavUsuarios().addActionListener(e -> 
    vista.getCardLayout().show(vista.getPanelContent(), "usuarios")
    );
    String rol = usuario.getRolNombre();

    
    vista.getBtnNavInventario().setVisible(false);
    vista.getBtnNavEntradas().setVisible(false);
    vista.getBtnNavSalidas().setVisible(false);
    vista.getBtnNavUsuarios().setVisible(false);
    

    
    switch (rol) {
        
        case "Administrador":
            
            vista.getBtnNavInventario().setVisible(true);
            vista.getBtnNavEntradas().setVisible(true);
            vista.getBtnNavSalidas().setVisible(true);
            vista.getBtnNavProveedores().setVisible(true);
            vista.getBtnNavClientes().setVisible(true);
            vista.getBtnNavReportes().setVisible(true); 
            vista.getBtnNavUsuarios().setVisible(true);
            vista.getBtnNavUsuarios().setVisible(true);
            
            break;
            
        case "Ventas":
            
            vista.getBtnNavSalidas().setVisible(true);
            vista.getBtnNavClientes().setVisible(true);
            break;
            
        case "Almacen":
           
            vista.getBtnNavInventario().setVisible(true);
            vista.getBtnNavEntradas().setVisible(true);
            vista.getBtnNavProveedores().setVisible(true);
           
            break;
    }
}
    
    
    private void cerrarSesion() {
        // Crea la nueva vista de Login
        LoginWindow loginView = new LoginWindow();
        // Crea su controlador
        new LoginController(loginView, usuarioDAO, productoDAO, categoriaDAO, 
                    proveedorDAO, entradaDAO, clienteDAO, salidaDAO, rolDAO, notifier);
        // Navega
        NavigationManager.navigateTo(vista, loginView);
    }
}
