package domain.model;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe que representa um pedido no sistema.
 * Contém informações sobre o pedido, cliente, franquia e itens do pedido.
 */
public class Order implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private String franchiseId;
    private String customerId;
    private LocalDateTime dateTimeCreated;
    private double totalValue;
    private List<OrderItem> orderItems;

    /**
     * Construtor padrão.
     */
    public Order() {
        this.dateTimeCreated = LocalDateTime.now();
        this.orderItems = new ArrayList<>();
    }

    /**
     * Construtor que inicializa os atributos básicos do pedido.
     * 
     * @param id Identificador único do pedido
     * @param franchiseId Identificador da franquia
     * @param customerId Identificador do cliente
     */
    public Order(String id, String franchiseId, String customerId) {
        this.id = id;
        this.franchiseId = franchiseId;
        this.customerId = customerId;
        this.dateTimeCreated = LocalDateTime.now();
        this.orderItems = new ArrayList<>();
    }

    /**
     * Adiciona um item ao pedido.
     * 
     * @param item Item a ser adicionado ao pedido
     * @throws IllegalArgumentException se o item for nulo
     */
    public void addOrderItem(OrderItem item) {
        if (item == null) throw new IllegalArgumentException("Item não pode ser nulo");
        this.orderItems.add(item);
        calculateTotal();
    }

    /**
     * Remove um item do pedido.
     * 
     * @param item Item a ser removido do pedido
     */
    public void removeOrderItem(OrderItem item) {
        if (item == null) return;
        this.orderItems.removeIf(i -> Objects.equals(i.getId(), item.getId()));
        calculateTotal();
    }

    /**
     * Recalcula o valor total do pedido baseado nos itens.
     */
    public double calculateTotal() {
        this.totalValue = this.orderItems.stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
         return this.totalValue;
    }

    // Getters e Setters

    public String getId() { 
        return id; 
    }
    
    public void setId(String id) { 
        this.id = id; 
    }

    public String getFranchiseId() { 
        return franchiseId; 
    }
    
    public void setFranchiseId(String franchiseId) { 
        this.franchiseId = franchiseId; 
    }

    public String getCustomerId() { 
        return customerId; 
    }
    
    public void setCustomerId(String customerId) { 
        this.customerId = customerId; 
    }

    public LocalDateTime getDateTimeCreated() { 
        return dateTimeCreated; 
    }
    
    public void setDateTimeCreated(LocalDateTime dateTimeCreated) { 
        this.dateTimeCreated = dateTimeCreated; 
    }

    public double getTotalValue() { 
        return totalValue; 
    }
    
    public void setTotalValue(double totalValue) {
        if (totalValue < 0.0) throw new IllegalArgumentException("Valor total não pode ser negativo");
        this.totalValue = totalValue;
    }

    public List<OrderItem> getOrderItems() { 
        return new ArrayList<>(orderItems); 
    }
    
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = (orderItems == null) ? new ArrayList<>() : new ArrayList<>(orderItems);
        calculateTotal();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", franchiseId='" + franchiseId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", dateTimeCreated=" + dateTimeCreated +
                ", totalValue=" + totalValue +
                ", orderItems=" + orderItems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
