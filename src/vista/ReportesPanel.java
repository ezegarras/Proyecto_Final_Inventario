/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
    private JButton btnGenerarReporte;
    private JPanel panelContenedorGrafico;

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
            "Productos más vendidos",
            "Ventas por Cliente"
        
        };
        cmbTipoReporte = new JComboBox<>(tipos);
        panelFiltros.add(cmbTipoReporte);
        

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelFiltros, BorderLayout.SOUTH);
        
        JPanel panelContenido = new JPanel(new GridLayout(1, 2, 10, 10)); // 1 fila, 2 cols
        panelContenido.setOpaque(false);
 
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblReporte = new JTable(modeloTabla);
        tblReporte.setRowHeight(25);
        tblReporte.getTableHeader().setFont(StyleManager.FONT_BOTON);
        JScrollPane scrollPane = new JScrollPane(tblReporte);
        
        panelContenedorGrafico = new JPanel(new BorderLayout());
        panelContenedorGrafico.setOpaque(false);
        panelContenedorGrafico.setBorder(BorderFactory.createTitledBorder("Visualización"));
        panelContenedorGrafico.add(new JLabel("(Gráfico se mostrará aquí)", SwingConstants.CENTER));

        panelContenido.add(scrollPane);
        panelContenido.add(panelContenedorGrafico);
        
        
        add(panelNorte, BorderLayout.NORTH);
        add(panelContenido, BorderLayout.CENTER);
    }
    
    public void setGrafico(JPanel graficoPanel) {
    panelContenedorGrafico.removeAll();
    if (graficoPanel != null) {
        panelContenedorGrafico.add(graficoPanel, BorderLayout.CENTER);
    } else {
        // Mostrar texto por defecto si no hay gráfico
        panelContenedorGrafico.add(new JLabel("(Gráfico no disponible)", SwingConstants.CENTER));
    }
    panelContenedorGrafico.revalidate();
    panelContenedorGrafico.repaint();
    }

    public JComboBox<String> getCmbTipoReporte() { return cmbTipoReporte; }
    public JTable getTblReporte() { return tblReporte; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}
