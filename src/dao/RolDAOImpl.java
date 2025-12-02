/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Rol;
import utils.ConexionBD;
import utils.LogService;

/**
 *
 * @author Enrique Zegarra
 */

public class RolDAOImpl implements IRolDAO {
    
@Override
    public List<Rol> listarTodos() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM Rol ORDER BY id_rol";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Rol r = new Rol();
                r.setIdRol(rs.getInt("id_rol"));
                r.setNombre(rs.getString("nombre"));
                roles.add(r);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar roles: " + e.getMessage());
            LogService.error("Error al listar roles: ", e);
        }
        return roles;
    }
}
