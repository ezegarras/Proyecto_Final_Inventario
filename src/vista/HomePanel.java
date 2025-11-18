/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import utils.StyleManager;
import utils.GraficoService;

/**
 *
 * @author Enrique Zegarra
 */

//Widgets
public class HomePanel extends JPanel {

    
    private JLabel lblStockTotalValor;
    private JLabel lblStockTotalUnidades;
    private JList<String> listStockBajo;
    private DefaultListModel<String> modeloListaStockBajo;
    private JPanel panelContenedorGrafico;
    private JTable tblEntradasRecientes;
    private DefaultTableModel modeloTablaEntradas;
    private JPanel panelGraficoVentas;
    
    public HomePanel() {
        super(new BorderLayout(10, 10));
        setBackground(StyleManager.GRIS_CLARO);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        JPanel panelSuperior = new JPanel(new GridLayout(1, 3, 10, 10)); // 1 fila, 3 cols, gaps
        panelSuperior.setOpaque(false);
        
        
        JPanel panelStock = crearWidgetStockActual();
        panelSuperior.add(panelStock);

        //Widget Stock Bajo
        JPanel panelStockBajo = crearWidgetStockBajo();
        panelSuperior.add(panelStockBajo);

        // Widget Gráfico de Ventas 
        panelContenedorGrafico = new JPanel(new BorderLayout());
        panelContenedorGrafico.setBackground(Color.WHITE);
        panelContenedorGrafico.setBorder(BorderFactory.createTitledBorder(
            null, " Ventas de la Semana ", TitledBorder.LEFT, TitledBorder.TOP, 
            StyleManager.FONT_BOTON, StyleManager.TEXTO_NEGRO
        ));
        panelContenedorGrafico.add(new JLabel("(Cargando gráfico...)", SwingConstants.CENTER));
        panelSuperior.add(panelContenedorGrafico);

        add(panelSuperior, BorderLayout.NORTH);
     
        // 4. Widget Entradas Recientes (CENTER)
        String[] columnas = {"PRODUCTO", "CANTIDAD", "FECHA", "PROVEEDOR"};
        modeloTablaEntradas = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
        };
        tblEntradasRecientes = new JTable(modeloTablaEntradas);
        tblEntradasRecientes.setRowHeight(25);
        tblEntradasRecientes.getTableHeader().setFont(StyleManager.FONT_BOTON);

        JScrollPane scrollPane = new JScrollPane(tblEntradasRecientes);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            null, " Entradas Recientes ", TitledBorder.LEFT, TitledBorder.TOP, 
            StyleManager.FONT_BOTON, StyleManager.TEXTO_NEGRO
        ));
        add(scrollPane, BorderLayout.CENTER);

    }
    public void setGraficoVentas(JPanel graficoPanel) {
    panelContenedorGrafico.removeAll();
    panelContenedorGrafico.add(graficoPanel, BorderLayout.CENTER);
    panelContenedorGrafico.revalidate();
    panelContenedorGrafico.repaint();
    }
    
    private JPanel crearWidgetStockActual() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            null, " Stock Actual ", TitledBorder.LEFT, TitledBorder.TOP, 
            StyleManager.FONT_BOTON, StyleManager.TEXTO_NEGRO
        ));
        
        lblStockTotalUnidades = new JLabel("0", SwingConstants.CENTER);
        lblStockTotalUnidades.setFont(StyleManager.FONT_TITULO.deriveFont(48f)); 
        
        lblStockTotalValor = new JLabel("Valor total: S/ 0.00", SwingConstants.CENTER);
        lblStockTotalValor.setFont(StyleManager.FONT_LABEL);
        
        panel.add(lblStockTotalUnidades, BorderLayout.CENTER);
        panel.add(lblStockTotalValor, BorderLayout.SOUTH);
        panel.setPreferredSize(new Dimension(250, 150)); 
        return panel;
    }
    
    private JPanel crearWidgetStockBajo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            null, " Productos con Bajo Stock ", TitledBorder.LEFT, TitledBorder.TOP, 
            StyleManager.FONT_BOTON, StyleManager.TEXTO_NEGRO
        ));
        
        modeloListaStockBajo = new DefaultListModel<>();
        listStockBajo = new JList<>(modeloListaStockBajo);
        listStockBajo.setFont(StyleManager.FONT_LABEL.deriveFont(Font.BOLD));
        listStockBajo.setForeground(new Color(211, 47, 47)); 
        
        panel.add(new JScrollPane(listStockBajo), BorderLayout.CENTER);
        return panel;
    }

    public void setStockTotal(int unidades, double valor) {
        lblStockTotalUnidades.setText(String.valueOf(unidades));
        lblStockTotalValor.setText(String.format(new Locale("es", "PE"), "Valor total: S/ %,.2f", valor));
    }
    
    public DefaultListModel<String> getModeloListaStockBajo() {
        return modeloListaStockBajo;
    }
    
    public DefaultTableModel getModeloTablaEntradas() {
    return modeloTablaEntradas;
    }
}
