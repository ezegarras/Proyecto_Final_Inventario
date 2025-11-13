/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class GraficoService {

    public static JPanel crearGraficoBarras(DefaultCategoryDataset dataset) {
       
        JFreeChart chart = ChartFactory.createBarChart(
            "Ventas de la Semana",  // Título
            "Día",                  // Etiqueta Eje X
            "Cantidad Vendida",     // Etiqueta Eje Y
            dataset,                // Datos
            PlotOrientation.VERTICAL,
            false,                  // Leyenda 
            true,                   // Tooltips
            false                   // URLs 
        );
        
       
        chart.setBackgroundPaint(java.awt.Color.WHITE);
        chart.getCategoryPlot().setBackgroundPaint(StyleManager.GRIS_CLARO);

        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(350, 250));
        
        return chartPanel;
    }
    
    // (Aquí irían los métodos: crearGraficoCircular, crearGraficoLinea, etc.)
}
