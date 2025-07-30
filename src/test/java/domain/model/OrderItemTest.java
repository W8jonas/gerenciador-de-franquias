package domain.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class OrderItemTest {

    @Test
    public void shouldCreateOrderItemWithValidData() {
        OrderItem item = new OrderItem("oi-1", "o-1", "Notebook", 2, 1500.0);

        assertEquals("oi-1", item.getId());
        assertEquals("o-1", item.getOrderId());
        assertEquals("Notebook", item.getName());
        assertEquals(2, item.getQuantity());
        assertEquals(1500.0, item.getUnitPrice(), 0.1);
        assertEquals(3000.0, item.getLineTotal(), 0.1);
    }

    @Test
    public void shouldThrowWhenInvalidQuantityOrPrice() {
        assertThrows(IllegalArgumentException.class, () ->
                new OrderItem("oi-2", "o-1", "Item", 0, 10.0)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new OrderItem("oi-3", "o-1", "Item", -1, 10.0)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new OrderItem("oi-4", "o-1", "Item", 1, -0.1)
        );
    }

    @Test
    public void settersShouldValidate() {
        OrderItem item = new OrderItem("oi-5", "o-1", "Item", 1, 10.0);

        assertThrows(IllegalArgumentException.class, () -> item.setQuantity(0));
        assertThrows(IllegalArgumentException.class, () -> item.setQuantity(-5));
        assertThrows(IllegalArgumentException.class, () -> item.setUnitPrice(-1.0));

        item.setQuantity(3);
        item.setUnitPrice(20.0);
        assertEquals(60.0, item.getLineTotal(), 0.1);
    }
}
