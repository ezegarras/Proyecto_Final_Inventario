/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Entrada;

/**
 *
 * @author Enrique Zegarra
 */

public interface IEntradaDAO {
    /**
     * Inserta un nuevo registro de entrada Y actualiza el stock del producto.
     * Esto debe ser una TRANSACCIÓN.
     * @param entrada La entrada a registrar.
     * @return true si la transacción fue exitosa.
     */
    boolean registrarEntrada(Entrada entrada);
    
    /**
     * Lista las últimas N entradas con los nombres (JOIN).
     * @param limite El número de entradas a mostrar.
     * @return Lista de entradas.
     */
    List<Entrada> listarUltimas(int limite);
}
