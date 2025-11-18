/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Enrique Zegarra
 */
package modelo;

/**
 * DTO para el reporte de Ventas por Cliente.
 */
public class ClienteReporteDTO {
    
    private String dni;
    private String nombreCliente;
    private int numeroCompras;
    private double montoTotal;

    // Getters y Setters
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public int getNumeroCompras() { return numeroCompras; }
    public void setNumeroCompras(int numeroCompras) { this.numeroCompras = numeroCompras; }
    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }
}
