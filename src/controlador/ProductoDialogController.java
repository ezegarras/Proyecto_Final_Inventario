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

    public ProductoDialogController(ProductoDialog vista, IProductoDAO pDAO, ICategoriaDAO cDAO, Producto producto, boolean modoEdicion) {
        this.vista = vista;
        this.productoDAO = pDAO;
        this.categoriaDAO = cDAO;
        this.producto = producto;
        this.modoEdicion = modoEdicion;
        
        //  Cargar datos en la vista
        cargarDatos();
        
        // Asignar listeners
        this.vista.getBtnGuardar().addActionListener(e -> guardarCambios());
        this.vista.getBtnCancelar().addActionListener(e -> vista.dispose());
    }
    
    private void cargarDatos() {
    // Cargar la lista de categorías
    List<Categoria> categorias = categoriaDAO.listarTodas();
    vista.setCategorias(categorias);

    // Llenar el formulario 
    if (modoEdicion) {
        vista.setProducto(producto);
        vista.setTitle("Editar Producto"); 
    } else {
        vista.setTitle("Crear Nuevo Producto"); 
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
                vista.dispose(); 
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
  
            int nuevoId = productoDAO.insertar(productoDatos);
            exito = (nuevoId > 0);

            if (exito) {
                JOptionPane.showMessageDialog(vista, "Producto creado exitosamente.");
                vista.dispose(); 
            } else {
                JOptionPane.showMessageDialog(vista, "Error al crear el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "El formato del precio es incorrecto.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }
}
