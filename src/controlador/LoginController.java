/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.IUsuarioDAO;
import dao.UsuarioDAOImpl;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.LoginWindow;
import modelo.Usuario;
import utils.NavigationManager;
import vista.DashboardWindow;

/**
 *
 * @author Enrique Zegarra
 */
public class LoginController {
    
private final LoginWindow vista;
    private final IUsuarioDAO dao;

    public LoginController(LoginWindow vista) {
        this.vista = vista;
        // Inyectamos la dependencia (SOLID)
        this.dao = new UsuarioDAOImpl(); 
        
        // 1. Añadimos el ActionListener al botón
        this.vista.getBtnLogin().addActionListener(e -> autenticar());
        
        // 2. 'Enter' para login
        this.vista.getRootPane().setDefaultButton(vista.getBtnLogin());
    }
    
    private void autenticar() {
        String user = vista.getUsuario().trim();
        String pass = vista.getContrasena();
        
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Usuario y contraseña son requeridos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Usuario usuario = dao.login(user, pass);
        
        if (usuario != null) {  
            // ¡Login Exitoso!
            // 1. Crea la nueva Vista (Dashboard)
            DashboardWindow dashboardView = new DashboardWindow(usuario);
            
            // 2. Crea el Controlador del Dashboard
            new DashboardController(dashboardView, usuario);
            
            // 3. Usa el NavigationManager para cambiar de ventana
            NavigationManager.navigateTo(vista, dashboardView);
            
        } else {
            // Error
            JOptionPane.showMessageDialog(vista, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
