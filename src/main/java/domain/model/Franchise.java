package domain.model;

import java.io.Serializable;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class Franchise implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private Address address;
    private double revenueAccumulated;
    private Manager manager;
    private List<Seller> sellers;
    private List<Order> orders;

    public Franchise(String id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.revenueAccumulated = 0.0;
        this.manager = null;
        this.sellers = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getRevenueAccumulated() {
        return revenueAccumulated;
    }

    public void setRevenueAccumulated(double revenueAccumulated) {
        this.revenueAccumulated = revenueAccumulated;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<Seller> getSellers() {
        return new ArrayList<>(sellers); // Retorna uma cópia para evitar modificação externa
    }

    public void setSellers(List<Seller> sellers) {
        this.sellers = new ArrayList<>(sellers);
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders); // Retorna uma cópia para evitar modificação externa
    }

    public void setOrders(List<Order> orders) {
        this.orders = new ArrayList<>(orders);
    }

    // Métodos de comportamento para o Dono e o Gerente
    public void addSeller(Seller seller) {
        if (seller != null && !sellers.contains(seller)) {
            sellers.add(seller);
        }
    }

    public void removeSeller(Seller seller) {
        sellers.remove(seller);
    }

    public void addOrder(Order order) {
        if (order != null && !orders.contains(order)) {
            orders.add(order);
            // Atualizar receita acumulada
//            this.revenueAccumulated += order.();
        }
    }

    public int getTotalOrders() {
        return orders.size();
    }

    public double getAverageTicket() {
        if (orders.isEmpty()) {
            return 0.0;
        }
        return revenueAccumulated / orders.size();
    }

    @Override
    public String toString() {
        return "Franchise{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", revenueAccumulated=" + revenueAccumulated +
                ", manager=" + (manager != null ? manager.getName() : "Nenhum") +
                ", sellersCount=" + sellers.size() +
                ", ordersCount=" + orders.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Franchise)) return false;
        Franchise franchise = (Franchise) o;
        return id.equals(franchise.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
} 