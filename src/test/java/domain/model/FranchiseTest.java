package domain.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FranchiseTest {
    
    private Franchise franchise;
    private Address address;
    private Manager manager;
    private Seller seller;
    private Order order;
    
    @Before
    public void setUp() {
        address = new Address("ADDR001", "Rua das Flores", "123", "Centro", "São Paulo", "SP", "01234-567", "Brasil");
        franchise = new Franchise("FRANCHISE001", "Franquia Centro", address);
        manager = new Manager("João Gerente", "joao@empresa.com", "123456");
        seller = new Seller("Maria Vendedora", "maria@empresa.com", "123456");
        order = new Order("ORDER001", "SELLER001", "CUSTOMER001", "FRANCHISE001");
    }
    
    @Test
    public void testFranchiseCreation() {
        assertNotNull(franchise);
        assertEquals("FRANCHISE001", franchise.getId());
        assertEquals("Franquia Centro", franchise.getName());
        assertEquals(address, franchise.getAddress());
        assertEquals(0.0, franchise.getRevenueAccumulated(), 0.01);
        assertNull(franchise.getManager());
        assertTrue(franchise.getSellers().isEmpty());
        assertTrue(franchise.getOrders().isEmpty());
    }
    
    @Test
    public void testAddSeller() {
        franchise.addSeller(seller);
        assertEquals(1, franchise.getSellers().size());
        assertTrue(franchise.getSellers().contains(seller));
    }
    
    @Test
    public void testRemoveSeller() {
        franchise.addSeller(seller);
        assertEquals(1, franchise.getSellers().size());
        
        franchise.removeSeller(seller);
        assertEquals(0, franchise.getSellers().size());
        assertFalse(franchise.getSellers().contains(seller));
    }
    
    @Test
    public void testAddOrder() {
        // Simular um pedido com valor
        order.addItem(new Product("PROD001", "FRANCHISE001", "Produto Teste", "Descrição", 100.0, 10), "Produto Teste", 2, 100.0);
        
        franchise.addOrder(order);
        assertEquals(1, franchise.getOrders().size());
        assertTrue(franchise.getOrders().contains(order));
        assertEquals(200.0, franchise.getRevenueAccumulated(), 0.01);
    }
    
    @Test
    public void testGetTotalOrders() {
        assertEquals(0, franchise.getTotalOrders());
        
        franchise.addOrder(order);
        assertEquals(1, franchise.getTotalOrders());
        
        Order order2 = new Order("ORDER002", "SELLER001", "CUSTOMER001", "FRANCHISE001");
        franchise.addOrder(order2);
        assertEquals(2, franchise.getTotalOrders());
    }
    
    @Test
    public void testGetAverageTicket() {
        // Sem pedidos
        assertEquals(0.0, franchise.getAverageTicket(), 0.01);
        
        // Com um pedido
        order.addItem(new Product("PROD001", "FRANCHISE001", "Produto Teste", "Descrição", 100.0, 10), "Produto Teste", 2, 100.0);
        franchise.addOrder(order);
        assertEquals(200.0, franchise.getAverageTicket(), 0.01);
        
        // Com dois pedidos
        Order order2 = new Order("ORDER002", "SELLER001", "CUSTOMER001", "FRANCHISE001");
        order2.addItem(new Product("PROD002", "FRANCHISE001", "Produto Teste 2", "Descrição", 50.0, 5), "Produto Teste 2", 1, 50.0);
        franchise.addOrder(order2);
        assertEquals(125.0, franchise.getAverageTicket(), 0.01); // (200 + 50) / 2
    }
    
    @Test
    public void testSetManager() {
        assertNull(franchise.getManager());
        
        franchise.setManager(manager);
        assertEquals(manager, franchise.getManager());
    }
    
    @Test
    public void testToString() {
        String result = franchise.toString();
        assertTrue(result.contains("FRANCHISE001"));
        assertTrue(result.contains("Franquia Centro"));
        assertTrue(result.contains("sellersCount=0"));
        assertTrue(result.contains("ordersCount=0"));
    }
    
    @Test
    public void testEqualsAndHashCode() {
        Franchise franchise2 = new Franchise("FRANCHISE001", "Outro Nome", address);
        Franchise franchise3 = new Franchise("FRANCHISE002", "Franquia Centro", address);
        
        assertEquals(franchise, franchise2);
        assertNotEquals(franchise, franchise3);
        assertEquals(franchise.hashCode(), franchise2.hashCode());
        assertNotEquals(franchise.hashCode(), franchise3.hashCode());
    }
} 