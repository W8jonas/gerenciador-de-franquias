package models;

import org.junit.Test;
import static org.junit.Assert.*;

public class OwnerTest {
    @Test
    public void testLogin() {
        Owner owner = new Owner("OWNER001", "Admin", "admin@company.com", "123");
        
        // Teste de login bem-sucedido
        assertTrue(owner.login("admin@company.com", "123"));
        
        // Teste de login com credenciais incorretas
        assertFalse(owner.login("admin@company.com", "wrong"));
        assertFalse(owner.login("wrong@email.com", "123"));
        assertFalse(owner.login("wrong@email.com", "wrong"));
    }

    @Test
    public void testUserAttributes() {
        Owner owner = new Owner("OWNER001", "Admin", "admin@company.com", "123");
        
        assertEquals("OWNER001", owner.getId());
        assertEquals("Admin", owner.getName());
        assertEquals("admin@company.com", owner.getEmail());
        assertTrue(owner.isActive());
    }

    @Test
    public void testInactiveUser() {
        Owner owner = new Owner("OWNER002", "Inactive Admin", "inactive@company.com", "123", false);
        
        // Usuário inativo não deve conseguir fazer login
        assertFalse(owner.login("inactive@company.com", "123"));
        assertFalse(owner.isActive());
    }
}
