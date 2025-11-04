/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

/**
 *
 * @author Enrique Zegarra
 */

import java.util.List;
import modelo.Cliente;

public interface IClienteDAO {
    List<Cliente> listarTodos();
    boolean insertar(Cliente cliente);
    boolean actualizar(Cliente cliente);
    boolean eliminar(int idCliente);
    Cliente buscarPorDni(String dni);
}
