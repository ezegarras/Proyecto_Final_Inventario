/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.*;
import dao.UsuarioDAOImpl;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.LoginWindow;
import modelo.Usuario;
import utils.NavigationManager;
import vista.DashboardWindow;
import utils.DataUpdateNotifier;

/**
 *
 * @author Enrique Zegarra
 */
public class LoginController {
    
    private final LoginWindow vista;
    private final IUsuarioDAO usuarioDAO;
    private final IProductoDAO productoDAO;
    private final ICategoriaDAO categoriaDAO;
    private final IProveedorDAO proveedorDAO;
    private final IEntradaDAO entradaDAO;
    private final IClienteDAO clienteDAO;
    private final ISalidaDAO salidaDAO;
    private final IRolDAO rolDAO;
    private final DataUpdateNotifier notifier;

    public LoginController(LoginWindow vista, IUsuarioDAO uDAO, IProductoDAO pDAO, ICategoriaDAO cDAO, 
                       IProveedorDAO provDAO, IEntradaDAO eDAO, IClienteDAO cliDAO, 
                       ISalidaDAO sDAO, IRolDAO rDAO, DataUpdateNotifier notifier) {

    this.vista = vista;
    this.usuarioDAO = uDAO; 

    // Guardamos los que va a PASAR
    this.productoDAO = pDAO;
    this.categoriaDAO = cDAO;
    this.proveedorDAO = provDAO;
    this.entradaDAO = eDAO;
    this.clienteDAO = cliDAO;
    this.salidaDAO = sDAO;
    this.rolDAO = rDAO;
    this.notifier = notifier;
    
    this.vista.getBtnLogin().addActionListener(e -> autenticar());
    this.vista.getRootPane().setDefaultButton(vista.getBtnLogin());
    
    }
    
    private void autenticar() {
        String user = vista.getUsuario().trim();
        String pass = vista.getContrasena();
        
        System.out.println("Intentando login con: [" + user + "] y [" + pass + "]");
        
        Usuario usuario = usuarioDAO.login(user, pass);
        
        System.out.println("Resultado del DAO: " + (usuario == null ? "NULL" : usuario.getNombre()));
        
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Usuario y contraseña son requeridos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (usuario != null) {
        // Crea la Vista y le pasa los DAOs
        DashboardWindow dashboardView = new DashboardWindow(usuario, productoDAO, categoriaDAO, 
                                                proveedorDAO, entradaDAO, clienteDAO, 
                                                salidaDAO, usuarioDAO, rolDAO, notifier);

        new DashboardController(dashboardView, usuario, 
        productoDAO, categoriaDAO, proveedorDAO, entradaDAO, 
        clienteDAO, salidaDAO, usuarioDAO, rolDAO, notifier);

        NavigationManager.navigateTo(vista, dashboardView);
        } else {
            
            JOptionPane.showMessageDialog(vista, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
