package domain.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Order implements Serializable {
    private String id;
    private String idCustomer;
    private String idSeller;
    private String idFranchise;
    private double totalAmount;
    private List<OrderItem> items;

    public Order(String idCustomer, String idSeller, String idFranchise, List<OrderItem> items) {
        this.id = UUID.randomUUID().toString();
        this.idCustomer = idCustomer;
        this.idSeller = idSeller;
        this.idFranchise = idFranchise;
        this.items = items;
        this.totalAmount = calculateTotal(items);
    }

    public Order() {

    }

    private double calculateTotal(List<OrderItem> items) {
        return items.stream().mapToDouble(item -> item.getUnitPrice() * item.getQuantity()).sum();
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdCustomer() { return idCustomer; }

    public void setIdCustomer(String idCustomer) { this.idCustomer = idCustomer; }

    public String getIdSeller() { return idSeller; }

    public void setIdSeller(String idSeller) { this.idSeller = idSeller; }

    public String getIdFranchise() { return idFranchise; }

    public void setIdFranchise(String idFranchise) { this.idFranchise = idFranchise; }

    public double getTotalAmount() { return totalAmount; }

    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public List<OrderItem> getItems() { return items; }

    public void setItems(List<OrderItem> items) {
        this.items = items;
        this.totalAmount = calculateTotal(items);
    }
}
