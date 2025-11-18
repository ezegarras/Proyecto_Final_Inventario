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
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;


    public class GraficoService {

    public static JPanel crearGraficoCircular(PieDataset dataset, String titulo) {

    
    JFreeChart chart = ChartFactory.createPieChart(
        titulo,
        dataset,
        true,  // Leyenda (true)
        true,  // Tooltips (true)
        false  // URLs (false)
    );

    
    chart.setBackgroundPaint(java.awt.Color.WHITE);

    
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(300, 250)); 

    return chartPanel;
    }

    public static JPanel crearGraficoBarras(DefaultCategoryDataset dataset, String titulo, String ejeX, String ejeY) {
       
        JFreeChart chart = ChartFactory.createBarChart(
            titulo, 
            ejeX,   
            ejeY,   
            dataset, 
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
