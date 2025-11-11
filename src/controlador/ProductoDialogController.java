/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.ICategoriaDAO;
import dao.IProductoDAO;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Categoria;
import modelo.Producto;
import vista.ProductoDialog;
import utils.DataUpdateNotifier;

/**
 *
 * @author Enrique Zegarra
 */

public class ProductoDialogController {
    
    private final ProductoDialog vista;
    private final IProductoDAO productoDAO;
    private final ICategoriaDAO categoriaDAO;
    private final Producto producto;
    private final boolean modoEdicion;
    private final DataUpdateNotifier notifier;

    public ProductoDialogController(ProductoDialog vista, IProductoDAO pDAO, ICategoriaDAO cDAO, Producto producto, boolean modoEdicion, DataUpdateNotifier notifier) {
        this.vista = vista;
        this.productoDAO = pDAO;
        this.categoriaDAO = cDAO;
        this.producto = producto;
        this.modoEdicion = modoEdicion;
        this.notifier = notifier;
        
        //  Cargar datos en la vista
        cargarDatos();
        
        // Asignar listeners
        this.vista.getBtnGuardar().addActionListener(e -> guardarCambios());
        this.vista.getBtnCancelar().addActionListener(e -> vista.dispose());
        this.vista.getBtnNuevaCategoria().addActionListener(e -> crearNuevaCategoria());
    }
    
    private void cargarDatos() {
    // Cargar la lista de categorías
    refrescarComboCategorias();

    if (modoEdicion) {
        vista.setProducto(producto);
        vista.setTitle("Editar Producto"); 
    } else {
        vista.setTitle("Crear Nuevo Producto");
        // Ocultamos los campos de Stock Actual
        vista.getLblStockActual().setVisible(false);
        vista.getSpinStockActual().setVisible(false);
    }
}
    
    private void refrescarComboCategorias() {
    // Guarda la categoría seleccionada (si hay una)
    Object seleccionActual = vista.getCmbCategoria().getSelectedItem();

    List<Categoria> categorias = categoriaDAO.listarTodas();
    vista.setCategorias(categorias); // (Esto llama al setModel)

    // Vuelve a seleccionar la que estaba (si aún existe)
    if (seleccionActual != null) {
        vista.getCmbCategoria().setSelectedItem(seleccionActual);
    }
}
    
    private void guardarCambios() {
        try {
           
            double precio = Double.parseDouble(vista.getTxtPrecio().getText());
            if (precio < 0) {
                JOptionPane.showMessageDialog(vista, "El precio no puede ser negativo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            Producto productoDatos = vista.getProductoActualizado(this.producto);
            boolean exito = false;
            
            
            if (modoEdicion) {
           
            exito = productoDAO.actualizar(productoDatos);

            if (exito) {
                JOptionPane.showMessageDialog(vista, "Producto actualizado exitosamente.");
                this.notifier.notifyListeners();
                vista.dispose(); 
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
  
            int nuevoId = productoDAO.insertar(productoDatos);
            exito = (nuevoId > 0);

            if (exito) {
                JOptionPane.showMessageDialog(vista, "Producto creado exitosamente.");
                this.notifier.notifyListeners();
                vista.dispose(); 
            } else {
                JOptionPane.showMessageDialog(vista, "Error al crear el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "El formato del precio es incorrecto.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void crearNuevaCategoria() {
    String nombreNuevaCat = JOptionPane.showInputDialog(
        vista, 
        "Ingrese el nombre de la nueva categoría:", 
        "Nueva Categoría", 
        JOptionPane.PLAIN_MESSAGE
    );

    if (nombreNuevaCat != null && !nombreNuevaCat.trim().isEmpty()) {
        Categoria nuevaCat = new Categoria();
        nuevaCat.setNombre(nombreNuevaCat.trim());

        if (categoriaDAO.insertar(nuevaCat)) {
            JOptionPane.showMessageDialog(vista, "Categoría creada exitosamente.");
            refrescarComboCategorias(); // Recargamos el ComboBox
        } else {
            JOptionPane.showMessageDialog(vista, "Error al crear la categoría (quizás ya existe).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
