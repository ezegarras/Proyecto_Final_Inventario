/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Enrique Zegarra
 */

public class InventarioTableCellRenderer extends DefaultTableCellRenderer {

    
    private static final Color COLOR_STOCK_BAJO = new Color(255, 220, 220); // Rojo claro
    private static final Color COLOR_NORMAL = Color.WHITE;
    private static final Color COLOR_SELECCION = new Color(57, 105, 138); // Azul de selección

    
    private static final NumberFormat FORMATO_MONEDA = NumberFormat.getCurrencyInstance(new Locale("es", "PE"));

    
    private static final int COLUMNA_PRECIO = 3;
    private static final int COLUMNA_STOCK_ACTUAL = 4;
    private static final int COLUMNA_STOCK_MINIMO = 5;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        
        
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        
        if (column == COLUMNA_PRECIO && value instanceof Number) {
            setText(FORMATO_MONEDA.format(value));
        }

        
        if (!isSelected) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            
            // Obtenemos los valores de la fila actual
            int stockActual = (int) model.getValueAt(row, COLUMNA_STOCK_ACTUAL);
            int stockMinimo = (int) model.getValueAt(row, COLUMNA_STOCK_MINIMO);

            if (stockActual <= stockMinimo) {
                c.setBackground(COLOR_STOCK_BAJO);
            } else {
                c.setBackground(COLOR_NORMAL);
            }
        } else {
            
            c.setBackground(COLOR_SELECCION);
        }

        return c;
    }
}
