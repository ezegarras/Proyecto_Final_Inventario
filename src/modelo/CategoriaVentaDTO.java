/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Enrique Zegarra
 */
package modelo;

// DTO para el gráfico circular
public class CategoriaVentaDTO {
    private String nombreCategoria;
    private int totalVendido;

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }
    public int getTotalVendido() { return totalVendido; }
    public void setTotalVendido(int totalVendido) { this.totalVendido = totalVendido; }
}
