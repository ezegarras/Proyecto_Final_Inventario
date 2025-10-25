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
        // ATENCIÓN: Esta consulta compara contraseñas en texto plano.
        // ¡Debemos cambiar esto por un HASH (ej. SHA-256) en el futuro!
        String sql = "SELECT * FROM Usuario WHERE usuario = ? AND contrasena = ?";
        
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
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error en el login: " + e.getMessage());
        }
        
        return user;
    }
}
