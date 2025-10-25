/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;
import utils.ConexionBD;

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
        }
        
        return user;
    }
}
