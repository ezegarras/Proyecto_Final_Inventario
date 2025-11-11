/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.IEntradaDAO;
import dao.IProductoDAO;
import dao.IProveedorDAO;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Entrada;
import modelo.Producto;
import modelo.Proveedor;
import vista.EntradasPanel;
import javax.swing.SwingUtilities;
import vista.ProductoDialog;
import dao.ICategoriaDAO;
import dao.CategoriaDAOImpl;
import utils.DataUpdateListener;
import utils.DataUpdateNotifier;

/**
 *
 * @author Enrique Zegarra
 */

public class EntradasController {
    
    private final EntradasPanel vista;
    private final IEntradaDAO entradaDAO;
    private final IProductoDAO productoDAO;
    private final IProveedorDAO proveedorDAO;
    private final ICategoriaDAO categoriaDAO;
    
    private final DefaultTableModel modeloTabla;
    private final DataUpdateNotifier notifier;

    public EntradasController(EntradasPanel vista, IEntradaDAO eDAO, IProductoDAO pDAO, IProveedorDAO provDAO, ICategoriaDAO cDAO, DataUpdateNotifier notifier) {
        this.vista = vista;
        this.entradaDAO = eDAO;
        this.productoDAO = pDAO;
        this.proveedorDAO = provDAO;
        this.categoriaDAO = cDAO;
        this.modeloTabla = vista.getModeloTablaHistorial();
        this.notifier = notifier;
        
        inicializar();
    }
    
    private void inicializar() {
        
        configurarRenderers();
        
        
        cargarCombos();
        
        
        cargarHistorial();
        
        
        vista.getBtnRegistrarEntrada().addActionListener(e -> registrarEntrada());
        
        vista.getBtnNuevoProducto().addActionListener(e -> abrirDialogoNuevoProducto());
    }
    
    private void configurarRenderers() {
        vista.setProductoCellRenderer();
        vista.setProveedorCellRenderer();
    }
    
    private void cargarCombos() {
       
        List<Producto> productos = productoDAO.listarProductos("", false); 
        DefaultComboBoxModel<Producto> modelProducto = new DefaultComboBoxModel<>();
        modelProducto.addElement(null); 
        for (Producto p : productos) {
            modelProducto.addElement(p);
        }
        vista.getCmbProducto().setModel(modelProducto);
        
        // Cargar Proveedores
        List<Proveedor> proveedores = proveedorDAO.listarTodos();
        DefaultComboBoxModel<Proveedor> modelProveedor = new DefaultComboBoxModel<>();
        modelProveedor.addElement(null); 
        for (Proveedor p : proveedores) {
            modelProveedor.addElement(p);
        }
        vista.getCmbProveedor().setModel(modelProveedor);
    }
    
    private void cargarHistorial() {
        modeloTabla.setRowCount(0);
        
        
        List<Entrada> entradas = entradaDAO.listarUltimas(20); 
        
        for (Entrada e : entradas) {
            Object[] fila = new Object[4];
            fila[0] = e.getNombreProducto();
            fila[1] = e.getCantidad();
            fila[2] = new java.text.SimpleDateFormat("dd/MM/yyyy").format(e.getFecha());
            fila[3] = e.getNombreProveedor();
            modeloTabla.addRow(fila);
        }
    }
    
    private void registrarEntrada() {
        // Obtiene y valida datos del formulario
        Producto producto = (Producto) vista.getCmbProducto().getSelectedItem();
        Proveedor proveedor = (Proveedor) vista.getCmbProveedor().getSelectedItem();
        int cantidad = (Integer) vista.getSpinCantidad().getValue();
        Date fecha = (Date) vista.getSpinFecha().getValue();
        
        if (producto == null || proveedor == null) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un producto y un proveedor.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        Entrada nuevaEntrada = new Entrada();
        nuevaEntrada.setIdProducto(producto.getIdProducto());
        nuevaEntrada.setIdProveedor(proveedor.getIdProveedor());
        nuevaEntrada.setCantidad(cantidad);
        nuevaEntrada.setFecha(fecha);
        
        // Llama al DAO que maneja la transacción
        boolean exito = entradaDAO.registrarEntrada(nuevaEntrada);
        
        if (exito) {
            JOptionPane.showMessageDialog(vista, "Entrada registrada exitosamente. Stock actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarHistorial(); 
            

        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar la entrada. La base de datos revirtió los cambios.", "Error de Transacción", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirDialogoNuevoProducto() {  
    
    java.awt.Frame framePadre = (java.awt.Frame) SwingUtilities.getWindowAncestor(vista);

    
    ProductoDialog dialogView = new ProductoDialog(framePadre);

    
    Producto productoNuevo = new Producto();

   
    new ProductoDialogController(dialogView, productoDAO, categoriaDAO, productoNuevo, false, notifier);

    
    dialogView.setVisible(true);

    cargarCombos();
}
}
