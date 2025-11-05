/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

/**
 *
 * @author Enrique Zegarra
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Factura {
    private int idFactura;
    private String numeroFactura;
    private Date fecha;
    private int idCliente;
    private double igv;
    private double subtotal;
    private double total;
    
    
    private String nombreCliente;
    
    // Lista de productos de la factura
    private List<Salida> detalle = new ArrayList<>();

    public int getIdFactura() { return idFactura; }
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }
    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public double getIgv() { return igv; }
    public void setIgv(double igv) { this.igv = igv; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public List<Salida> getDetalle() { return detalle; }
    public void setDetalle(List<Salida> detalle) { this.detalle = detalle; }
}
