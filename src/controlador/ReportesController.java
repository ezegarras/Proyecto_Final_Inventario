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
import javax.swing.JPanel;
import modelo.CategoriaVentaDTO;
import modelo.ClienteReporteDTO;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import utils.GraficoService;
import vista.CurrencyRenderer;


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
            case "Ventas por Cliente":
            cargarReporteVentasCliente();
            break;
            default:
                
                vista.getModeloTabla().setRowCount(0);
                vista.getModeloTabla().setColumnCount(0);
                vista.setGrafico(null);
                break;
        }
    }
    
    private void cargarReporteVentasCliente() {

    // 1. Obtener datos del DAO (sin cambios)
    List<ClienteReporteDTO> reporte = salidaDAO.getReporteVentasPorCliente();

    // --- INICIO: LÓGICA DEL GRÁFICO ---

    // 2. Crear el dataset para el Gráfico de Barras (Top 10 Clientes)
    DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
    int clientesEnGrafico = 0;

    for (ClienteReporteDTO item : reporte) {
        if (clientesEnGrafico >= 10) break; // Limitar a los Top 10

        // (Serie, Eje X, Eje Y)
        barDataset.addValue(item.getMontoTotal(), "Monto (S/)", item.getNombreCliente());
        clientesEnGrafico++;
    }

    // 3. Crear el panel del gráfico
    JPanel graficoPanel = GraficoService.crearGraficoBarras(
        barDataset, 
        "Top 10 Clientes (Monto Gastado)", // Título
        "Cliente",                        // Eje X
        "Monto Total (S/)"                // Eje Y
    );

    // 4. Insertar el gráfico en la vista
    vista.setGrafico(graficoPanel);

    // --- FIN: LÓGICA DEL GRÁFICO ---

    // --- INICIO: LÓGICA DE LA TABLA ---

    // 5. Definir columnas (sin cambios)
    String[] columnas = {"DNI", "NOMBRE CLIENTE", "# COMPRAS", "MONTO TOTAL (S/)"};
    vista.getModeloTabla().setColumnIdentifiers(columnas);
    vista.getModeloTabla().setRowCount(0);

    // 6. Llenar la tabla
    for (ClienteReporteDTO item : reporte) {
        Object[] fila = new Object[4];
        fila[0] = item.getDni();
        fila[1] = item.getNombreCliente();
        fila[2] = item.getNumeroCompras();

        // --- IMPORTANTE: Pasar el Double, no el String ---
        // fila[3] = String.format("%.2f", item.getMontoTotal()); // <-- ANTES
        fila[3] = item.getMontoTotal(); // <-- AHORA

        vista.getModeloTabla().addRow(fila);
    }

    // 7. Aplicar el Renderer de Moneda a la Columna 3
    vista.getTblReporte().getColumnModel().getColumn(3).setCellRenderer(new CurrencyRenderer());

    // --- FIN: LÓGICA DE LA TABLA ---
    }
    
    private void cargarReporteMasVendidos() {
        
    String[] columnas = {"ID PRODUCTO", "NOMBRE PRODUCTO", "TOTAL VENDIDO (Unidades)"};
    vista.getModeloTabla().setColumnIdentifiers(columnas);
    vista.getModeloTabla().setRowCount(0);

    List<CategoriaVentaDTO> ventasCat = salidaDAO.getVentasPorCategoria();
    List<Producto> productos = salidaDAO.listarProductosMasVendidos(10);
    
    DefaultPieDataset pieDataset = new DefaultPieDataset();
    for (CategoriaVentaDTO dto : ventasCat) {
        pieDataset.setValue(dto.getNombreCategoria(), dto.getTotalVendido());
    }

    
    JPanel graficoPanel = GraficoService.crearGraficoCircular(pieDataset, "Participación por Categoría");

    vista.setGrafico(graficoPanel);


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
        
        vista.setGrafico(null);
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
