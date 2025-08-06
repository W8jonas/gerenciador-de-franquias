package domain.model;

import java.util.Objects;

public class OrderItem {
    private String id;
    private String productId;
    private String name;
    private int quantity;
    private double unitPrice;

    public OrderItem() {}

    public OrderItem(String id, String productId, String name, int quantity, double unitPrice) {
        setId(id);
        setProductId(productId);
        setName(name);
        setQuantity(quantity);
        setUnitPrice(unitPrice);
    }
    public double getLineTotal() {
        return quantity * unitPrice;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID do item não pode ser vazio");
        }
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("ID do produto não pode ser vazio");
        }
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        if (unitPrice < 0.0) {
            throw new IllegalArgumentException("Preço unitário deve ser maior ou igual a zero");
        }
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return quantity * unitPrice;
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

    @Override
    public String toString() {
        return name + " (" + quantity + " x R$" + unitPrice + ") = R$" + getTotalPrice();
    }
}
