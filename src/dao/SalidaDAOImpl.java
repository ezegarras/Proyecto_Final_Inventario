/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Enrique Zegarra
 */

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Factura;
import modelo.Salida;
import utils.ConexionBD;

public class SalidaDAOImpl implements ISalidaDAO {

    @Override
    public boolean registrarVenta(Factura factura, List<Salida> detalle) {
        Connection con = null;
        String sqlFactura = "INSERT INTO Factura (numero_factura, fecha, id_cliente, igv, subtotal, total) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";
        String sqlSalida = "INSERT INTO Salida (id_factura, id_producto, cantidad, precio_venta, importe_total) " +
                           "VALUES (?, ?, ?, ?, ?)";
        String sqlStock = "UPDATE Producto SET stock_actual = stock_actual - ? WHERE id_producto = ?";

        try {
            con = ConexionBD.getConexion();
            // --- INICIO DE TRANSACCIÓN ---
            con.setAutoCommit(false);
            
            // 1. Insertar la Factura (cabecera) y obtener el ID generado
            int idFacturaGenerada = -1;
            try (PreparedStatement psFactura = con.prepareStatement(sqlFactura, Statement.RETURN_GENERATED_KEYS)) {
                psFactura.setString(1, factura.getNumeroFactura());
                psFactura.setTimestamp(2, new java.sql.Timestamp(factura.getFecha().getTime()));
                psFactura.setInt(3, factura.getIdCliente());
                psFactura.setDouble(4, factura.getIgv());
                psFactura.setDouble(5, factura.getSubtotal());
                psFactura.setDouble(6, factura.getTotal());
                
                psFactura.executeUpdate();
                
                try (ResultSet rs = psFactura.getGeneratedKeys()) {
                    if (rs.next()) {
                        idFacturaGenerada = rs.getInt(1);
                    } else {
                        throw new SQLException("Fallo al crear la factura, no se obtuvo ID.");
                    }
                }
            }
            
            // 2. Insertar cada Salida (detalle) y Actualizar Stock
            try (PreparedStatement psSalida = con.prepareStatement(sqlSalida);
                 PreparedStatement psStock = con.prepareStatement(sqlStock)) {
                
                for (Salida item : detalle) {
                    // 2a. Insertar en Salida
                    psSalida.setInt(1, idFacturaGenerada);
                    psSalida.setInt(2, item.getIdProducto());
                    psSalida.setInt(3, item.getCantidad());
                    psSalida.setDouble(4, item.getPrecioVenta());
                    psSalida.setDouble(5, item.getImporteTotal());
                    psSalida.addBatch(); // Añadir al lote
                    
                    // 2b. Actualizar Stock
                    psStock.setInt(1, item.getCantidad());
                    psStock.setInt(2, item.getIdProducto());
                    psStock.addBatch(); // Añadir al lote
                }
                
                psSalida.executeBatch(); // Ejecutar lote de Salidas
                psStock.executeBatch(); // Ejecutar lote de Stock
            }
            
            // 3. Si todo fue bien, confirmar la transacción
            con.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Error en la transacción de venta: " + e.getMessage());
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    @Override
    public List<Factura> listarUltimasFacturas(int limite) {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT f.*, c.nombre as cliente_nombre " +
                     "FROM Factura f " +
                     "JOIN Cliente c ON f.id_cliente = c.id_cliente " +
                     "ORDER BY f.fecha DESC LIMIT ?";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Factura f = new Factura();
                    f.setIdFactura(rs.getInt("id_factura"));
                    f.setNumeroFactura(rs.getString("numero_factura"));
                    f.setFecha(rs.getTimestamp("fecha"));
                    f.setTotal(rs.getDouble("total"));
                    f.setIdCliente(rs.getInt("id_cliente"));
                    f.setNombreCliente(rs.getString("cliente_nombre"));
                    facturas.add(f);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar últimas facturas: " + e.getMessage());
        }
        return facturas;
    }
}
