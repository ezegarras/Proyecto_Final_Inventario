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
import modelo.Usuario;
import utils.ConexionBD;
import utils.LogService;

/**
 *
 * @author Enrique Zegarra
 */
public class UsuarioDAOImpl implements IUsuarioDAO {

    @Override
    public Usuario login(String usuario, String contrasena) {
        Usuario user = null;

        String sql = "SELECT u.*, r.nombre as rol_nombre " +
             "FROM Usuario u " +
             "JOIN Rol r ON u.id_rol = r.id_rol " +
             "WHERE u.usuario = ? AND u.contrasena = ?";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new Usuario();
                    user.setIdUsuario(rs.getInt("id_usuario"));
                    user.setNombre(rs.getString("nombre"));
                    user.setUsuario(rs.getString("usuario"));
                    user.setIdRol(rs.getInt("id_rol"));
                    user.setRolNombre(rs.getString("rol_nombre"));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error en el login: " + e.getMessage());
             LogService.error("Error en el login: ", e);
        }
        
        return user;
    }
    
    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, r.nombre as rol_nombre FROM Usuario u JOIN Rol r ON u.id_rol = r.id_rol ORDER BY u.nombre";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setUsuario(rs.getString("usuario"));
                u.setContrasena(rs.getString("contrasena"));
                u.setIdRol(rs.getInt("id_rol"));
                u.setRolNombre(rs.getString("rol_nombre"));
                usuarios.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
             LogService.error("Error al listar usuarios: ", e);
        }
        return usuarios;
    }

    @Override
    public boolean insertar(Usuario u) {
        String sql = "INSERT INTO Usuario (nombre, usuario, contrasena, id_rol) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getContrasena());
            ps.setInt(4, u.getIdRol());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
             LogService.error("Error al insertar usuario: ", e);
            return false;
        }
    }

    @Override
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE Usuario SET nombre=?, usuario=?, contrasena=?, id_rol=? WHERE id_usuario=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getContrasena());
            ps.setInt(4, u.getIdRol());
            ps.setInt(5, u.getIdUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
             LogService.error("Error al actualizar usuario: ", e);
            return false;
        }
    }

    @Override
    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM Usuario WHERE id_usuario=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
             LogService.error("Error al eliminar usuario: ", e);
            return false;
        }
    }
}
