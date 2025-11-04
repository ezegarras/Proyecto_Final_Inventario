/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

/**
 *
 * @author Enrique Zegarra
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Cliente;
import utils.ConexionBD;

public class ClienteDAOImpl implements IClienteDAO {

    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente ORDER BY nombre";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                clientes.add(mapResultSetToCliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    @Override
    public boolean insertar(Cliente c) {
        String sql = "INSERT INTO Cliente (dni, nombre, direccion, celular, correo, edad, sexo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getCelular());
            ps.setString(5, c.getCorreo());
            ps.setInt(6, c.getEdad());
            ps.setString(7, c.getSexo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Cliente c) {
        String sql = "UPDATE Cliente SET dni = ?, nombre = ?, direccion = ?, celular = ?, " +
                     "correo = ?, edad = ?, sexo = ? WHERE id_cliente = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getCelular());
            ps.setString(5, c.getCorreo());
            ps.setInt(6, c.getEdad());
            ps.setString(7, c.getSexo());
            ps.setInt(8, c.getIdCliente());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idCliente) {
        String sql = "DELETE FROM Cliente WHERE id_cliente = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            if (e.getErrorCode() == 1451) {
                JOptionPane.showMessageDialog(null, 
                    "No se puede eliminar. El cliente tiene facturas registradas.",
                    "Error de Borrado", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }

    @Override
    public Cliente buscarPorDni(String dni) {
        String sql = "SELECT * FROM Cliente WHERE dni = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCliente(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por DNI: " + e.getMessage());
        }
        return null; // Si no lo encuentra regresa no encontrado
    }
    
    // Método helper privado para evitar repetir código (SRP)
    private Cliente mapResultSetToCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setDni(rs.getString("dni"));
        c.setNombre(rs.getString("nombre"));
        c.setDireccion(rs.getString("direccion"));
        c.setCelular(rs.getString("celular"));
        c.setCorreo(rs.getString("correo"));
        c.setEdad(rs.getInt("edad"));
        c.setSexo(rs.getString("sexo"));
        return c;
    }
}
