/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import modelo.Proveedor;
import utils.StyleManager;

/**
 *
 * @author Enrique Zegarra
 */

public class EntradasPanel extends JPanel {

    // --- Componentes del Formulario (Izquierda) ---
    private JComboBox<Producto> cmbProducto;
    private JButton btnNuevoProducto;
    private JSpinner spinCantidad;
    private JSpinner spinFecha; // Usaremos un JSpinner para la fecha
    private JComboBox<Proveedor> cmbProveedor;
    private JButton btnRegistrarEntrada;

    // --- Componentes del Historial (Derecha) ---
    private JTable tblHistorial;
    private DefaultTableModel modeloTablaHistorial;

    public EntradasPanel() {
        // 1. Configuración del Panel Principal
        setLayout(new BorderLayout(20, 0)); // HGap de 20 entre formulario e historial
        setBackground(StyleManager.GRIS_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 2. Crear los dos paneles principales
        JPanel panelFormulario = crearPanelFormulario();
        JPanel panelHistorial = crearPanelHistorial();
        
        // Añadimos un título general
        JLabel lblTitulo = StyleManager.createLabel("Registro de Entradas de Productos");
        lblTitulo.setFont(StyleManager.FONT_TITULO);

        // 3. Ensamblar Vista Principal
        add(lblTitulo, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.WEST);
        add(panelHistorial, BorderLayout.CENTER);
    }

    /**
     * Crea el panel del formulario a la izquierda.
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Fila 0: Título Formulario
        JLabel lblFormTitulo = StyleManager.createLabel("Formulario de Entrada");
        lblFormTitulo.setFont(StyleManager.FONT_BOTON); // Fuente más grande
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblFormTitulo, gbc);

        // Fila 1: Label Producto
        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(StyleManager.createLabel("Seleccionar Producto:"), gbc);
        
        // Fila 2: ComboBox Producto (en un panel con el botón '+')
        cmbProducto = new JComboBox<>();
        btnNuevoProducto = new JButton("+");
        JPanel panelProducto = new JPanel(new BorderLayout(5, 0));
        panelProducto.add(cmbProducto, BorderLayout.CENTER);
        panelProducto.add(btnNuevoProducto, BorderLayout.EAST);
        
        gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(panelProducto, gbc);

        // Fila 3: Label Cantidad
        gbc.gridy = 3; gbc.gridwidth = 1;
        panel.add(StyleManager.createLabel("Cantidad:"), gbc);
        gbc.gridy = 4; gbc.gridwidth = 2;
        spinCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        panel.add(spinCantidad, gbc);

        // Fila 5: Label Fecha
        gbc.gridy = 5; gbc.gridwidth = 1;
        panel.add(StyleManager.createLabel("Fecha:"), gbc);
        gbc.gridy = 6; gbc.gridwidth = 2;
        // Usamos un JSpinner para la fecha. Se puede reemplazar por JDatePicker (librería externa)
        spinFecha = new JSpinner(new SpinnerDateModel());
        spinFecha.setEditor(new JSpinner.DateEditor(spinFecha, "dd/MM/yyyy"));
        panel.add(spinFecha, gbc);
        
        // Fila 7: Label Proveedor
        gbc.gridy = 7; gbc.gridwidth = 1;
        panel.add(StyleManager.createLabel("Proveedor:"), gbc);
        gbc.gridy = 8; gbc.gridwidth = 2;
        cmbProveedor = new JComboBox<>();
        panel.add(cmbProveedor, gbc);
        
        // Fila 9: Botón Registrar
        gbc.gridy = 9; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        btnRegistrarEntrada = StyleManager.createPrimaryButton("Registrar Entrada");
        panel.add(btnRegistrarEntrada, gbc);
        
        return panel;
    }
    
    /**
     * Crea el panel del historial a la derecha.
     */
    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout(0, 10)); // VGap 10
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblHistorial = StyleManager.createLabel("Historial de Entradas Recientes");
        lblHistorial.setFont(StyleManager.FONT_BOTON);
        
        // Configurar la Tabla
        String[] columnas = {"PRODUCTO", "CANTIDAD", "FECHA", "PROVEEDOR"};
        modeloTablaHistorial = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblHistorial = new JTable(modeloTablaHistorial);
        tblHistorial.setRowHeight(25);
        tblHistorial.getTableHeader().setFont(StyleManager.FONT_BOTON);
        
        panel.add(lblHistorial, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblHistorial), BorderLayout.CENTER);
        
        return panel;
    }

    // --- Getters para el Controlador ---
    public JComboBox<Producto> getCmbProducto() { return cmbProducto; }
    public JComboBox<Proveedor> getCmbProveedor() { return cmbProveedor; }
    public JButton getBtnNuevoProducto() { return btnNuevoProducto; }
    public JButton getBtnRegistrarEntrada() { return btnRegistrarEntrada; }
    public JSpinner getSpinCantidad() { return spinCantidad; }
    public JSpinner getSpinFecha() { return spinFecha; }
    public DefaultTableModel getModeloTablaHistorial() { return modeloTablaHistorial; }

    // --- Métodos para el Controlador (Renderers) ---
    // (Estos son necesarios para que el JComboBox muestre el nombre del Producto)
    
    public void setProductoCellRenderer() {
        cmbProducto.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Producto) {
                    setText(((Producto) value).getNombre());
                } else {
                    setText("Seleccione un producto...");
                }
                return this;
            }
        });
    }

    public void setProveedorCellRenderer() {
        // El toString() del Proveedor ya hace esto, pero es buena práctica
        // ser explícito si el toString() cambia.
        cmbProveedor.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Proveedor) {
                    setText(value.toString()); // Usa el toString() personalizado
                } else {
                    setText("Seleccione un proveedor...");
                }
                return this;
            }
        });
    }
}
