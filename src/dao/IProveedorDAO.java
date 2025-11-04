/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Proveedor;

/**
 *
 * @author Enrique Zegarra
 */
public interface IProveedorDAO {
    List<Proveedor> listarTodos();
    boolean insertar(Proveedor proveedor);
    boolean actualizar(Proveedor proveedor);
    boolean eliminar(int idProveedor);
}
