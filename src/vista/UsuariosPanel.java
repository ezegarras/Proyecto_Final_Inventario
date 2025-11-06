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

public class UsuariosPanel extends JPanel {

    private JTable tblUsuarios;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;

    public UsuariosPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(StyleManager.GRIS_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = StyleManager.createLabel("Gestión de Usuarios");
        lblTitulo.setFont(StyleManager.FONT_TITULO);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAcciones.setOpaque(false);
        btnAgregar = StyleManager.createPrimaryButton("Agregar Usuario");
        btnEditar = new JButton("Editar Seleccionado");
        btnEliminar = new JButton("Eliminar Seleccionado");
        panelAcciones.add(btnAgregar);
        panelAcciones.add(btnEditar);
        panelAcciones.add(btnEliminar);
        
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelAcciones, BorderLayout.SOUTH);

        // La contraseña NO se muestra en la tabla por seguridad
        String[] columnas = {"ID", "NOMBRE", "USUARIO (LOGIN)", "ROL"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblUsuarios = new JTable(modeloTabla);
        tblUsuarios.setRowHeight(25);
        tblUsuarios.getTableHeader().setFont(StyleManager.FONT_BOTON);
        tblUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(tblUsuarios), BorderLayout.CENTER);
    }

    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTable getTablaUsuarios() { return tblUsuarios; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnEditar() { return btnEditar; }
    public JButton getBtnEliminar() { return btnEliminar; }
}
