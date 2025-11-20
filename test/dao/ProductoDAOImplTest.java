/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

package dao;

import modelo.Producto;
// --- IMPORTACIONES JUNIT 4 ---
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Clase de Pruebas Unitarias para el DAO de Productos (Versión JUnit 4).
 */
public class ProductoDAOImplTest {
    
    private ProductoDAOImpl instance;
    
    public ProductoDAOImplTest() {
    }
    
    @Before // En JUnit 5 era @BeforeEach
    public void setUp() {
        System.out.println("Iniciando prueba...");
        instance = new ProductoDAOImpl();
    }
    
    @After // En JUnit 5 era @AfterEach
    public void tearDown() {
        instance = null;
    }

    /**
     * Caso de Prueba CP-03: Registro de Producto.
     */
    @Test
    public void testInsertarProducto() {
        System.out.println("Ejecutando: testInsertarProducto");
        
        Producto p = new Producto();
        // Usamos un nombre único con el tiempo para evitar duplicados en pruebas repetidas
        p.setNombre("Producto Test JUnit4 " + System.currentTimeMillis()); 
        p.setPrecioUnitario(100.00);
        p.setStockActual(10);
        p.setStockMinimo(5);
        p.setIdCategoria(1); 
        
        int resultadoId = instance.insertar(p);
        
        // Verificaciones
        assertTrue("El ID del producto insertado debería ser mayor a 0", resultadoId > 0);
        System.out.println("Exito. ID generado: " + resultadoId);
    }

    /**
     * Caso de Prueba: Listar Productos.
     */
    @Test
    public void testListarProductos() {
        System.out.println("Ejecutando: testListarProductos");
        
        // Probamos listar con paginación (10 productos de la página 1)
        List<Producto> resultado = instance.listarProductos("", false, 1, 10);
        
        assertNotNull("La lista no debería ser nula", resultado);
        assertTrue("La lista debería tener elementos (si ya insertaste datos)", resultado.size() > 0);
    }
}
