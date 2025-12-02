/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Factura;
import modelo.Salida;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para el módulo de Ventas (Transacciones).
 */
public class SalidaDAOImplTest {
    
    private SalidaDAOImpl instance;
    
    public SalidaDAOImplTest() {
    }
    
    @Before
    public void setUp() {
        instance = new SalidaDAOImpl();
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Caso de Prueba CP-05: Registro de Venta Completa.
     * Verifica que la transacción (Factura + Detalle + Stock) funcione.
     */
    @Test
    public void testRegistrarVenta() {
        System.out.println("Prueba: Registrar Venta (Transacción)");
        
        Factura factura = new Factura();
        factura.setNumeroFactura("TEST-" + System.currentTimeMillis());
        factura.setFecha(new Date());
        factura.setIdCliente(1); 
        factura.setSubtotal(100.00);
        factura.setIgv(18.00);
        factura.setTotal(118.00);
        
       
        List<Salida> detalle = new ArrayList<>();
        
        Salida item1 = new Salida();
        item1.setIdProducto(1); 
        item1.setCantidad(1);
        item1.setPrecioVenta(100.00);
        item1.setImporteTotal(100.00);
        
        detalle.add(item1);
        
        boolean resultado = instance.registrarVenta(factura, detalle);
        
        assertTrue("La venta debería registrarse correctamente (return true)", resultado);
        
        if (resultado) {
            System.out.println("Exito. Venta registrada y stock descontado.");
        } else {
            System.out.println("Fallo. La transacción hizo rollback.");
        }
    }
}
