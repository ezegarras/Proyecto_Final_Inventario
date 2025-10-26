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
import modelo.Entrada;
import utils.ConexionBD;

/**
 *
 * @author Enrique Zegarra
 */

public class EntradaDAOImpl implements IEntradaDAO {

    @Override
    public boolean registrarEntrada(Entrada entrada) {
        Connection con = null;
        String sqlInsertarEntrada = "INSERT INTO Entrada (fecha, cantidad, id_producto, id_proveedor) VALUES (?, ?, ?, ?)";
        String sqlActualizarStock = "UPDATE Producto SET stock_actual = stock_actual + ? WHERE id_producto = ?";
        
        try {
            con = ConexionBD.getConexion();
            // --- INICIO DE LA TRANSACCIÓN ---
            con.setAutoCommit(false); 
            
            // 1. Insertar en la tabla Entrada
            try (PreparedStatement psEntrada = con.prepareStatement(sqlInsertarEntrada)) {
                psEntrada.setDate(1, new java.sql.Date(entrada.getFecha().getTime()));
                psEntrada.setInt(2, entrada.getCantidad());
                psEntrada.setInt(3, entrada.getIdProducto());
                psEntrada.setInt(4, entrada.getIdProveedor());
                psEntrada.executeUpdate();
            }
            
            // 2. Actualizar el stock en la tabla Producto
            try (PreparedStatement psStock = con.prepareStatement(sqlActualizarStock)) {
                psStock.setInt(1, entrada.getCantidad());
                psStock.setInt(2, entrada.getIdProducto());
                psStock.executeUpdate();
            }
            
            // 3. Si todo salió bien, confirmar la transacción
            con.commit();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error en la transacción de entrada: " + e.getMessage());
            // 4. Si algo falló, revertir todo
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            // 5. Devolver la conexión al modo AutoCommit y cerrarla
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close(); // Cerramos la conexión manualmente (ya no usamos try-with-resources)
                }
            } catch (SQLException e) {
                System.err.println("Error al restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    @Override
    public List<Entrada> listarUltimas(int limite) {
        List<Entrada> entradas = new ArrayList<>();
        String sql = "SELECT e.fecha, e.cantidad, p.nombre as producto_nombre, pr.razon_social as proveedor_nombre " +
                     "FROM Entrada e " +
                     "JOIN Producto p ON e.id_producto = p.id_producto " +
                     "JOIN Proveedor pr ON e.id_proveedor = pr.id_proveedor " +
                     "ORDER BY e.fecha DESC LIMIT ?";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, limite);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Entrada e = new Entrada();
                    e.setFecha(rs.getDate("fecha"));
                    e.setCantidad(rs.getInt("cantidad"));
                    e.setNombreProducto(rs.getString("producto_nombre"));
                    e.setNombreProveedor(rs.getString("proveedor_nombre"));
                    entradas.add(e);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar últimas entradas: " + e.getMessage());
        }
        return entradas;
    }
}
