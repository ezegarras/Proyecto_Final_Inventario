/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.IProveedorDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import modelo.Proveedor;
import modelo.Usuario;
import vista.ProveedorDialog;
import vista.ProveedoresPanel;
import utils.DataUpdateListener;
import utils.DataUpdateNotifier;


/**
 *
 * @author Enrique Zegarra
 */


public class ProveedoresController {
    
    private final ProveedoresPanel vista;
    private final IProveedorDAO dao;
    private final DefaultTableModel modeloTabla;
    private final Usuario usuario; 
    private final DataUpdateNotifier notifier;

    public ProveedoresController(ProveedoresPanel vista, IProveedorDAO dao, Usuario usuario, DataUpdateNotifier notifier) {
        this.vista = vista;
        this.dao = dao;
        this.usuario = usuario;
        this.notifier = notifier;
        this.modeloTabla = vista.getModeloTabla();
        
        inicializar();
    }
    
    private void inicializar() {
        cargarDatosTabla();
        
        
        boolean esAdmin = usuario.getRolNombre().equals("Administrador");
        
        vista.getBtnAgregar().addActionListener(e -> abrirDialogo(null));
        vista.getBtnEditar().addActionListener(e -> abrirDialogo(getProveedorSeleccionado()));
        vista.getBtnEliminar().addActionListener(e -> eliminarProveedor());
        
        // Solo Admin puede crear, editar o eliminar (RBAC)
        vista.getBtnAgregar().setVisible(esAdmin);
        vista.getBtnEditar().setVisible(esAdmin);
        vista.getBtnEliminar().setVisible(esAdmin);
    }
    
    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0); 
        List<Proveedor> proveedores = dao.listarTodos();
        for (Proveedor p : proveedores) {
            modeloTabla.addRow(new Object[]{
                p.getIdProveedor(),
                p.getRazonSocial(),
                p.getRuc(),
                p.getCelular(),
                p.getCorreo(),
                p.getDireccion()
            });
        }
    }
    
    private Proveedor getProveedorSeleccionado() {
        int fila = vista.getTablaProveedores().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un proveedor de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        Proveedor p = new Proveedor();
        p.setIdProveedor((int) modeloTabla.getValueAt(fila, 0));
        p.setRazonSocial((String) modeloTabla.getValueAt(fila, 1));
        p.setRuc((String) modeloTabla.getValueAt(fila, 2));
        p.setCelular((String) modeloTabla.getValueAt(fila, 3));
        p.setCorreo((String) modeloTabla.getValueAt(fila, 4));
        p.setDireccion((String) modeloTabla.getValueAt(fila, 5));
        return p;
    }

    private void abrirDialogo(Proveedor proveedor) {
        boolean modoEdicion = (proveedor != null);
        String titulo = modoEdicion ? "Editar Proveedor" : "Nuevo Proveedor";
        
        java.awt.Frame framePadre = (java.awt.Frame) SwingUtilities.getWindowAncestor(vista);
        ProveedorDialog dialog = new ProveedorDialog(framePadre, titulo);
        
        if (modoEdicion) {
            dialog.setProveedor(proveedor);
        }
        
        // Listener para el botón Guardar del diálogo
        dialog.getBtnGuardar().addActionListener(e -> {
            if (dialog.validarCampos()) {
                Proveedor p = dialog.getProveedor();
                boolean exito = false;
                if (modoEdicion) {
                    p.setIdProveedor(proveedor.getIdProveedor()); // No olvidar el ID
                    exito = dao.actualizar(p);
                } else {
                    exito = dao.insertar(p);
                }
                
                if (exito) {
                    cargarDatosTabla();
                    this.notifier.notifyListeners();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Error al guardar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        dialog.getBtnCancelar().addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }
    
    private void eliminarProveedor() {
        Proveedor p = getProveedorSeleccionado();
        if (p == null) return;
        
        int confirm = JOptionPane.showConfirmDialog(vista, 
            "¿Seguro que desea eliminar a: " + p.getRazonSocial() + "?", 
            "Confirmar", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.eliminar(p.getIdProveedor())) {
                cargarDatosTabla();
                this.notifier.notifyListeners();
            }
        }
    }
}
