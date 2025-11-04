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
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.StyleManager;

public class ClientesPanel extends JPanel {

    private JTable tblClientes;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;

    public ClientesPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(StyleManager.GRIS_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = StyleManager.createLabel("Gestión de Clientes");
        lblTitulo.setFont(StyleManager.FONT_TITULO);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAcciones.setOpaque(false);
        btnAgregar = StyleManager.createPrimaryButton("Agregar Cliente");
        btnEditar = new JButton("Editar Seleccionado");
        btnEliminar = new JButton("Eliminar Seleccionado");
        
        panelAcciones.add(btnAgregar);
        panelAcciones.add(btnEditar);
        panelAcciones.add(btnEliminar);
        
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelAcciones, BorderLayout.SOUTH);

        String[] columnas = {"ID", "DNI", "NOMBRE", "CELULAR", "CORREO", "DIRECCIÓN", "EDAD", "SEXO"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblClientes = new JTable(modeloTabla);
        tblClientes.setRowHeight(25);
        tblClientes.getTableHeader().setFont(StyleManager.FONT_BOTON);
        tblClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(tblClientes), BorderLayout.CENTER);
    }

    // --- Getters ---
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTable getTablaClientes() { return tblClientes; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnEditar() { return btnEditar; }
    public JButton getBtnEliminar() { return btnEliminar; }
}
