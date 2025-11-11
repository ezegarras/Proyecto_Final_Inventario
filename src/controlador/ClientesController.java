/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

/**
 *
 * @author Enrique Zegarra
 */

import dao.IClienteDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Usuario;
import vista.ClienteDialog;
import vista.ClientesPanel;
import utils.DataUpdateListener;
import utils.DataUpdateNotifier;

public class ClientesController {
    
    private final ClientesPanel vista;
    private final IClienteDAO dao;
    private final DefaultTableModel modeloTabla;
    private final Usuario usuario;
    private final DataUpdateNotifier notifier;

    public ClientesController(ClientesPanel vista, IClienteDAO dao, Usuario usuario, DataUpdateNotifier notifier) {
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
        boolean esVentas = usuario.getRolNombre().equals("Ventas");
        
        // Solo Admin y Ventas pueden gestionar clientes
        vista.getBtnAgregar().addActionListener(e -> abrirDialogo(null));
        vista.getBtnEditar().addActionListener(e -> abrirDialogo(getClienteSeleccionado()));
        vista.getBtnEliminar().addActionListener(e -> eliminarCliente());
        
        vista.getBtnAgregar().setVisible(esAdmin || esVentas);
        vista.getBtnEditar().setVisible(esAdmin || esVentas);
        vista.getBtnEliminar().setVisible(esAdmin); // Solo Admin puede eliminar
    }
    
    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        List<Cliente> clientes = dao.listarTodos();
        for (Cliente c : clientes) {
            modeloTabla.addRow(new Object[]{
                c.getIdCliente(), c.getDni(), c.getNombre(), c.getCelular(),
                c.getCorreo(), c.getDireccion(), c.getEdad(), c.getSexo()
            });
        }
    }
    
    private Cliente getClienteSeleccionado() {
        int fila = vista.getTablaClientes().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un cliente de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        // Creamos el cliente desde el modelo de la tabla
        Cliente c = new Cliente();
        c.setIdCliente((int) modeloTabla.getValueAt(fila, 0));
        c.setDni((String) modeloTabla.getValueAt(fila, 1));
        c.setNombre((String) modeloTabla.getValueAt(fila, 2));
        c.setCelular((String) modeloTabla.getValueAt(fila, 3));
        c.setCorreo((String) modeloTabla.getValueAt(fila, 4));
        c.setDireccion((String) modeloTabla.getValueAt(fila, 5));
        c.setEdad((int) modeloTabla.getValueAt(fila, 6));
        c.setSexo((String) modeloTabla.getValueAt(fila, 7));
        return c;
    }

    private void abrirDialogo(Cliente cliente) {
        boolean modoEdicion = (cliente != null);
        String titulo = modoEdicion ? "Editar Cliente" : "Nuevo Cliente";
        
        java.awt.Frame framePadre = (java.awt.Frame) SwingUtilities.getWindowAncestor(vista);
        ClienteDialog dialog = new ClienteDialog(framePadre, titulo);
        
        if (modoEdicion) {
            dialog.setCliente(cliente);
        }
        
        dialog.getBtnGuardar().addActionListener(e -> {
            if (dialog.validarCampos()) {
                Cliente c = dialog.getCliente();
                boolean exito = false;
                if (modoEdicion) {
                    c.setIdCliente(cliente.getIdCliente());
                    exito = dao.actualizar(c);
                } else {
                    exito = dao.insertar(c);
                }
                
                if (exito) {
                    cargarDatosTabla();
                    this.notifier.notifyListeners();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Error al guardar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        dialog.getBtnCancelar().addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }
    
    private void eliminarCliente() {
        Cliente c = getClienteSeleccionado();
        if (c == null) return;
        
        int confirm = JOptionPane.showConfirmDialog(vista, 
            "¿Seguro que desea eliminar a: " + c.getNombre() + "?", 
            "Confirmar", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.eliminar(c.getIdCliente())) {
                cargarDatosTabla();
                this.notifier.notifyListeners();
            }
        }
    }
}
