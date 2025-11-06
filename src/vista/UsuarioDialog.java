/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.*;
import javax.swing.*;
import modelo.Rol;
import modelo.Usuario;
import utils.StyleManager;

/**
 *
 * @author Enrique Zegarra
 */

public class UsuarioDialog extends JDialog {

    private JTextField txtNombre;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JComboBox<Rol> cmbRol;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public UsuarioDialog(Frame owner, String title) {
        super(owner, title, true);
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNombre = StyleManager.createStyledTextField();
        txtUsuario = StyleManager.createStyledTextField();
        txtContrasena = StyleManager.createStyledPasswordField();
        cmbRol = new JComboBox<>();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0; panel.add(StyleManager.createLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(txtNombre, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(StyleManager.createLabel("Usuario (Login):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(txtUsuario, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(StyleManager.createLabel("Contraseña:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(txtContrasena, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(StyleManager.createLabel("Rol:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(cmbRol, gbc);

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
    public JComboBox<Rol> getCmbRol() { return cmbRol; }

    public void setUsuario(Usuario u) {
        txtNombre.setText(u.getNombre());
        txtUsuario.setText(u.getUsuario());
        txtContrasena.setText(u.getContrasena());
        
        for (int i = 0; i < cmbRol.getItemCount(); i++) {
            if (cmbRol.getItemAt(i).getIdRol() == u.getIdRol()) {
                cmbRol.setSelectedIndex(i);
                break;
            }
        }
    }

    public Usuario getUsuario() {
        Usuario u = new Usuario();
        u.setNombre(txtNombre.getText().trim());
        u.setUsuario(txtUsuario.getText().trim());
        u.setContrasena(new String(txtContrasena.getPassword()));
        u.setIdRol(((Rol) cmbRol.getSelectedItem()).getIdRol());
        return u;
    }
    
    public boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() || txtUsuario.getText().trim().isEmpty() || 
            new String(txtContrasena.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
