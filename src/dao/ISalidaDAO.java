/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

/**
 *
 * @author Enrique Zegarra
 */

import java.util.List;
import modelo.Factura;
import modelo.Producto;
import modelo.Salida;
import modelo.VentaDiariaDTO;

public interface ISalidaDAO {
    
    boolean registrarVenta(Factura factura, List<Salida> detalle);
    
    List<Factura> listarUltimasFacturas(int limite);
    
    //Obtiene una lista de productos ordenados por la cantidad total vendida
    List<Producto> listarProductosMasVendidos(int limite);
    
    
    //Obtiene la suma de cantidades vendidas por día de los últimos 7 días.
    List<VentaDiariaDTO> getVentasUltimos7Dias();
}
