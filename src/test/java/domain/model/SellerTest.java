package domain.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class SellerTest {
    
    @Test
    public void testSellerCreation() {
        Seller seller = new Seller( "João Vendedor", "joao@empresa.com", "123456", "1", "1");
        
        assertEquals("João Vendedor", seller.getName());
        assertEquals("joao@empresa.com", seller.getEmail());
        assertEquals(0, seller.getTotalSalesCount());
        assertEquals(0.0, seller.getTotalSalesAmount(), 0.01);
    }
    
    @Test
    public void testAddSale() {
        Seller seller = new Seller("Maria Vendedora", "maria@empresa.com", "123456", "1", "1");
        
        seller.addSale(150.50);
        assertEquals(1, seller.getTotalSalesCount());
        assertEquals(150.50, seller.getTotalSalesAmount(), 0.01);
        
        seller.addSale(75.25);
        assertEquals(2, seller.getTotalSalesCount());
        assertEquals(225.75, seller.getTotalSalesAmount(), 0.01);
    }
    
    @Test
    public void testSellerInheritance() {
        Seller seller = new Seller("Pedro Vendedor", "pedro@empresa.com", "123456", "1", "1");
        
        // Verifica se Seller herda de User
        assertTrue(seller instanceof User);
    }
    
    @Test
    public void testAuthentication() {
        Seller seller = new Seller("Ana Vendedora", "ana@empresa.com", "senha123","1", "1");
        
        assertTrue(seller.authenticate("senha123"));
        assertFalse(seller.authenticate("senhaerrada"));
    }
} 