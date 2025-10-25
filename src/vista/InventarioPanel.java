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
    
    // Panel de paginación (a implementar en el futuro)
    private JPanel panelPaginacion;

    public InventarioPanel() {
        // 1. Configuración del Panel Principal
        setLayout(new BorderLayout(10, 10));
        setBackground(StyleManager.GRIS_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 2. Crear Paneles de Estructura
        JPanel panelNorte = new JPanel(new BorderLayout(10, 10));
        panelNorte.setOpaque(false); // Transparente
        
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelFiltros.setOpaque(false);

        // 3. Inicializar Componentes
        chkStockBajo = new JCheckBox("Productos con stock bajo");
    btnEliminar = StyleManager.createPrimaryButton("Eliminar Producto");
    btnEliminar.setBackground(new java.awt.Color(211, 47, 47));
        JLabel lblTitulo = StyleManager.createLabel("Gestión de Productos");
        lblTitulo.setFont(StyleManager.FONT_TITULO);
        
        txtBuscarProducto = StyleManager.createStyledTextField();
        txtBuscarProducto.putClientProperty("JTextField.placeholderText", "Buscar por nombre o categoría...");
        
        chkStockBajo = new JCheckBox("Productos con stock bajo");
        
        // 4. Configurar la Tabla
        String[] columnas = {"ID", "NOMBRE", "CATEGORÍA", "PRECIO", "STOCK ACTUAL", "STOCK MÍNIMO"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            // Hacemos que la tabla no sea editable directamente
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tblProductos = new JTable(modeloTabla);
        tblProductos.setRowHeight(25);
        tblProductos.getTableHeader().setFont(StyleManager.FONT_BOTON);
        
        JScrollPane scrollPane = new JScrollPane(tblProductos);

        // 5. Ensamblar Paneles
        panelFiltros.add(txtBuscarProducto);
        panelFiltros.add(chkStockBajo);
        
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelFiltros, BorderLayout.CENTER);
        
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAcciones.setOpaque(false);
        panelAcciones.add(btnEliminar);
        
        // (El botón 'Agregar Producto' se omite intencionalmente según tu lógica)

        // 6. Ensamblar Vista Principal
        add(panelNorte, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);
        
        // (Aquí iría el panelPaginacion en BorderLayout.SOUTH)
    }
    
    // --- Getters para el Controlador ---
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTable getTablaProductos() { return tblProductos; }
    public JTextField getTxtBuscarProducto() { return txtBuscarProducto; }
    public JCheckBox getChkStockBajo() { return chkStockBajo; }
    public JButton getBtnEliminar() { return btnEliminar;}
}
