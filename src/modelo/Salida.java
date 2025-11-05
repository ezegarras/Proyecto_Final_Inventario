/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Enrique Zegarra
 */

package modelo;

public class Salida {
    private int idSalida;
    private int idFactura;
    private int idProducto;
    private int cantidad;
    private double precioVenta; // Precio al momento de la venta
    private double importeTotal;
    
    
    private String nombreProducto;

    
    public int getIdSalida() { return idSalida; }
    public void setIdSalida(int idSalida) { this.idSalida = idSalida; }
    public int getIdFactura() { return idFactura; }
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }
    public double getImporteTotal() { return importeTotal; }
    public void setImporteTotal(double importeTotal) { this.importeTotal = importeTotal; }
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
}
