/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.StyleManager;
import javax.swing.SwingConstants;


/**
 *
 * @author Enrique Zegarra
 */
public class InventarioPanel extends JPanel {

    // Componentes
    private JTextField txtBuscarProducto;
    private JCheckBox chkStockBajo;
    private JTable tblProductos;
    private DefaultTableModel modeloTabla;
    private JButton btnEliminar;
    private JPanel panelPaginacion;
    private static final int COLUMNA_PRECIO = 3;
    private JButton btnAnterior;
    private JButton btnSiguiente;
    private JLabel lblPaginacion;

    public InventarioPanel() {
        // Configuración del Panel Principal
        setLayout(new BorderLayout(10, 10));
        setBackground(StyleManager.GRIS_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Paneles de Estructura
        JPanel panelNorte = new JPanel(new BorderLayout(10, 10));
        panelNorte.setOpaque(false); // Transparente
        
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelFiltros.setOpaque(false);

        // Inicializa Componentes
        chkStockBajo = new JCheckBox("Productos con stock bajo");
        btnEliminar = StyleManager.createPrimaryButton("Eliminar Producto");
        btnEliminar.setBackground(new java.awt.Color(211, 47, 47));
        btnEliminar.setVisible(false);
        
        btnAnterior = new JButton("<< Anterior");
        btnSiguiente = new JButton("Siguiente >>");
        lblPaginacion = StyleManager.createLabel("Página 1 de 1");
        JLabel lblTitulo = StyleManager.createLabel("Gestión de Productos");
        lblTitulo.setFont(StyleManager.FONT_TITULO);
        
        txtBuscarProducto = StyleManager.createStyledTextField();
        txtBuscarProducto.putClientProperty("JTextField.placeholderText", "Buscar por nombre o categoría...");
        
        chkStockBajo = new JCheckBox("Productos con stock bajo");
        
        // Configura la Tabla
        String[] columnas = {"ID", "NOMBRE", "CATEGORÍA", "PRECIO", "STOCK ACTUAL", "STOCK MÍNIMO"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tblProductos = new JTable(modeloTabla);
        tblProductos.setRowHeight(25);
        tblProductos.getTableHeader().setFont(StyleManager.FONT_BOTON);
        
        JScrollPane scrollPane = new JScrollPane(tblProductos);
        
        InventarioTableCellRenderer renderer = new InventarioTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblProductos.setDefaultRenderer(Object.class, renderer);
        tblProductos.setDefaultRenderer(Number.class, renderer);
        tblProductos.getColumnModel().getColumn(COLUMNA_PRECIO).setCellRenderer(renderer);

        // Ensamblar Paneles
        panelFiltros.add(txtBuscarProducto);
        panelFiltros.add(chkStockBajo);
        
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelFiltros, BorderLayout.CENTER);
        
        JPanel panelAcciones = new JPanel(new BorderLayout());
        panelAcciones.setOpaque(false);

        JPanel panelBotonesAccion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotonesAccion.setOpaque(false);
        panelBotonesAccion.add(btnEliminar);
        // (Añadiremos el botón Editar aquí luego)

        JPanel panelPaginador = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelPaginador.setOpaque(false);
        panelPaginador.add(btnAnterior);
        panelPaginador.add(lblPaginacion);
        panelPaginador.add(btnSiguiente);

        panelAcciones.add(panelBotonesAccion, BorderLayout.WEST);
        panelAcciones.add(panelPaginador, BorderLayout.EAST);
        
        

        
        add(panelNorte, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);
        
        
    }
    
    
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTable getTablaProductos() { return tblProductos; }
    public JTextField getTxtBuscarProducto() { return txtBuscarProducto; }
    public JCheckBox getChkStockBajo() { return chkStockBajo; }
    public JButton getBtnEliminar() { return btnEliminar;}
    public JButton getBtnAnterior() { return btnAnterior; }
    public JButton getBtnSiguiente() { return btnSiguiente; }
    public JLabel getLblPaginacion() { return lblPaginacion; }
}
