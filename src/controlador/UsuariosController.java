/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import dao.IRolDAO;
import dao.IUsuarioDAO;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import modelo.Rol;
import modelo.Usuario;
import vista.UsuarioDialog;
import vista.UsuariosPanel;

/**
 *
 * @author Enrique Zegarra
 */

public class UsuariosController {

    private final UsuariosPanel vista;
    private final IUsuarioDAO usuarioDAO;
    private final IRolDAO rolDAO;
    private final DefaultTableModel modeloTabla;
    
   
    private List<Usuario> listaUsuarios;

    public UsuariosController(UsuariosPanel vista, IUsuarioDAO uDAO, IRolDAO rDAO) {
        this.vista = vista;
        this.usuarioDAO = uDAO;
        this.rolDAO = rDAO;
        this.modeloTabla = vista.getModeloTabla();
        inicializar();
    }

    private void inicializar() {
        cargarDatosTabla();
        vista.getBtnAgregar().addActionListener(e -> abrirDialogo(null));
        vista.getBtnEditar().addActionListener(e -> abrirDialogo(getUsuarioSeleccionado()));
        vista.getBtnEliminar().addActionListener(e -> eliminarUsuario());
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        listaUsuarios = usuarioDAO.listarTodos();
        for (Usuario u : listaUsuarios) {
            modeloTabla.addRow(new Object[]{
                u.getIdUsuario(), u.getNombre(), u.getUsuario(), u.getRolNombre()
            });
        }
    }

    private Usuario getUsuarioSeleccionado() {
        int fila = vista.getTablaUsuarios().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        return listaUsuarios.get(fila);
    }

    private void abrirDialogo(Usuario usuarioEditar) {
        boolean modoEdicion = (usuarioEditar != null);
        String titulo = modoEdicion ? "Editar Usuario" : "Nuevo Usuario";
        java.awt.Frame framePadre = (java.awt.Frame) SwingUtilities.getWindowAncestor(vista);
        UsuarioDialog dialog = new UsuarioDialog(framePadre, titulo);

        
        DefaultComboBoxModel<Rol> modelRol = new DefaultComboBoxModel<>();
        for (Rol r : rolDAO.listarTodos()) {
            modelRol.addElement(r);
        }
        dialog.getCmbRol().setModel(modelRol);

        if (modoEdicion) {
            dialog.setUsuario(usuarioEditar);
        }

        dialog.getBtnGuardar().addActionListener(e -> {
            if (dialog.validarCampos()) {
                Usuario u = dialog.getUsuario();
                boolean exito;
                if (modoEdicion) {
                    u.setIdUsuario(usuarioEditar.getIdUsuario());
                    exito = usuarioDAO.actualizar(u);
                } else {
                    exito = usuarioDAO.insertar(u);
                }
                if (exito) {
                    cargarDatosTabla();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Error al guardar (¿Usuario duplicado?).", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        dialog.getBtnCancelar().addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private void eliminarUsuario() {
        Usuario u = getUsuarioSeleccionado();
        if (u == null) return;
        if (u.getRolNombre().equalsIgnoreCase("Administrador")) {
        }
        int confirm = JOptionPane.showConfirmDialog(vista, "¿Eliminar usuario: " + u.getUsuario() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (usuarioDAO.eliminar(u.getIdUsuario())) {
                cargarDatosTabla();
            }
        }
    }
}
