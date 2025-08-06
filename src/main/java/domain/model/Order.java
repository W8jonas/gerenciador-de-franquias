package domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private String id;
    private String sellerId;
    private String customerId;
    private String franchiseId;
    private String createdAt;
    private double total;
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
        this.createdAt = LocalDateTime.now().toString();
    }

    public Order(String id, String sellerId, String customerId, String franchiseId) {
        this.id = id;
        this.sellerId = sellerId;
        this.customerId = customerId;
        this.franchiseId = franchiseId;
        this.createdAt = LocalDateTime.now().toString();
    }

    public void addItem(Product product, String name, int quantity, double unitPrice) {
        if (product.getId() == null || product.getId().isBlank()) throw new IllegalArgumentException("Um item de pedido deve ser um produto");
        if (quantity <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que 0");
        if (unitPrice < 0.0) throw new IllegalArgumentException("Preço unitário deve ser maior que 0");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Propriedade nome é obrigatória");

        String itemId = UUID.randomUUID().toString();
        OrderItem item = new OrderItem(itemId, this.id, name, quantity, unitPrice);
        this.orderItems.add(item);
        recalculateTotal();
    }

    public void removeItem(OrderItem item) {
        if (item == null) return;
        this.orderItems.removeIf(i -> Objects.equals(i.getId(), item.getId()));
        recalculateTotal();
    }

    public double calculateTotal() {
        recalculateTotal();
        return this.total;
    }

    private void recalculateTotal() {
        this.total = this.orderItems.stream()
                .mapToDouble(i -> i.getUnitPrice() * i.getQuantity())
                .sum();
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getFranchiseId() { return franchiseId; }
    public void setFranchiseId(String franchiseId) { this.franchiseId = franchiseId; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = (orderItems == null) ? new ArrayList<>() : new ArrayList<>(orderItems);
        recalculateTotal();
    }

    public double getTotal() { return total; }

    @Override
    public String toString() {
        return "Order " +
                "id= " + id +
                ", sellerId= " + sellerId +
                ", customerId= " + customerId +
                ", franchiseId= " + franchiseId +
                ", createdAt= " + createdAt +
                ", total= " + total +
                ", orderItems= " + orderItems +
                '.';
    }
}
