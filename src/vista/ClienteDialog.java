/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package vista;

/**
 *
 * @author Enrique Zegarra
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import modelo.Cliente;
import utils.StyleManager;

public class ClienteDialog extends JDialog {

    private JTextField txtDni;
    private JTextField txtNombre;
    private JTextField txtCelular;
    private JTextField txtCorreo;
    private JTextField txtDireccion;
    private JSpinner spinEdad;
    private JComboBox<String> cmbSexo;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public ClienteDialog(Frame owner, String title) {
        super(owner, title, true);
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtDni = StyleManager.createStyledTextField();
        txtNombre = StyleManager.createStyledTextField();
        txtCelular = StyleManager.createStyledTextField();
        txtCorreo = StyleManager.createStyledTextField();
        txtDireccion = StyleManager.createStyledTextField();
        spinEdad = new JSpinner(new SpinnerNumberModel(18, 1, 100, 1));
        cmbSexo = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0; panel.add(StyleManager.createLabel("DNI (8 dig.):"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(txtDni, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panel.add(StyleManager.createLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; panel.add(StyleManager.createLabel("Celular:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(txtCelular, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; panel.add(StyleManager.createLabel("Correo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(txtCorreo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; panel.add(StyleManager.createLabel("Dirección:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; panel.add(txtDireccion, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; panel.add(StyleManager.createLabel("Edad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; panel.add(spinEdad, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6; panel.add(StyleManager.createLabel("Sexo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; panel.add(cmbSexo, gbc);

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
    
    // Aqui se leen los metodos para cargar y leer datos 
    
    public void setCliente(Cliente c) {
        txtDni.setText(c.getDni());
        txtNombre.setText(c.getNombre());
        txtCelular.setText(c.getCelular());
        txtCorreo.setText(c.getCorreo());
        txtDireccion.setText(c.getDireccion());
        spinEdad.setValue(c.getEdad());
        cmbSexo.setSelectedItem(c.getSexo());
    }
    
    public Cliente getCliente() {
        Cliente c = new Cliente();
        c.setDni(txtDni.getText().trim());
        c.setNombre(txtNombre.getText().trim());
        c.setCelular(txtCelular.getText().trim());
        c.setCorreo(txtCorreo.getText().trim());
        c.setDireccion(txtDireccion.getText().trim());
        c.setEdad((Integer) spinEdad.getValue());
        c.setSexo((String) cmbSexo.getSelectedItem());
        return c;
    }
    
    public boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() || txtDni.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "DNI y Nombre son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtDni.getText().trim().length() != 8) {
            JOptionPane.showMessageDialog(this, "El DNI debe tener 8 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
