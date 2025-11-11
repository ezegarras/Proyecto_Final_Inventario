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
import modelo.Categoria;
import utils.ConexionBD;

/**
 *
 * @author Enrique Zegarra
 */

public class CategoriaDAOImpl implements ICategoriaDAO {

    @Override
    public List<Categoria> listarTodas() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM Categoria ORDER BY nombre";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Categoria cat = new Categoria();
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setNombre(rs.getString("nombre"));
                categorias.add(cat);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar categorías: " + e.getMessage());
        }
        return categorias;
    }
    
    @Override
    public boolean insertar(Categoria categoria) {
        String sql = "INSERT INTO Categoria (nombre) VALUES (?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, categoria.getNombre());
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar categoría: " + e.getMessage());
            return false;
        }
    }
}
