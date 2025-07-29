package models;

import org.junit.Test;
import static org.junit.Assert.*;

public class OwnerTest {
    @Test
    public void testUserAttributes() {
        Owner owner = new Owner("OWNER001", "Admin", "admin@company.com", "123");
        
        assertEquals("OWNER001", owner.getId());
        assertEquals("Admin", owner.getName());
        assertEquals("admin@company.com", owner.getEmail());
        assertEquals("123", owner.getPassword());
        assertTrue(owner.isActive());
    }

    @Test
    public void testInactiveUser() {
        Owner owner = new Owner("OWNER002", "Inactive Admin", "inactive@company.com", "123", false);
        
        assertFalse(owner.isActive());
    }

    @Test
    public void testHierarchy() {
        Owner owner = new Owner("OWNER001", "Admin", "admin@company.com", "123");
        
        // Verifica se Owner herda de User
        assertTrue(owner instanceof User);
    }
}
