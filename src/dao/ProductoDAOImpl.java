/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Producto;
import utils.ConexionBD;

/**
 *
 * @author Enrique Zegarra
 */

public class ProductoDAOImpl implements IProductoDAO {
    
    @Override
    public int insertar(Producto producto) {
        String sql = "INSERT INTO Producto (nombre, precio_unitario, stock_actual, " +
                     "stock_minimo, id_categoria) VALUES (?, ?, ?, ?, ?)";
        
        // Usamos Statement.RETURN_GENERATED_KEYS para obtener el ID
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, 
                                     java.sql.Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecioUnitario());
            ps.setInt(3, producto.getStockActual());
            ps.setInt(4, producto.getStockMinimo());
            ps.setInt(5, producto.getIdCategoria());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Obtenemos el ID generado
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Devuelve el ID
                    }
                }
            }
            return -1; // Falló la inserción
            
        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            return -1;
        }
    }

@Override
public List<Producto> listarProductos(String busqueda, boolean soloStockBajo) {
    List<Producto> productos = new ArrayList<>();

    // 1. Construimos una consulta SQL dinámica
    StringBuilder sql = new StringBuilder(
        "SELECT p.*, c.nombre AS nombre_categoria " +
        "FROM Producto p " +
        "JOIN Categoria c ON p.id_categoria = c.id_categoria "
    );

    List<Object> params = new ArrayList<>(); // Lista para los parámetros

    // Añadir filtros (WHERE)
    boolean hasWhere = false;

    if (busqueda != null && !busqueda.trim().isEmpty()) {
        sql.append(" WHERE (p.nombre LIKE ? OR c.nombre LIKE ?)");
        String busquedaLike = "%" + busqueda.trim() + "%";
        params.add(busquedaLike);
        params.add(busquedaLike);
        hasWhere = true;
    }

    if (soloStockBajo) {
        sql.append(hasWhere ? " AND " : " WHERE ");
        sql.append("p.stock_actual <= p.stock_minimo");
        hasWhere = true; // No es necesario, pero es buena práctica
    }

    sql.append(" ORDER BY p.id_producto");

    // 2. Ejecutar la consulta
    try (Connection con = ConexionBD.getConexion();
         PreparedStatement ps = con.prepareStatement(sql.toString())) {

        // 3. Asignar los parámetros dinámicos
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecioUnitario(rs.getDouble("precio_unitario"));
                p.setStockActual(rs.getInt("stock_actual"));
                p.setStockMinimo(rs.getInt("stock_minimo"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setNombreCategoria(rs.getString("nombre_categoria"));
                productos.add(p);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al listar productos: " + e.getMessage());
    }
    return productos;
}

@Override
    public Producto buscarPorId(int idProducto) {
        Producto p = null;
        // Obtenemos el producto CON el nombre de su categoría
        String sql = "SELECT p.*, c.nombre AS nombre_categoria " +
                     "FROM Producto p " +
                     "JOIN Categoria c ON p.id_categoria = c.id_categoria " +
                     "WHERE p.id_producto = ?";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idProducto);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Producto();
                    p.setIdProducto(rs.getInt("id_producto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    p.setStockActual(rs.getInt("stock_actual"));
                    p.setStockMinimo(rs.getInt("stock_minimo"));
                    p.setIdCategoria(rs.getInt("id_categoria"));
                    p.setNombreCategoria(rs.getString("nombre_categoria"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar producto por ID: " + e.getMessage());
        }
        return p;
    }

    @Override
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE Producto SET nombre = ?, precio_unitario = ?, " +
                     "stock_actual = ?, stock_minimo = ?, id_categoria = ? " +
                     "WHERE id_producto = ?";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecioUnitario());
            ps.setInt(3, producto.getStockActual());
            ps.setInt(4, producto.getStockMinimo());
            ps.setInt(5, producto.getIdCategoria());
            ps.setInt(6, producto.getIdProducto());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

@Override
public boolean eliminar(int idProducto) {
    // NOTA: Primero deberíamos verificar si el producto tiene
    // salidas o entradas (integridad referencial).
    // Por ahora, lo borraremos directamente.
    String sql = "DELETE FROM Producto WHERE id_producto = ?";

    try (Connection con = ConexionBD.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idProducto);
        int filasAfectadas = ps.executeUpdate();
        return filasAfectadas > 0;

    } catch (SQLException e) {
        System.err.println("Error al eliminar producto: " + e.getMessage());
        // Manejar error de integridad referencial (si el producto está en una 'Salida')
        if (e.getErrorCode() == 1451) { // Código de error MySQL para FK constraint
            javax.swing.JOptionPane.showMessageDialog(null, 
                "No se puede eliminar el producto porque tiene ventas (salidas) registradas.",
                "Error de Borrado", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
    }
