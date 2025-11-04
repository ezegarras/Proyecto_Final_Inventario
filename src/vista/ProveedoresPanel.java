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

public class ProveedoresPanel extends JPanel    {
    
    private JTable tblProveedores;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;

    public ProveedoresPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(StyleManager.GRIS_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = StyleManager.createLabel("Gestión de Proveedores");
        lblTitulo.setFont(StyleManager.FONT_TITULO);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAcciones.setOpaque(false);
        btnAgregar = StyleManager.createPrimaryButton("Agregar Proveedor");
        btnEditar = new JButton("Editar Seleccionado");
        btnEliminar = new JButton("Eliminar Seleccionado");
        
        panelAcciones.add(btnAgregar);
        panelAcciones.add(btnEditar);
        panelAcciones.add(btnEliminar);
        
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelAcciones, BorderLayout.SOUTH);

        String[] columnas = {"ID", "RAZÓN SOCIAL", "RUC", "CELULAR", "CORREO", "DIRECCIÓN"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblProveedores = new JTable(modeloTabla);
        tblProveedores.setRowHeight(25);
        tblProveedores.getTableHeader().setFont(StyleManager.FONT_BOTON);
        tblProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tblProveedores);

        add(panelNorte, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTable getTablaProveedores() { return tblProveedores; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnEditar() { return btnEditar; }
    public JButton getBtnEliminar() { return btnEliminar; }
}
