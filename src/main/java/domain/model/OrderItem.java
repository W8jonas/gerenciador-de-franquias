package domain.model;

import java.util.Objects;

public class OrderItem {
    private String id;
    private String orderId;
    private String name;
    private int quantity;
    private double unitPrice;

    public OrderItem() {
    }

    public OrderItem(String id, String orderId, String name, int quantity, double unitPrice) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que 0");
        if (unitPrice < 0.0) throw new IllegalArgumentException("Preço unitário deve ser maior que 0");
        this.id = id;
        this.orderId = orderId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double getLineTotal() {
        return quantity * unitPrice;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que 0");
        this.quantity = quantity;
    }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) {
        if (unitPrice < 0.0) throw new IllegalArgumentException("Preço unitário deve ser maior que 0");
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderItem " +
                "id " + id +
                ", orderId " + orderId +
                ", name " + name +
                ", quantity= " + quantity +
                ", unitPrice= " + unitPrice +
                '.';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem that = (OrderItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
