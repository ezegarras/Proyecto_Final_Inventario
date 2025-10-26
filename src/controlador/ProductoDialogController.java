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
    private final Producto producto; // El producto que estamos editando

    public ProductoDialogController(ProductoDialog vista, IProductoDAO pDAO, ICategoriaDAO cDAO, Producto producto) {
        this.vista = vista;
        this.productoDAO = pDAO;
        this.categoriaDAO = cDAO;
        this.producto = producto;
        
        // 1. Cargar datos en la vista
        cargarDatos();
        
        // 2. Asignar listeners
        this.vista.getBtnGuardar().addActionListener(e -> guardarCambios());
        this.vista.getBtnCancelar().addActionListener(e -> vista.dispose());
    }
    
    private void cargarDatos() {
        // 1. Cargar la lista de categorías en el JComboBox
        List<Categoria> categorias = categoriaDAO.listarTodas();
        vista.setCategorias(categorias);
        
        // 2. Llenar el formulario con los datos del producto
        vista.setProducto(producto);
    }
    
    private void guardarCambios() {
        try {
            // 1. Validar precio (simple)
            double precio = Double.parseDouble(vista.getTxtPrecio().getText());
            if (precio < 0) {
                JOptionPane.showMessageDialog(vista, "El precio no puede ser negativo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 2. Obtener el objeto Producto actualizado desde la vista
            Producto productoActualizado = vista.getProductoActualizado(this.producto);
            
            // 3. Llamar al DAO para guardar
            boolean exito = productoDAO.actualizar(productoActualizado);
            
            if (exito) {
                JOptionPane.showMessageDialog(vista, "Producto actualizado exitosamente.");
                vista.dispose(); // Cierra el diálogo
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "El formato del precio es incorrecto.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }
}
