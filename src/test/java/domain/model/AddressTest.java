package domain.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class AddressTest {

    @Test
    public void shouldCreateAddressWithValidParameters() {
        Address a = new Address(
                "id-123", "rua qualquer", "123", "São Pedro",
                "Juiz de Fora", "MG", "31037000", "Brasil"
        );

        assertEquals("id-123", a.getId());
        assertEquals("rua qualquer", a.getStreet());
        assertEquals("123", a.getNumber());
        assertEquals("São Pedro", a.getDistrict());
        assertEquals("Juiz de Fora", a.getCity());
        assertEquals("MG", a.getState());
        assertEquals("31037000", a.getZipCode());
        assertEquals("Brasil", a.getCountry());
    }

    @Test
    public void equalsShouldBeTrueForSameId() {
        Address a1 = new Address("123456789", "A", "1", "D", "C", "S", "Z", "Brasil");
        Address a2 = new Address("123456789", "B", "2", "E", "D", "T", "X", "Brazil");
        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void toStringShouldContainKeyFields() {
        Address a = new Address("id-xyz", "Av Presidente Costa e Silva", "10", "Dist", "Juiz de Fora", "MG", "00000", "BR");
        String s = a.toString();
        assertTrue(s.contains("Av Presidente Costa e Silva"));
        assertTrue(s.contains("Juiz de Fora"));
    }

}
