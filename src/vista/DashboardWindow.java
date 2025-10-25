/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package vista;

/**
 *
 * @author Enrique Zegarra
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import modelo.Usuario;
import utils.StyleManager;
import javax.swing.BorderFactory;
import java.awt.Dimension;

public class DashboardWindow extends JFrame {

    // Paneles de la estructura
    private JPanel panelMenu;
    private JPanel panelHeader;
    private JPanel panelContent;
    
    // Componentes
    private JLabel lblSaludo;
    private JButton btnCerrarSesion;
    private JButton btnNavHome;
    private JButton btnNavInventario;
    private JButton btnNavEntradas;
    private JButton btnNavSalidas;
    // (Añadiremos más botones de menú luego)
    
    private CardLayout cardLayout; 

    public DashboardWindow(Usuario usuario) {
        initializeComponents(usuario);
        setupLayout();
        configureWindow();
    }
    
    private void initializeComponents(Usuario usuario) {
        // --- Header ---
        lblSaludo = StyleManager.createLabel("Hola, " + usuario.getNombre() + " (Perfil: " + usuario.getUsuario() + ")");
        btnCerrarSesion = StyleManager.createPrimaryButton("Cerrar Sesión");
        // (Estilo rojo para 'Cerrar Sesión')
        btnCerrarSesion.setBackground(new Color(211, 47, 47));

        // --- Menú ---
        btnNavHome = new JButton("Dashboard");
        btnNavInventario = new JButton("Inventario");
        btnNavEntradas = new JButton("Entradas");
        btnNavSalidas = new JButton("Salidas");
        
        // --- Contenido ---
        cardLayout = new CardLayout();
        panelContent = new JPanel(cardLayout);
    }
    
    private void setupLayout() {
        // --- Configuración JFrame ---
        setLayout(new BorderLayout());
        
        // --- 1. Header (NORTH) ---
        panelHeader = new JPanel(new BorderLayout(10, 10)); // HGap, VGap
        panelHeader.setBackground(Color.WHITE);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelHeader.add(lblSaludo, BorderLayout.CENTER);
        panelHeader.add(btnCerrarSesion, BorderLayout.EAST);
        add(panelHeader, BorderLayout.NORTH);

        // --- 2. Menú (WEST) ---
        panelMenu = new JPanel(new GridLayout(8, 1, 0, 10)); // 8 filas, 1 col, 0 HGap, 10 VGap
        panelMenu.setBackground(new Color(51, 51, 51)); // Color oscuro
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // (Aplicamos estilo a los botones del menú)
        JButton[] menuButtons = {btnNavHome, btnNavInventario, btnNavEntradas, btnNavSalidas};
        for (JButton btn : menuButtons) {
            btn.setBackground(new Color(51, 51, 51));
            btn.setForeground(Color.WHITE);
            btn.setFont(StyleManager.FONT_BOTON);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            panelMenu.add(btn);
        }
        add(panelMenu, BorderLayout.WEST);

        // --- 3. Contenido (CENTER) ---
        panelContent.setBackground(StyleManager.GRIS_CLARO);
        // Añadimos el panel de bienvenida como 'home'
        panelContent.add(new HomePanel(), "home"); 
 
        // --- AÑADIR ESTE BLOQUE ---
        // Crear e instanciar el módulo de Inventario
        vista.InventarioPanel panelInventario = new vista.InventarioPanel();
        dao.IProductoDAO productoDAO = new dao.ProductoDAOImpl();
        new controlador.InventarioController(panelInventario, productoDAO);
        // Añadirlo al CardLayout
        panelContent.add(panelInventario, "inventario");
        
        // -------------------------
        // (Aquí añadiremos los otros paneles: "inventario", "entradas", etc.)
        
        add(panelContent, BorderLayout.CENTER);
        
        // Mostramos 'home' por defecto
        cardLayout.show(panelContent, "home");
    }

    private void configureWindow() {
        setTitle("MABARUC TECNOLOGY - Sistema de Gestión");
        setMinimumSize(new Dimension(1024, 768));
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar
    }
    
    // --- Getters para el Controlador ---
    public CardLayout getCardLayout() { return cardLayout; }
    public JPanel getPanelContent() { return panelContent; }
    public JButton getBtnCerrarSesion() { return btnCerrarSesion; }
    public JButton getBtnNavHome() { return btnNavHome; }
    public JButton getBtnNavInventario() { return btnNavInventario; }
    public JButton getBtnNavEntradas() { return btnNavEntradas; }
    public JButton getBtnNavSalidas() { return btnNavSalidas; }
}
