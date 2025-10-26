/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author Enrique Zegarra
 */

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.*;
import modelo.Categoria;
import modelo.Producto;
import utils.StyleManager;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class ProductoDialog extends JDialog {

    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JSpinner spinStockActual;
    private JSpinner spinStockMinimo;
    private JComboBox<Categoria> cmbCategoria; 
    private JButton btnGuardar;
    private JButton btnCancelar;

    public ProductoDialog(Frame owner) {
     
        super(owner, "Editar Producto", true);
        
        initializeComponents();
        setupLayout();
        configureWindow();
    }
    
    private void initializeComponents() {
        txtNombre = StyleManager.createStyledTextField();
        txtPrecio = StyleManager.createStyledTextField();
        
     
        spinStockActual = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
        spinStockMinimo = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
        
        cmbCategoria = new JComboBox<>();
        btnGuardar = StyleManager.createPrimaryButton("Guardar Cambios");
        btnCancelar = new JButton("Cancelar");
    }
    
    private void setupLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 0: Nombre
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(StyleManager.createLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(txtNombre, gbc);

        // Fila 1: Precio
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(StyleManager.createLabel("Precio Unitario:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtPrecio, gbc);

        // Fila 2: Stock Actual
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(StyleManager.createLabel("Stock Actual:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(spinStockActual, gbc);

        // Fila 3: Stock Mínimo
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(StyleManager.createLabel("Stock Mínimo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(spinStockMinimo, gbc);
        
        // Fila 4: Categoría
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(StyleManager.createLabel("Categoría:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(cmbCategoria, gbc);
        
        // Fila 5: Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(panelBotones, gbc);
        
        this.add(panel);
    }

    private void configureWindow() {
        pack();
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }
    
   
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JSpinner getSpinStockActual() { return spinStockActual; }
    public JSpinner getSpinStockMinimo() { return spinStockMinimo; }
    public JComboBox<Categoria> getCmbCategoria() { return cmbCategoria; }
    
 
    public void setProducto(Producto p) {
        txtNombre.setText(p.getNombre());
        txtPrecio.setText(String.valueOf(p.getPrecioUnitario()));
        spinStockActual.setValue(p.getStockActual());
        spinStockMinimo.setValue(p.getStockMinimo());
        
        
        DefaultComboBoxModel<Categoria> model = (DefaultComboBoxModel<Categoria>) cmbCategoria.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            if (model.getElementAt(i).getIdCategoria() == p.getIdCategoria()) {
                cmbCategoria.setSelectedIndex(i);
                break;
            }
        }
    }

    /**
     * Lee los datos del formulario y los aplica a un objeto Producto.
     */
    public Producto getProductoActualizado(Producto p) throws NumberFormatException {
        p.setNombre(txtNombre.getText());
        p.setPrecioUnitario(Double.parseDouble(txtPrecio.getText()));
        p.setStockActual((Integer) spinStockActual.getValue());
        p.setStockMinimo((Integer) spinStockMinimo.getValue());
        
        Categoria catSeleccionada = (Categoria) cmbCategoria.getSelectedItem();
        p.setIdCategoria(catSeleccionada.getIdCategoria());
        
        return p;
    }


    public void setCategorias(List<Categoria> categorias) {
        DefaultComboBoxModel<Categoria> model = new DefaultComboBoxModel<>();
        for (Categoria cat : categorias) {
            model.addElement(cat);
        }
        cmbCategoria.setModel(model);

        // Personaliza cómo se muestra el objeto Categoria en el JComboBox
        cmbCategoria.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Categoria) {
                    setText(((Categoria) value).getNombre());
                }
                return this;
            }
        });
    }
}
