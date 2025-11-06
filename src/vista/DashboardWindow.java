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
import dao.IClienteDAO;
import dao.ClienteDAOImpl;
import dao.IEntradaDAO;
import dao.IProveedorDAO;
import dao.EntradaDAOImpl;
import dao.ProveedorDAOImpl;
import dao.IRolDAO;
import dao.RolDAOImpl;

public class DashboardWindow extends JFrame {

    //Estructura
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
    private JButton btnNavProveedores;
    private JButton btnNavClientes;
    private JButton btnNavReportes; 
    private JButton btnNavUsuarios;
    private Usuario usuario;
    private CardLayout cardLayout; 

    public DashboardWindow(Usuario usuario) {
        this.usuario = usuario;
        initializeComponents(usuario);
        setupLayout();
        configureWindow();
    }
    
    private void initializeComponents(Usuario usuario) {
        // cabeceras
        lblSaludo = StyleManager.createLabel("Hola, " + usuario.getNombre() + " (Perfil: " + usuario.getUsuario() + ")");
        btnCerrarSesion = StyleManager.createPrimaryButton("Cerrar Sesión");
        // (Estilo rojo para 'Cerrar Sesión')
        btnCerrarSesion.setBackground(new Color(211, 47, 47));

        // Menu
        btnNavHome = new JButton("Dashboard");
        btnNavInventario = new JButton("Inventario");
        btnNavEntradas = new JButton("Entradas");
        btnNavSalidas = new JButton("Salidas");
        btnNavProveedores = new JButton("Proveedores");
        btnNavClientes = new JButton("Clientes");
        btnNavReportes = new JButton("Reportes");
        btnNavUsuarios = new JButton("Usuarios");
        
        //Contenido
        cardLayout = new CardLayout();
        panelContent = new JPanel(cardLayout);
    }
    
    private void setupLayout() {
        
        setLayout(new BorderLayout());
        
        // Header
        panelHeader = new JPanel(new BorderLayout(10, 10));
        panelHeader.setBackground(Color.WHITE);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelHeader.add(lblSaludo, BorderLayout.CENTER);
        panelHeader.add(btnCerrarSesion, BorderLayout.EAST);
        add(panelHeader, BorderLayout.NORTH);

        // Menú
        panelMenu = new JPanel(new GridLayout(8, 1, 0, 10)); 
        panelMenu.setBackground(new Color(51, 51, 51));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        
        JButton[] menuButtons = {btnNavHome, btnNavInventario, btnNavEntradas, btnNavSalidas, btnNavClientes, btnNavProveedores, btnNavReportes, btnNavUsuarios};
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

        // 
        panelContent.setBackground(StyleManager.GRIS_CLARO);
        
        panelContent.add(new HomePanel(), "home"); 
 
        // ------------------------
        
        vista.InventarioPanel panelInventario = new vista.InventarioPanel();
        dao.IProductoDAO productoDAO = new dao.ProductoDAOImpl();
        new controlador.InventarioController(panelInventario, productoDAO, usuario);
        panelContent.add(panelInventario, "inventario");
        
        vista.EntradasPanel panelEntradas = new vista.EntradasPanel();
        dao.IEntradaDAO entradaDAO = new dao.EntradaDAOImpl();
        dao.IProveedorDAO proveedorDAO = new dao.ProveedorDAOImpl();
        dao.ICategoriaDAO categoriaDAO = new dao.CategoriaDAOImpl();
        new controlador.EntradasController(panelEntradas, entradaDAO, productoDAO, proveedorDAO, categoriaDAO);
        panelContent.add(panelEntradas, "entradas");
        
        vista.ProveedoresPanel panelProveedores = new vista.ProveedoresPanel();
        dao.IProveedorDAO daoProv = new dao.ProveedorDAOImpl();
        new controlador.ProveedoresController(panelProveedores, daoProv, usuario);
        panelContent.add(panelProveedores, "proveedores");
        
        vista.ClientesPanel panelClientes = new vista.ClientesPanel();
        dao.IClienteDAO daoCli = new dao.ClienteDAOImpl();
        new controlador.ClientesController(panelClientes, daoCli, usuario);
        panelContent.add(panelClientes, "clientes");
        
        // módulo de Salidas
        vista.SalidasPanel panelSalidas = new vista.SalidasPanel();
        dao.IClienteDAO daoCliSalida = new dao.ClienteDAOImpl();
        dao.IProductoDAO daoProdSalida = new dao.ProductoDAOImpl();
        dao.ISalidaDAO daoSalida = new dao.SalidaDAOImpl();
        new controlador.SalidasController(panelSalidas, daoCliSalida, daoProdSalida, daoSalida);
        panelContent.add(panelSalidas, "salidas");
        
        //Modulo usuario
        vista.UsuariosPanel panelUsuarios = new vista.UsuariosPanel();
        dao.IUsuarioDAO daoUser = new dao.UsuarioDAOImpl();
        dao.IRolDAO daoRol = new dao.RolDAOImpl();
        new controlador.UsuariosController(panelUsuarios, daoUser, daoRol);
        panelContent.add(panelUsuarios, "usuarios");
        
        add(panelContent, BorderLayout.CENTER);
        
        cardLayout.show(panelContent, "home");
        
        
        
    }

    private void configureWindow() {
        setTitle("MABARUC TECNOLOGY - Sistema de Gestión");
        setMinimumSize(new Dimension(1024, 768));
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
    }
    

    public CardLayout getCardLayout() { return cardLayout; }
    public JPanel getPanelContent() { return panelContent; }
    public JButton getBtnCerrarSesion() { return btnCerrarSesion; }
    public JButton getBtnNavHome() { return btnNavHome; }
    public JButton getBtnNavInventario() { return btnNavInventario; }
    public JButton getBtnNavEntradas() { return btnNavEntradas; }
    public JButton getBtnNavSalidas() { return btnNavSalidas; }
    public JButton getBtnNavProveedores() { return btnNavProveedores; }
    public JButton getBtnNavClientes() { return btnNavClientes; }
    public JButton getBtnNavReportes() { return btnNavReportes; }
    public JButton getBtnNavUsuarios() { return btnNavUsuarios; }
}
