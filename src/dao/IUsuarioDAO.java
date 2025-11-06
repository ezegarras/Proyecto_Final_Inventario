/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Usuario;

/**
 *
 * @author Enrique Zegarra
 */
public interface IUsuarioDAO {
    Usuario login(String usuario, String contrasena);
    List<Usuario> listarTodos();
    boolean insertar(Usuario usuario);
    boolean actualizar(Usuario usuario);
    boolean eliminar(int idUsuario);
    
}
