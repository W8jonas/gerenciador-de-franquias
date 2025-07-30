package domain.model;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class OrderTest {

    private Product sampleProduct(String id, String name, double price) {
        return new Product(id,"fr-1", name,"desc", price,100
        );
    }

    @Test
    public void shouldCreateOrderWithDefaults() {
        Order o = new Order("o-1", "s-1", "c-1", "fr-1");

        assertEquals("o-1", o.getId());
        assertEquals("s-1", o.getSellerId());
        assertEquals("c-1", o.getCustomerId());
        assertEquals("fr-1", o.getFranchiseId());
        assertNotNull(o.getCreatedAt());
        assertEquals(0.0, o.getTotal(), 0.1);
        assertTrue(o.getOrderItems().isEmpty());
    }

    @Test
    public void addItemShouldThrowWhenInvalidQuantityPriceOrName() {
        Order o = new Order("o-3", "s-1", "c-1", "fr-1");
        Product p = sampleProduct("p-2", "Keyboard", 100.0);

        assertThrows(IllegalArgumentException.class, () -> o.addItem(p, "K", 0, 10.0));
        assertThrows(IllegalArgumentException.class, () -> o.addItem(p, "K", -1, 10.0));
        assertThrows(IllegalArgumentException.class, () -> o.addItem(p, "K", 1, -0.1));
        assertThrows(IllegalArgumentException.class, () -> o.addItem(p, null, 1, 10.0));
        assertThrows(IllegalArgumentException.class, () -> o.addItem(p, "", 1, 10.0));
    }

    @Test
    public void removeItemShouldUpdateTotalsAndBeNullSafe() {
        Order o = new Order("o-4", "s-1", "c-1", "fr-1");
        Product p = sampleProduct("p-3", "SSD", 200.0);

        o.addItem(p, "Notebook", 1, 200.0); // total 200
        o.addItem(p, "SSD Promo", 2, 150.0); // +300 = 500
        assertEquals(500.0, o.getTotal(), 0.1);
        assertEquals(2, o.getOrderItems().size());

        // null-safe
        o.removeItem(null);
        assertEquals(2, o.getOrderItems().size());
        assertEquals(500.0, o.getTotal(), 0.1);

        // remove first item
        OrderItem first = o.getOrderItems().get(0);
        o.removeItem(first);
        assertEquals(1, o.getOrderItems().size());
        assertEquals(300.0, o.getTotal(), 0.1);
    }

    @Test
    public void calculateTotalShouldSumAllItems() {
        Order o = new Order("o-5", "s-1", "c-1", "fr-1");
        Product p1 = sampleProduct("p-4", "ItemA", 10.0);
        Product p2 = sampleProduct("p-5", "ItemB", 25.0);

        o.addItem(p1, "Notebook", 3, 10.0);
        o.addItem(p2, "Notebook", 2, 25.0);
        assertEquals(80.0, o.calculateTotal(), 0.1);

        // add another
        o.addItem(p1, "Celular", 1, 5.0);
        assertEquals(85.0, o.getTotal(), 0.1);
    }

    @Test
    public void setOrderItemsShouldReplaceListAndRecalculate() {
        Order o = new Order("o-6", "s-1", "c-1", "fr-1");
        Product p = sampleProduct("p-6", "Headset", 80.0);

        o.addItem(p, "Celular", 1, 80.0);  // total 80
        assertEquals(80.0, o.getTotal(), 0.1);

        OrderItem i1 = new OrderItem("it-1", "o-6", "Item1", 2, 10.0); // 20
        OrderItem i2 = new OrderItem("it-2", "o-6", "Item2", 1, 5.0);  // 5

        o.setOrderItems(List.of(i1, i2)); // should recalc → 25
        assertEquals(25.0, o.getTotal(), 0.1);
        assertEquals(2, o.getOrderItems().size());
    }
}
