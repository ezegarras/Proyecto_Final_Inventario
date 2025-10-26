/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.ICategoriaDAO;
import dao.IProductoDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import modelo.Usuario;
import vista.InventarioPanel;
import javax.swing.SwingUtilities;
import vista.ProductoDialog;

/**
 *
 * @author Enrique Zegarra
 */
public class InventarioController {
    
    private final InventarioPanel vista;
    private final IProductoDAO dao;
    private final ICategoriaDAO categoriaDAO;
    private final DefaultTableModel modeloTabla;
    private final Usuario usuario;

    public InventarioController(InventarioPanel vista, IProductoDAO dao, Usuario usuario) {
    this.vista = vista;
    this.dao = dao;
    this.categoriaDAO = new dao.CategoriaDAOImpl();
    this.usuario = usuario; 
    this.modeloTabla = vista.getModeloTabla();

    inicializar();
}
    
    private void inicializar() {
    // 1. Carga inicial
    cargarDatosTabla();

    // 2. Listeners para los filtros (se actualizan en vivo)
    vista.getTxtBuscarProducto().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { cargarDatosTabla(); }
        @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { cargarDatosTabla(); }
        @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { cargarDatosTabla(); }
    });

    vista.getChkStockBajo().addActionListener(e -> cargarDatosTabla());

    // 3. Permisos de Administrador
    if (usuario.getRolNombre().equals("Administrador")) {
        // Solo el Admin ve y usa el botón eliminar
        vista.getBtnEliminar().setVisible(true);
        vista.getBtnEliminar().addActionListener(e -> eliminarProductoSeleccionado());
        vista.getTablaProductos().addMouseListener(new java.awt.event.MouseAdapter() {
            
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            abrirDialogoEditar();
        }
    }
});
   
    } else {
        // Oculta el botón para otros roles
        vista.getBtnEliminar().setVisible(false);
    }

    // 4. (Próximo paso) Listener para Doble-Clic (Editar)
    // vista.getTablaProductos().addMouseListener(...)
}
    
    private void cargarDatosTabla() {
        
    // 1. Obtener valores de los filtros
    String busqueda = vista.getTxtBuscarProducto().getText();
    boolean soloStockBajo = vista.getChkStockBajo().isSelected();

    // 2. Limpiar tabla
    modeloTabla.setRowCount(0);

    // 3. Pedir datos al DAO (ahora con filtros)
    List<Producto> lista = dao.listarProductos(busqueda, soloStockBajo);

    // 4. Llenar la tabla
    for (Producto p : lista) {
        Object[] fila = new Object[6];
        fila[0] = p.getIdProducto();
        fila[1] = p.getNombre();
        fila[2] = p.getNombreCategoria();
        fila[3] = p.getPrecioUnitario();
        fila[4] = p.getStockActual();
        fila[5] = p.getStockMinimo();

        modeloTabla.addRow(fila);
    }
}
    
    private void eliminarProductoSeleccionado() {
    // 1. Obtener la fila seleccionada
    int filaSeleccionada = vista.getTablaProductos().getSelectedRow();

    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(vista, "Por favor, seleccione un producto de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 2. Obtener el ID y el Nombre (para confirmación)
    int idProducto = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
    String nombreProducto = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

    // 3. Pedir confirmación
    int confirmacion = JOptionPane.showConfirmDialog(vista, 
        "¿Está seguro de que desea eliminar el producto: " + nombreProducto + "?",
        "Confirmar Eliminación", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.WARNING_MESSAGE);

    if (confirmacion == JOptionPane.YES_OPTION) {
        // 4. Proceder con la eliminación
        boolean exito = dao.eliminar(idProducto);

        if (exito) {
            JOptionPane.showMessageDialog(vista, "Producto eliminado exitosamente.");
            cargarDatosTabla(); // Recargar la tabla
        }
        // (Si hay error, el DAO ya mostró un mensaje)
    }
}

private void abrirDialogoEditar() {
    // 1. Obtener la fila seleccionada
    int filaSeleccionada = vista.getTablaProductos().getSelectedRow();
    if (filaSeleccionada == -1) {
        return; // No hay fila seleccionada
    }

    // 2. Obtener el ID del producto de la tabla (columna 0)
    int idProducto = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

    // 3. Buscar el producto en la BD
    Producto productoAEditar = dao.buscarPorId(idProducto);

    if (productoAEditar == null) {
        JOptionPane.showMessageDialog(vista, "No se pudo encontrar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 4. Crear la vista (Diálogo)
    java.awt.Frame framePadre = (java.awt.Frame) SwingUtilities.getWindowAncestor(vista);
    ProductoDialog dialogView = new ProductoDialog(framePadre);

    // 5. Crear el controlador del diálogo
    new ProductoDialogController(dialogView, dao, categoriaDAO, productoAEditar);

    // 6. Mostrar el diálogo
    dialogView.setVisible(true);

    // 7. (Importante) Cuando el diálogo se cierra, recargamos la tabla
    cargarDatosTabla();
}
    
}
