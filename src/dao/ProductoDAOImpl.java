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
    public List<Producto> listarParaCombos() {
        List<Producto> productos = new ArrayList<>();
        // Traemos solo lo necesario para el combo de ventas
        String sql = "SELECT id_producto, nombre, stock_actual, precio_unitario " +
                     "FROM Producto " +
                     "WHERE stock_actual > 0 " + // Opcional: solo mostrar productos con stock
                     "ORDER BY nombre ASC";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setStockActual(rs.getInt("stock_actual"));
                p.setPrecioUnitario(rs.getDouble("precio_unitario"));
                productos.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos para combos: " + e.getMessage());
        }
        return productos;
    }
    
    @Override
    public int contarProductos(String busqueda, boolean soloStockBajo) {
    // Esta consulta debe tener EXACTAMENTE los mismos JOIN y WHERE
    // que listarProductos, pero solo cuenta.

    StringBuilder sql = new StringBuilder(
        "SELECT COUNT(p.id_producto) " +
        "FROM Producto p " +
        "JOIN Categoria c ON p.id_categoria = c.id_categoria "
    );

    List<Object> params = new ArrayList<>();
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
    }

    try (Connection con = ConexionBD.getConexion();
         PreparedStatement ps = con.prepareStatement(sql.toString())) {

        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1); // Devuelve el conteo
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al contar productos: " + e.getMessage());
    }
    return 0;
    }
    
    @Override
    public int insertar(Producto producto) {
        String sql = "INSERT INTO Producto (nombre, precio_unitario, stock_actual, " +
                     "stock_minimo, id_categoria) VALUES (?, ?, ?, ?, ?)";
        
       
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
            return -1;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            return -1;
        }
    }

@Override
public List<Producto> listarProductos(String busqueda, boolean soloStockBajo, int pagina, int tamanoPagina) {
    List<Producto> productos = new ArrayList<>();
    int offset = (pagina - 1) * tamanoPagina;

    // Construimos una consulta SQL dinámica
    StringBuilder sql = new StringBuilder(
        "SELECT p.*, c.nombre AS nombre_categoria " +
        "FROM Producto p " +
        "JOIN Categoria c ON p.id_categoria = c.id_categoria " 
    );

    List<Object> params = new ArrayList<>(); 
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
        hasWhere = true;
    }

    sql.append(" ORDER BY p.id_producto");
    sql.append(" LIMIT ? OFFSET ?");
    params.add(tamanoPagina);
    params.add(offset);
    
    try (Connection con = ConexionBD.getConexion();
         PreparedStatement ps = con.prepareStatement(sql.toString())) {

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

    String sql = "DELETE FROM Producto WHERE id_producto = ?";

    try (Connection con = ConexionBD.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idProducto);
        int filasAfectadas = ps.executeUpdate();
        return filasAfectadas > 0;

    } catch (SQLException e) {
        System.err.println("Error al eliminar producto: " + e.getMessage());
        if (e.getErrorCode() == 1451) {
            javax.swing.JOptionPane.showMessageDialog(null, 
                "No se puede eliminar el producto porque tiene ventas (salidas) registradas.",
                "Error de Borrado", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}

@Override
    public List<Producto> listarProductosConStockBajo() {
        List<Producto> productos = new ArrayList<>();
        
        String sql = "SELECT p.*, c.nombre AS nombre_categoria " +
                     "FROM Producto p " +
                     "JOIN Categoria c ON p.id_categoria = c.id_categoria " +
                     "WHERE p.stock_actual <= p.stock_minimo " +
                     "ORDER BY p.stock_actual ASC"; // Mostrar los más críticos primero
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
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
        } catch (SQLException e) {
            System.err.println("Error al listar productos con stock bajo: " + e.getMessage());
        }
        return productos;
    }
    }
