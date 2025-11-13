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
public class ReportesPanel extends JPanel {

    private JComboBox<String> cmbTipoReporte;
    private JTable tblReporte;
    private DefaultTableModel modeloTabla;
    private JButton btnGenerarReporte; // (Lo usaremos después)

    public ReportesPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(StyleManager.GRIS_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        
        JLabel lblTitulo = StyleManager.createLabel("Módulo de reportes de ventas de la empresa");
        lblTitulo.setFont(StyleManager.FONT_TITULO);

        
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setOpaque(false);
        
        panelFiltros.add(StyleManager.createLabel("Tipo de Reporte:"));
        
        
        String[] tipos = {
            "- Seleccione un reporte -", 
            "Productos con Stock bajo",
            "Productos más vendidos"
        
        };
        cmbTipoReporte = new JComboBox<>(tipos);
        panelFiltros.add(cmbTipoReporte);
        

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelFiltros, BorderLayout.SOUTH);
 
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblReporte = new JTable(modeloTabla);
        tblReporte.setRowHeight(25);
        tblReporte.getTableHeader().setFont(StyleManager.FONT_BOTON);
        JScrollPane scrollPane = new JScrollPane(tblReporte);

        
        add(panelNorte, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JComboBox<String> getCmbTipoReporte() { return cmbTipoReporte; }
    public JTable getTblReporte() { return tblReporte; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}
