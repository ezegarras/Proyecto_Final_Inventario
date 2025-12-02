/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Proveedor;
import utils.ConexionBD;
import utils.LogService;

/**
 *
 * @author Enrique Zegarra
 */

public class ProveedorDAOImpl implements IProveedorDAO {

    @Override
    public List<Proveedor> listarTodos() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor ORDER BY razon_social";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Proveedor p = new Proveedor();
                p.setIdProveedor(rs.getInt("id_proveedor"));
                p.setRazonSocial(rs.getString("razon_social"));
                p.setRuc(rs.getString("ruc"));
                proveedores.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar proveedores: " + e.getMessage());
            LogService.error("Error al listar proveedores: ", e);
            
        }
        return proveedores;
    }
    
    @Override
    public boolean insertar(Proveedor p) {
        String sql = "INSERT INTO Proveedor (razon_social, ruc, celular, correo, direccion) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, p.getRazonSocial());
            ps.setString(2, p.getRuc());
            ps.setString(3, p.getCelular());
            ps.setString(4, p.getCorreo());
            ps.setString(5, p.getDireccion());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar proveedor: " + e.getMessage());
            LogService.error("Error al insertar proveedor: ", e);
            return false;
        }
    }

    @Override
    public boolean actualizar(Proveedor p) {
        String sql = "UPDATE Proveedor SET razon_social = ?, ruc = ?, celular = ?, " +
                     "correo = ?, direccion = ? WHERE id_proveedor = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, p.getRazonSocial());
            ps.setString(2, p.getRuc());
            ps.setString(3, p.getCelular());
            ps.setString(4, p.getCorreo());
            ps.setString(5, p.getDireccion());
            ps.setInt(6, p.getIdProveedor());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar proveedor: " + e.getMessage());
            LogService.error("Error al actualizar proveedor: ", e);
            return false;
        }
    }

    @Override
    public boolean eliminar(int idProveedor) {
        String sql = "DELETE FROM Proveedor WHERE id_proveedor = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idProveedor);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
            LogService.error("Error al eliminar proveedor: ", e);
            if (e.getErrorCode() == 1451) { // Error de Foreign Key
                JOptionPane.showMessageDialog(null, 
                    "No se puede eliminar. El proveedor tiene entradas registradas.",
                    "Error de Borrado", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }
}
