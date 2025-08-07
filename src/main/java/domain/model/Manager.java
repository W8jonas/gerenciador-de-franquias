package domain.model;

import java.io.Serializable;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class Manager extends User implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    private List<Seller> assignedSellers;
    private Franchise assignedFranchise;
    private List<Product> productsInStock;
    private List<Order> salesHistory;

    public Manager(String name, String email, String password) {
        super(name, email, password);
        this.assignedSellers = new ArrayList<>();
        this.productsInStock = new ArrayList<>();
        this.salesHistory = new ArrayList<>();
    }

    public void assignSeller(Seller seller) {
        if (seller != null && !assignedSellers.contains(seller)) {
            assignedSellers.add(seller);
        }
    }

    public void removeSeller(Seller seller) {
        assignedSellers.remove(seller);
    }

    public List<Seller> getAssignedSellers() {
        return new ArrayList<>(assignedSellers);
    }

    public void setAssignedSellers(List<Seller> assignedSellers) {
        this.assignedSellers = new ArrayList<>(assignedSellers);
    }

    public Franchise getAssignedFranchise() {
        return assignedFranchise;
    }

    public void setAssignedFranchise(Franchise assignedFranchise) {
        this.assignedFranchise = assignedFranchise;
    }

    public List<Product> getProductsInStock() {
        return new ArrayList<>(productsInStock);
    }

    public void setProductsInStock(List<Product> productsInStock) {
        this.productsInStock = new ArrayList<>(productsInStock);
    }

    public List<Order> getSalesHistory() {
        return new ArrayList<>(salesHistory);
    }

    public void setSalesHistory(List<Order> salesHistory) {
        this.salesHistory = new ArrayList<>(salesHistory);
    }

    public void addProduct(Product product) {
        if (product != null && !productsInStock.contains(product)) {
            productsInStock.add(product);
        }
    }

    public void removeProduct(Product product) {
        productsInStock.remove(product);
    }

    public void addOrderToHistory(Order order) {
        if (order != null && !salesHistory.contains(order)) {
            salesHistory.add(order);
        }
    }

    public void editOrder(String orderId) {
    }


    @Override
    public String toString() {
        return "Manager " +
                "name " + name +
                ", email " + email +
                ", assignedFranchise " + (assignedFranchise != null ? assignedFranchise.getName() : "Nenhuma") +
                ", assignedSellersCount " + assignedSellers.size() +
                ", productsInStockCount " + productsInStock.size() +
                ", salesHistoryCount " + salesHistory.size() +
                " ";
    }
} 