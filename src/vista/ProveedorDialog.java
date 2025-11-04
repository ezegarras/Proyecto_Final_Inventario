/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import modelo.Proveedor;
import utils.StyleManager;


/**
 *
 * @author Enrique Zegarra
 */

public class ProveedorDialog extends JDialog {
    
    private JTextField txtRazonSocial;
    private JTextField txtRuc;
    private JTextField txtCelular;
    private JTextField txtCorreo;
    private JTextField txtDireccion;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public ProveedorDialog(Frame owner, String title) {
        super(owner, title, true); 
        
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        
        txtRazonSocial = StyleManager.createStyledTextField();
        txtRuc = StyleManager.createStyledTextField();
        txtCelular = StyleManager.createStyledTextField();
        txtCorreo = StyleManager.createStyledTextField();
        txtDireccion = StyleManager.createStyledTextField();
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0; panel.add(StyleManager.createLabel("Razón Social:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(txtRazonSocial, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panel.add(StyleManager.createLabel("RUC (11 dig.):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(txtRuc, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; panel.add(StyleManager.createLabel("Celular:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(txtCelular, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; panel.add(StyleManager.createLabel("Correo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(txtCorreo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; panel.add(StyleManager.createLabel("Dirección:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; panel.add(txtDireccion, gbc);

        // Botones 
        btnGuardar = StyleManager.createPrimaryButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        
        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }
    
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnCancelar() { return btnCancelar; }
    
    
    public void setProveedor(Proveedor p) {
        txtRazonSocial.setText(p.getRazonSocial());
        txtRuc.setText(p.getRuc());
        txtCelular.setText(p.getCelular());
        txtCorreo.setText(p.getCorreo());
        txtDireccion.setText(p.getDireccion());
    }
    
    public Proveedor getProveedor() {
        Proveedor p = new Proveedor();
        p.setRazonSocial(txtRazonSocial.getText());
        p.setRuc(txtRuc.getText());
        p.setCelular(txtCelular.getText());
        p.setCorreo(txtCorreo.getText());
        p.setDireccion(txtDireccion.getText());
        return p;
    }
    
    public boolean validarCampos() {
        if (txtRazonSocial.getText().trim().isEmpty() || txtRuc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Razón Social y RUC son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtRuc.getText().trim().length() != 11) {
            JOptionPane.showMessageDialog(this, "El RUC debe tener 11 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
