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
import dao.IProductoDAO;
import dao.ISalidaDAO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Factura;
import modelo.Producto;
import modelo.Salida;
import vista.SalidasPanel;

public class SalidasController {
    
    private final SalidasPanel vista;
    private final IClienteDAO clienteDAO;
    private final IProductoDAO productoDAO;
    private final ISalidaDAO salidaDAO;
    
   
    private Cliente clienteActual = null;
    private final List<Salida> carrito = new ArrayList<>();
    private final DefaultTableModel modeloCarrito;
    private final DefaultTableModel modeloHistorial;

    public SalidasController(SalidasPanel vista, IClienteDAO cDAO, IProductoDAO pDAO, ISalidaDAO sDAO) {
        this.vista = vista;
        this.clienteDAO = cDAO;
        this.productoDAO = pDAO;
        this.salidaDAO = sDAO;
        this.modeloCarrito = vista.getModeloTablaCarrito();
        this.modeloHistorial = vista.getModeloTablaHistorial();
        
        inicializar();
    }
    
    private void inicializar() {
        cargarHistorial();
        
       
        vista.getBtnBuscarCliente().addActionListener(e -> buscarCliente());
        vista.getBtnAgregarAlCarrito().addActionListener(e -> agregarAlCarrito());
        vista.getBtnRegistrarVenta().addActionListener(e -> registrarVenta());
        
        
        vista.getTblCarrito().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    eliminarDelCarrito();
                }
            }
        });
    }
    
    private void buscarCliente() {
        String dni = vista.getTxtBuscarClienteDNI().getText().trim();
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese un DNI.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        clienteActual = clienteDAO.buscarPorDni(dni);
        
        if (clienteActual != null) {
            vista.getLblNombreCliente().setText("Cliente: " + clienteActual.getNombre());
        } else {
            vista.getLblNombreCliente().setText("Cliente: (No encontrado)");
            JOptionPane.showMessageDialog(vista, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregarAlCarrito() {
        
        Producto producto;
        try {
            int idProducto = Integer.parseInt(vista.getTxtBuscarProducto().getText().trim());
            producto = productoDAO.buscarPorId(idProducto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Ingrese un ID de producto válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (producto == null) {
            JOptionPane.showMessageDialog(vista, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

       
        int cantidad = (Integer) vista.getSpinCantidadProducto().getValue();
        
        
        if (cantidad > producto.getStockActual()) {
            JOptionPane.showMessageDialog(vista, "Stock insuficiente. Stock actual: " + producto.getStockActual(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        Salida item = new Salida();
        item.setIdProducto(producto.getIdProducto());
        item.setNombreProducto(producto.getNombre()); 
        item.setCantidad(cantidad);
        item.setPrecioVenta(producto.getPrecioUnitario());
        item.setImporteTotal(producto.getPrecioUnitario() * cantidad);
        
        
        carrito.add(item);
        modeloCarrito.addRow(new Object[]{
            item.getIdProducto(),
            item.getNombreProducto(),
            item.getCantidad(),
            item.getPrecioVenta(),
            item.getImporteTotal()
        });
        
        actualizarTotal();
        
        
        vista.getTxtBuscarProducto().setText("");
        vista.getSpinCantidadProducto().setValue(1);
    }

    private void eliminarDelCarrito() {
        int filaSeleccionada = vista.getTblCarrito().getSelectedRow();
        if (filaSeleccionada == -1) return;
        
        carrito.remove(filaSeleccionada);
        modeloCarrito.removeRow(filaSeleccionada);
        
        actualizarTotal();
    }
    
    private void actualizarTotal() {
        double total = 0.0;
        for (Salida item : carrito) {
            total += item.getImporteTotal();
        }
        vista.getLblTotalVenta().setText(String.format("Total: S/ %.2f", total));
    }
    
    private void registrarVenta() {
        
        if (clienteActual == null) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El carrito está vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
       
        double totalVenta = 0.0;
        for (Salida item : carrito) {
            totalVenta += item.getImporteTotal();
        }
        double subtotal = totalVenta / 1.18;
        double igv = totalVenta - subtotal;
        
        
        Factura factura = new Factura();
        factura.setIdCliente(clienteActual.getIdCliente());
        factura.setFecha(new Date()); 
        factura.setNumeroFactura(generarNumeroFactura()); 
        factura.setSubtotal(subtotal);
        factura.setIgv(igv);
        factura.setTotal(totalVenta);
        
        
        boolean exito = salidaDAO.registrarVenta(factura, carrito);
        
        if (exito) {
            JOptionPane.showMessageDialog(vista, "Venta registrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormularioVenta();
            cargarHistorial();
            
        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar la venta. Transacción revertida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormularioVenta() {
        clienteActual = null;
        carrito.clear();
        modeloCarrito.setRowCount(0);
        vista.getLblNombreCliente().setText("Cliente: (No seleccionado)");
        vista.getTxtBuscarClienteDNI().setText("");
        actualizarTotal();
    }
    
    private void cargarHistorial() {
        modeloHistorial.setRowCount(0);
        List<Factura> facturas = salidaDAO.listarUltimasFacturas(20);
        
        for (Factura f : facturas) {
            modeloHistorial.addRow(new Object[]{
                f.getNumeroFactura(),
                f.getNombreCliente(),
                new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(f.getFecha()),
                String.format("%.2f", f.getTotal())
            });
        }
    }
    
    
    private String generarNumeroFactura() {
        
        return "F001-" + (System.currentTimeMillis() / 1000);
    }
}
