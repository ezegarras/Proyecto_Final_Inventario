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

/**
 *
 * @author Enrique Zegarra
 */

/**
 * Controlador para el panel principal (Dashboard Widgets).
 */
public class HomeController implements DataUpdateListener {

    private final HomePanel vista;
    private final IProductoDAO productoDAO;
    private final ISalidaDAO salidaDAO;
    // (Necesitaremos ISalidaDAO para el gráfico de ventas después)

    public HomeController(HomePanel vista, IProductoDAO pDAO, ISalidaDAO sDAO) {
        this.vista = vista;
        this.productoDAO = pDAO;
        this.salidaDAO = sDAO;
        
        cargarWidgets();
    }
    
    private void cargarWidgets() {
        cargarWidgetStockTotal();
        cargarWidgetStockBajo();
        cargarGraficoVentas();
    }
    
    private void cargarGraficoVentas() {
    // Obtener datos del DAO
    List<VentaDiariaDTO> ventas = salidaDAO.getVentasUltimos7Dias();

    // Crea el dataset para JFreeChart
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    //  "Lun", "Mar", "Mié"
    SimpleDateFormat formatDia = new SimpleDateFormat("E", new Locale("es", "ES"));

    for (VentaDiariaDTO dia : ventas) {
        String diaSemana = formatDia.format(dia.getFecha());
        dataset.addValue(dia.getTotalVendido(), "Ventas", diaSemana);
    }

    //Crea el gráfico usando el servicio
    JPanel graficoPanel = GraficoService.crearGraficoBarras(dataset);

    //Inserta el gráfico en la vista
    vista.setGraficoVentas(graficoPanel);
    }
    
    private void cargarWidgetStockTotal() {
        
        List<Producto> productos = productoDAO.listarProductos("", false);
        
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
