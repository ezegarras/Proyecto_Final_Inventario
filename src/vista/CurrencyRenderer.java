/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Component;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderer genérico para formatear números como Moneda (Soles).
 */
public class CurrencyRenderer extends DefaultTableCellRenderer {

    private static final NumberFormat FORMATO_MONEDA = 
            NumberFormat.getCurrencyInstance(new Locale("es", "PE"));

    public CurrencyRenderer() {
        super();
        // Alinea el texto a la derecha (estándar contable)
        setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        // Llama al padre para manejar selección, colores, etc.
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Formatear solo si el valor es un número
        if (value instanceof Number) {
            setText(FORMATO_MONEDA.format(value));
        } else if (value != null) {
            // Si no es un número (ej. el String formateado de la Etapa 17)
            // lo mostramos tal cual, pero intentamos formatearlo si es un String numérico.
            try {
                setText(FORMATO_MONEDA.format(Double.parseDouble(value.toString())));
            } catch (NumberFormatException e) {
                setText(value.toString()); // Fallback
            }
        } else {
            setText("");
        }

        return this;
    }
}
