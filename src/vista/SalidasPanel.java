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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.StyleManager;

public class SalidasPanel extends JPanel {

    // Componentes del Formulario de Venta ---
    private JTextField txtBuscarClienteDNI;
    private JButton btnBuscarCliente;
    private JLabel lblNombreCliente;
    private JTextField txtBuscarProducto; 
    private JSpinner spinCantidadProducto;
    private JButton btnAgregarAlCarrito;
    private JTable tblCarrito;
    private DefaultTableModel modeloTablaCarrito;
    private JLabel lblTotalVenta;
    private JButton btnRegistrarVenta;
    private JTable tblHistorialVentas;
    private DefaultTableModel modeloTablaHistorial;

    public SalidasPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(StyleManager.GRIS_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        
        JPanel panelNorte = crearPanelFormularioVenta();
        JPanel panelSur = crearPanelHistorial();
        
       
        add(panelNorte, BorderLayout.NORTH);
        add(panelSur, BorderLayout.CENTER); 
    }

    private JPanel crearPanelFormularioVenta() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Registrar Nueva Venta"));

        
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        
        
        JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCliente.add(StyleManager.createLabel("Buscar Cliente (DNI):"));
        txtBuscarClienteDNI = StyleManager.createStyledTextField();
        txtBuscarClienteDNI.setPreferredSize(new Dimension(100, 30));
        btnBuscarCliente = new JButton("Buscar");
        lblNombreCliente = StyleManager.createLabel("Cliente: (No seleccionado)");
        lblNombreCliente.setFont(StyleManager.FONT_BOTON);
        
        panelCliente.add(txtBuscarClienteDNI);
        panelCliente.add(btnBuscarCliente);
        panelCliente.add(lblNombreCliente);
        
        
        JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAgregar.add(StyleManager.createLabel("Producto:"));
        txtBuscarProducto = StyleManager.createStyledTextField();
        txtBuscarProducto.setPreferredSize(new Dimension(150, 30));
        txtBuscarProducto.putClientProperty("JTextField.placeholderText", "Buscar producto...");
        
        panelAgregar.add(StyleManager.createLabel("Cant:"));
        spinCantidadProducto = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        btnAgregarAlCarrito = new JButton("Añadir al Carrito");
        
        panelAgregar.add(spinCantidadProducto);
        panelAgregar.add(btnAgregarAlCarrito);

        // Zona Tabla Carrito
        String[] colCarrito = {"ID", "PRODUCTO", "CANT", "P. UNIT", "IMPORTE"};
        modeloTablaCarrito = new DefaultTableModel(colCarrito, 0);
        tblCarrito = new JTable(modeloTablaCarrito);
        JScrollPane scrollCarrito = new JScrollPane(tblCarrito);
        scrollCarrito.setPreferredSize(new Dimension(400, 150));
        
        panelIzquierdo.add(panelCliente, BorderLayout.NORTH);
        panelIzquierdo.add(scrollCarrito, BorderLayout.CENTER);
        panelIzquierdo.add(panelAgregar, BorderLayout.SOUTH);
        
        
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        lblTotalVenta = StyleManager.createLabel("Total: S/ 0.00");
        lblTotalVenta.setFont(StyleManager.FONT_TITULO);
        btnRegistrarVenta = StyleManager.createPrimaryButton("Registrar Venta");
        btnRegistrarVenta.setPreferredSize(new Dimension(200, 50));
        
        gbc.gridy = 0; panelDerecho.add(lblTotalVenta, gbc);
        gbc.gridy = 1; panelDerecho.add(btnRegistrarVenta, gbc);

        
        panel.add(panelIzquierdo, BorderLayout.CENTER);
        panel.add(panelDerecho, BorderLayout.EAST);
        
        return panel;
    }

    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Historial de Ventas"));
        
        String[] colHistorial = {"# FACTURA", "CLIENTE", "FECHA", "TOTAL"};
        modeloTablaHistorial = new DefaultTableModel(colHistorial, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblHistorialVentas = new JTable(modeloTablaHistorial);
        
        panel.add(new JScrollPane(tblHistorialVentas), BorderLayout.CENTER);
        return panel;
    }

    
    public JTextField getTxtBuscarClienteDNI() { return txtBuscarClienteDNI; }
    public JButton getBtnBuscarCliente() { return btnBuscarCliente; }
    public JLabel getLblNombreCliente() { return lblNombreCliente; }
    public JTextField getTxtBuscarProducto() { return txtBuscarProducto; }
    public JSpinner getSpinCantidadProducto() { return spinCantidadProducto; }
    public JButton getBtnAgregarAlCarrito() { return btnAgregarAlCarrito; }
    public JTable getTblCarrito() { return tblCarrito; }
    public DefaultTableModel getModeloTablaCarrito() { return modeloTablaCarrito; }
    public JLabel getLblTotalVenta() { return lblTotalVenta; }
    public JButton getBtnRegistrarVenta() { return btnRegistrarVenta; }
    public DefaultTableModel getModeloTablaHistorial() { return modeloTablaHistorial; }
}
