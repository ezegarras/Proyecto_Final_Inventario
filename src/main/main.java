    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import com.formdev.flatlaf.FlatLightLaf;
import controlador.LoginController;
import javax.swing.UIManager;
import vista.LoginWindow;
import dao.*;
import utils.DataUpdateNotifier;

/**
 *
 * @author Enrique Zegarra
 */

public class main {
    
public static void main(String[] args) {

    try {
        FlatLightLaf.setup();
        UIManager.put("defaultFont", new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    } catch (Exception e) {
        System.err.println("Error al iniciar FlatLaf: " + e.getMessage());
    }

    // Mejora de eficiencia aplicando SOLID - DIP INVERSION DE DEPENDENCIAS
    
    final IUsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    final IProductoDAO productoDAO = new ProductoDAOImpl();
    final ICategoriaDAO categoriaDAO = new CategoriaDAOImpl();
    final IProveedorDAO proveedorDAO = new ProveedorDAOImpl();
    final IEntradaDAO entradaDAO = new EntradaDAOImpl();
    final IClienteDAO clienteDAO = new ClienteDAOImpl();
    final ISalidaDAO salidaDAO = new SalidaDAOImpl();
    final IRolDAO rolDAO = new RolDAOImpl();
    
    final DataUpdateNotifier notifier = new DataUpdateNotifier();
    

    javax.swing.SwingUtilities.invokeLater(() -> {

        
        LoginWindow loginView = new LoginWindow();

        
        new LoginController(loginView, usuarioDAO, productoDAO, categoriaDAO, 
                            proveedorDAO, entradaDAO, clienteDAO, salidaDAO, rolDAO, notifier);

        
        loginView.setVisible(true);
    });
}
}
