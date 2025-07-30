package domain.model;

import java.util.Objects;

public class Product {
    private String id;
    private String franchiseId;
    private String name;
    private String description;
    private String sku;
    private double price;
    private int stockQty;

    public Product() {
    }

    public Product(String id, String franchiseId, String name, String description, String sku, double price, int stockQty) {
        if (price < 0.0) throw new IllegalArgumentException("Preço não pode ser negativo");
        if (stockQty < 0) throw new IllegalArgumentException("Quatidade não pode ser negativo");
        this.id = id;
        this.franchiseId = franchiseId;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.stockQty = stockQty;
    }

    public boolean isLowStock(int threshold) {
        return stockQty < threshold;
    }

    public void decreaseStock(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quatidade deve ser maior que 0");
        if (stockQty - qty < 0) throw new IllegalArgumentException("Sem estoque disponível");
        stockQty -= qty;
    }

    public void increaseStock(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quatidade deve ser maior que 0");
        stockQty += qty;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFranchiseId() { return franchiseId; }
    public void setFranchiseId(String franchiseId) { this.franchiseId = franchiseId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price < 0.0) throw new IllegalArgumentException("Preço não pode ser negativo");
        this.price = price;
    }

    public int getStockQty() { return stockQty; }
    public void setStockQty(int stockQty) {
        if (stockQty < 0) throw new IllegalArgumentException("Quantidade não pode ser negativo");
        this.stockQty = stockQty;
    }

    @Override
    public String toString() {
        return "Produto " +
                "id= " + id +
                ", franchiseId= " + franchiseId +
                ", name= " + name +
                ", description= " + description +
                ", sku= " + sku +
                ", price= " + price +
                ", stockQty= " + stockQty +
                '.';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
