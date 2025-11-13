/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Enrique Zegarra
 */
package controlador;

import dao.IProductoDAO;
import dao.ISalidaDAO;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import vista.ReportesPanel;


public class ReportesController {
    
    private final ReportesPanel vista;
    private final IProductoDAO productoDAO;
    private final ISalidaDAO salidaDAO;
    

    public ReportesController(ReportesPanel vista, IProductoDAO pDAO, ISalidaDAO sDAO) {
        this.vista = vista;
        this.productoDAO = pDAO;
        this.salidaDAO = sDAO;
        inicializar();
    }
    
    private void inicializar() {
        
        vista.getCmbTipoReporte().addActionListener(e -> cargarReporteSeleccionado());
    }
    
    private void cargarReporteSeleccionado() {
        String seleccion = (String) vista.getCmbTipoReporte().getSelectedItem();
        if (seleccion == null) return;
        
        switch (seleccion) {
            case "Productos con Stock bajo":
                cargarReporteStockBajo();
                break;
            case "Productos más vendidos":
                cargarReporteMasVendidos();
                break;
            default:
                
                vista.getModeloTabla().setRowCount(0);
                vista.getModeloTabla().setColumnCount(0);
                break;
        }
    }
    
    private void cargarReporteMasVendidos() {
    // columnas
    String[] columnas = {"ID PRODUCTO", "NOMBRE PRODUCTO", "TOTAL VENDIDO (Unidades)"};
    vista.getModeloTabla().setColumnIdentifiers(columnas);
    vista.getModeloTabla().setRowCount(0);

    // Obteniene datos del DAO
    List<Producto> productos = salidaDAO.listarProductosMasVendidos(10);

    // completa la tabla con la informacion
    for (Producto p : productos) {
        Object[] fila = new Object[3];
        fila[0] = p.getIdProducto();
        fila[1] = p.getNombre();
        fila[2] = p.getTotalVendido();
        vista.getModeloTabla().addRow(fila);
    }
    }
    
    private void cargarReporteStockBajo() {
        //Definir columnas parareporte
        String[] columnas = {"ID", "PRODUCTO", "CATEGORÍA", "STOCK ACTUAL", "STOCK MÍNIMO"};
        vista.getModeloTabla().setColumnIdentifiers(columnas);
        vista.getModeloTabla().setRowCount(0); // Limpiar filas

        //Obtener datos del DAO
        List<Producto> productos = productoDAO.listarProductosConStockBajo();
        
        //Llenar la tabla
        for (Producto p : productos) {
            Object[] fila = new Object[5];
            fila[0] = p.getIdProducto();
            fila[1] = p.getNombre();
            fila[2] = p.getNombreCategoria();
            fila[3] = p.getStockActual();
            fila[4] = p.getStockMinimo();
            vista.getModeloTabla().addRow(fila);
        }
        
        // (Aplicar el renderer de color rojo que ya creamos en Inventario)
        // Esto es una mejora futura: centralizar el renderer.
    }
}
