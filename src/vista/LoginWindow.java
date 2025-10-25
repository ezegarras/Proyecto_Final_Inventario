/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import utils.StyleManager;

/**
 *
 * @author Enrique Zegarra
 */
public class LoginWindow extends JFrame {
    
    // Componentes (privados)
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JCheckBox chkRecordar;
    private JButton btnLogin;

    public LoginWindow() {
        initializeComponents();
        setupLayout();
        configureWindow();
    }
    
    private void initializeComponents() {
        // Usamos el StyleManager para crear componentes
        txtUsuario = StyleManager.createStyledTextField();
        txtContrasena = StyleManager.createStyledPasswordField();
        btnLogin = StyleManager.createPrimaryButton("Iniciar Sesión");
        chkRecordar = new JCheckBox("Recordarme");
        
        // Placeholders (requieren FlatLaf)
        txtUsuario.putClientProperty("JTextField.placeholderText", "Ingrese su nombre de usuario");
        txtContrasena.putClientProperty("JTextField.placeholderText", "Escriba su clave");
    }

    private void setupLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(StyleManager.GRIS_CLARO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20); // Padding
        
        // Fila 0: Título (Simulado)
        JLabel lblTitulo = new JLabel("Iniciar Sesión");
        lblTitulo.setFont(StyleManager.FONT_TITULO);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa 2 columnas
        panel.add(lblTitulo, gbc);

        // Fila 1: Label Usuario
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(StyleManager.createLabel("Nombre de Usuario"), gbc);

        // Fila 2: Campo Usuario
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtUsuario, gbc);
        
        // Fila 3: Label Contraseña
        gbc.gridy = 3;
        panel.add(StyleManager.createLabel("Contraseña"), gbc);
        
        // Fila 4: Campo Contraseña
        gbc.gridy = 4;
        panel.add(txtContrasena, gbc);
        
        // Fila 5: Recordarme
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(chkRecordar, gbc);
        
        // Fila 6: Botón Login
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);
        
        // Añadir panel al JFrame
        this.add(panel);
    }
    
    private void configureWindow() {
        setTitle("MABARUC TECNOLOGY - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack(); // Ajusta el tamaño al contenido
        setLocationRelativeTo(null); // Centra
        setResizable(false);
    }
    
    // --- GETTERS (Para el Controlador) ---
    public String getUsuario() { return txtUsuario.getText(); }
    public String getContrasena() { return new String(txtContrasena.getPassword()); }
    public boolean isRecordar() { return chkRecordar.isSelected(); }
    public JButton getBtnLogin() { return btnLogin; }
}