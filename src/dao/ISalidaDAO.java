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
import modelo.Salida;

public interface ISalidaDAO {
    
    boolean registrarVenta(Factura factura, List<Salida> detalle);
    
    List<Factura> listarUltimasFacturas(int limite);
}
