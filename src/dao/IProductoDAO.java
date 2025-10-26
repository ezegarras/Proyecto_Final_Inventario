/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Producto;

/**
 *
 * @author Enrique Zegarra
 */
public interface IProductoDAO {
    
    /**
     * Lista productos, permitiendo filtrar por nombre/categoría y stock bajo.
     * @param busqueda Término de búsqueda (para nombre o categoría).
     * @param soloStockBajo Si es true, filtra por stock_actual <= stock_minimo.
     * @return Lista de productos filtrada.
     */
    List<Producto> listarProductos(String busqueda, boolean soloStockBajo);
    
    /**
     * Elimina un producto de la base de datos usando su ID.
     * @param idProducto El ID del producto a eliminar.
     * @return true si se eliminó, false si hubo un error.
     */
    boolean eliminar(int idProducto);
    
   
    Producto buscarPorId(int idProducto);
    boolean actualizar(Producto producto);
    
    /**
     * Inserta un nuevo producto en la BD.
     * Devuelve el ID generado.
     * @param producto El producto a insertar (sin ID).
     * @return El ID autogenerado del nuevo producto, o -1 si falla.
     */
    int insertar(Producto producto);
}
