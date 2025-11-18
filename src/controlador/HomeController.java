/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import dao.IProductoDAO;
import java.util.List;
import javax.swing.DefaultListModel;
import modelo.Producto;
import utils.DataUpdateListener;
import vista.HomePanel;
import dao.ISalidaDAO;
import java.util.List;
import modelo.VentaDiariaDTO;
import org.jfree.data.category.DefaultCategoryDataset;
import utils.GraficoService;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JPanel;
import dao.IEntradaDAO;
import modelo.Entrada;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Enrique Zegarra
 */

public class HomeController implements DataUpdateListener {

    private final HomePanel vista;
    private final IProductoDAO productoDAO;
    private final ISalidaDAO salidaDAO;
    private final IEntradaDAO entradaDAO;
  

    public HomeController(HomePanel vista, IProductoDAO pDAO, ISalidaDAO sDAO, IEntradaDAO eDAO) {
        this.vista = vista;
        this.productoDAO = pDAO;
        this.salidaDAO = sDAO;
        this.entradaDAO = eDAO;
        
        cargarWidgets();
    }
    
    private void cargarWidgets() {
        cargarWidgetStockTotal();
        cargarWidgetStockBajo();
        cargarGraficoVentas();
        cargarWidgetEntradasRecientes();
    }
    
    private void cargarWidgetEntradasRecientes() {
    DefaultTableModel model = vista.getModeloTablaEntradas();
    model.setRowCount(0);

    List<Entrada> entradas = entradaDAO.listarUltimas(5); 

    SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy");

    for (Entrada e : entradas) {
        Object[] fila = new Object[4];
        fila[0] = e.getNombreProducto();
        fila[1] = e.getCantidad();
        fila[2] = formatFecha.format(e.getFecha());
        fila[3] = e.getNombreProveedor();
        model.addRow(fila);
        }
    }
    
    private void cargarGraficoVentas() {
 
    List<VentaDiariaDTO> ventas = salidaDAO.getVentasUltimos7Dias();

 
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();


    SimpleDateFormat formatDia = new SimpleDateFormat("E", new Locale("es", "ES"));

    for (VentaDiariaDTO dia : ventas) {
        String diaSemana = formatDia.format(dia.getFecha());
        dataset.addValue(dia.getTotalVendido(), "Ventas", diaSemana);
    }

    
    JPanel graficoPanel = GraficoService.crearGraficoBarras(dataset, 
    "Ventas de la Semana (Unidades)", "Día", "Cantidad Vendida");

    vista.setGraficoVentas(graficoPanel);
    }
    
    private void cargarWidgetStockTotal() {
        
        List<Producto> productos = productoDAO.listarParaCombos();
        
        int totalUnidades = 0;
        double valorTotal = 0.0;
        
        for (Producto p : productos) {
            totalUnidades += p.getStockActual();
            valorTotal += (p.getStockActual() * p.getPrecioUnitario());
        }
        
        vista.setStockTotal(totalUnidades, valorTotal);
    }
    
    private void cargarWidgetStockBajo() {
        DefaultListModel<String> model = vista.getModeloListaStockBajo();
        model.clear();
        
        List<Producto> productosBajos = productoDAO.listarProductosConStockBajo();
        
        if (productosBajos.isEmpty()) {
            model.addElement("No hay productos con stock bajo.");
        } else {
            for (Producto p : productosBajos) {
                model.addElement(p.getNombre() + " (Stock: " + p.getStockActual() + ")");
            }
        }
    }

  
    @Override
    public void onDataChanged() {
        System.out.println("HomeController: ¡Datos cambiaron! Recargando widgets...");
        cargarWidgets();
    }
}
