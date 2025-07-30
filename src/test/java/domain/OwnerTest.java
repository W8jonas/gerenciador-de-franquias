package domain;

import domain.model.Owner;
import org.junit.Test;
import static org.junit.Assert.*;

public class OwnerTest {
    @Test
    public void testAuthenticate() {
        Owner owner = new Owner("Admin", "admin@company.com", "123");
        assertTrue(owner.authenticate("123"));
        assertFalse(owner.authenticate("wrong"));
    }
}
