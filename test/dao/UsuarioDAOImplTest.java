/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dao;

import modelo.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para el Login.
 */
public class UsuarioDAOImplTest {
    
    private UsuarioDAOImpl instance;
    
    public UsuarioDAOImplTest() {
    }
    
    @Before 
    public void setUp() {
        instance = new UsuarioDAOImpl();
    }
    
    @After 
    public void tearDown() {
        instance = null;
    }

    /**
     * Prueba del caso CP01: Login Exitoso
     */
    @Test
    public void testLoginExitoso() {
        System.out.println("Prueba: Login Exitoso");
        
     
        String usuario = "admin";
        String contrasena = "admin"; 
        
        Usuario result = instance.login(usuario, contrasena);
        
        assertNotNull("El objeto usuario no debe ser null", result);
        assertEquals("El usuario debe ser admin", "admin", result.getUsuario());
        
    }

    /**
     * Prueba del caso CP02: Login Fallido
     */
    @Test
    public void testLoginFallido() {
        System.out.println("Prueba: Login Fallido");
        
        String usuario = "admin";
        String contrasena = "CLAVE_ERRONEA_123";
        
        Usuario result = instance.login(usuario, contrasena);
        
        assertNull("El usuario debe ser null si la clave falla", result);
    }
    
    /**
     * Prueba de conexión (Listar)
     */
    @Test
    public void testListarTodos() {
        System.out.println("Prueba: Listar Usuarios");
        java.util.List<Usuario> lista = instance.listarTodos();
        
        assertNotNull("La lista no debe ser null", lista);
        assertTrue("Debe haber al menos 1 usuario en la BD", lista.size() > 0);
    }
}
