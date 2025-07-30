package domain.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void shouldCreateProductWithValidParameters() {
        Product p = new Product(
                "p-1", "f-1", "Notebook", "intel i3",
                3500.0, 10
        );

        assertEquals("p-1", p.getId());
        assertEquals("f-1", p.getFranchiseId());
        assertEquals("Notebook", p.getName());
        assertEquals("intel i3", p.getDescription());
        assertEquals(3500.0, p.getPrice(), 0.1);
        assertEquals(10, p.getStockQty());
    }

    @Test
    public void shouldThrowWhenNegativePriceOnConstructor() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product("p-2", "f-1", "X", "Y", -1.0, 0)
        );
    }

    @Test
    public void shouldThrowWhenNegativeStockOnConstructor() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product("p-3", "f-1", "X", "Y", 1.0, -5)
        );
    }

    @Test
    public void isLowStockShouldReturnTrueWhenBelowThreshold() {
        Product p = new Product("p-4", "f-1", "X", "Y", 10.0, 2);
        assertTrue(p.isLowStock(3));
        assertFalse(p.isLowStock(2)); // edge: equal to threshold is NOT low
    }

    @Test
    public void decreaseStockShouldWorkAndThrowWhenInsufficient() {
        Product p = new Product("p-5", "f-1", "X", "Y", 10.0, 5);
        p.decreaseStock(2);
        assertEquals(3, p.getStockQty());

        assertThrows(IllegalArgumentException.class, () -> p.decreaseStock(0));
        assertThrows(IllegalArgumentException.class, () -> p.decreaseStock(-1));
        assertThrows(IllegalArgumentException.class, () -> p.decreaseStock(10)); // insufficient
    }

    @Test
    public void increaseStockShouldWorkAndValidateQty() {
        Product p = new Product("p-6", "f-1", "X", "Y", 10.0, 5);
        p.increaseStock(3);
        assertEquals(8, p.getStockQty());

        assertThrows(IllegalArgumentException.class, () -> p.increaseStock(0));
        assertThrows(IllegalArgumentException.class, () -> p.increaseStock(-2));
    }

    @Test
    public void settersShouldValidateNegativeValues() {
        Product p = new Product("p-7", "f-1", "X", "Y", 10.0, 5);
        assertThrows(IllegalArgumentException.class, () -> p.setPrice(-0.01));
        assertThrows(IllegalArgumentException.class, () -> p.setStockQty(-1));
    }
}
